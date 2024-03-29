package TCP;


import JDBC.JDBC_class;
import res.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;

import static JDBC.Queries.*;

public class TCPClientThread implements Runnable {
    public static int clientAmount = 0;
    private final Statement statement;
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Client client;

    public TCPClientThread(Socket socket) throws SQLException {
        this.socket = socket;
        statement = JDBC_class.getConnection().createStatement();
    }

    private void onLoginState() throws IOException, ClassNotFoundException {
        Client account = (Client) in.readObject();
        String answer = logIn(statement, account.getLogin(), account.getPassword());
        if ("01".contains(answer)) {
            try {
                client = getClientByLogin(statement, account.getLogin());
            } catch (SQLException e) {
                answer = "err";
            }
        }
        out.writeObject(new TransferCode(answer));
    }

    private void onRegisterState() throws IOException, ClassNotFoundException {
        Client account = (Client) in.readObject();
        try {
            if (isLoginAvailable(statement, account.getLogin())) {
                addNewAccountReg(statement, account.getLogin(), account.getPassword());
                out.writeObject(new TransferCode("success"));
            } else
                out.writeObject(new TransferCode("bad_login"));
        } catch (SQLException e) {
            e.printStackTrace();
            out.writeObject(new TransferCode("err"));
        }
    }

    private void afterLoginState() throws IOException, ClassNotFoundException {
        boolean proceed = true;
        out.writeObject(client);
        while (proceed)
            switch (((TransferCode) in.readObject()).getCode()) {
                case "leave":
                    proceed = false;
                    client = null;
                    break;
                case "change_password":
                    client.setPassword(((Client) in.readObject()).getPassword());
                    try {
                        changePassword(statement, client);
                    } catch (SQLException ignored) {
                    }
                    break;
                case "see_workers":
                    try {
                        out.writeObject(getWorkers(statement));
                    } catch (SQLException ignored) {
                    }
                    break;
                case "see_operations_user":
                    try {
                        out.writeObject(seeOperationsAtProducts(statement));
                    } catch (SQLException ignored) {
                    }
                    break;
                case "manage_products":
                    try {
                        out.writeObject(getProducts(statement));
                        boolean noBackRequest = true;
                        while (noBackRequest) {
                            switch (((TransferCode) in.readObject()).getCode()) {
                                case "refresh":
                                    out.writeObject(getProducts(statement));
                                    break;
                                case "add":
                                    addProduct(statement, client, (Product) in.readObject());
                                    break;
                                case "remove":
                                    removeProduct(statement, client, (Product) in.readObject());
                                    break;
                                case "edit":
                                    editProduct(statement, client, (TransferCode) in.readObject(), (Product) in.readObject(), (Product) in.readObject());
                                    break;
                                case "prod_types":
                                    out.writeObject(getTypes(statement));
                                    break;
                                case "add_type":
                                    addType(statement, (ProductType) in.readObject());
                                    break;
                                case "back":
                                    noBackRequest = false;
                                    break;
                                case "leave":
                                    noBackRequest = false;
                                    proceed = false;
                                    client = null;
                                    break;
                            }
                        }
                    } catch (SQLException ignored) {
                    }
                    break;
                case "manage_workers":
                    try {
                        out.writeObject(getWorkers(statement));
                        boolean noBackRequest = true;
                        while (noBackRequest) {
                            switch (((TransferCode) in.readObject()).getCode()) {
                                case "refresh":
                                    out.writeObject(getWorkers(statement));
                                    break;
                                case "add":
                                    addWorker(statement, client, (Worker) in.readObject());
                                    break;
                                case "remove":
                                    removeWorker(statement, client, (Worker) in.readObject());
                                    break;
                                case "edit":
                                    editWorker(statement, client, (TransferCode) in.readObject(), (Worker) in.readObject(), (Worker) in.readObject());
                                    break;
                                case "get_logins":
                                    out.writeObject(getClients(statement));
                                    break;
                                case "back":
                                    noBackRequest = false;
                                    break;
                                case "leave":
                                    noBackRequest = false;
                                    proceed = false;
                                    client = null;
                                    break;
                            }
                        }
                    } catch (SQLException ignored) {
                    }
                    break;
                case "manage_users":
                    try {
                        out.writeObject(getClients(statement));
                        boolean noBackRequest = true;
                        while (noBackRequest) {
                            switch (((TransferCode) in.readObject()).getCode()) {
                                case "refresh":
                                    out.writeObject(getClients(statement));
                                    break;
                                case "add":
                                    addClient(statement, client, (Client) in.readObject());
                                    break;
                                case "remove":
                                    removeClient(statement, client, (Client) in.readObject());
                                    break;
                                case "edit":
                                    editClient(statement, client, (TransferCode) in.readObject(), (Client) in.readObject(), (Client) in.readObject());
                                    break;
                                case "get_workers":
                                    out.writeObject(getWorkers(statement));
                                    break;
                                case "get_unverified":
                                    out.writeObject(getUnverified(statement));
                                    break;
                                case "back":
                                    noBackRequest = false;
                                    break;
                                case "leave":
                                    noBackRequest = false;
                                    proceed = false;
                                    client = null;
                                    break;
                            }
                        }
                    } catch (SQLException ignored) {
                    }
                    break;
                case "see_all_operations":
                    try {
                        out.writeObject(getOperations(statement));
                        boolean noBackRequest = true;
                        while (noBackRequest) {
                            switch (((TransferCode) in.readObject()).getCode()) {
                                case "refresh":
                                    out.writeObject(getOperations(statement));
                                    break;
                                case "back":
                                    noBackRequest = false;
                                    break;
                                case "leave":
                                    noBackRequest = false;
                                    proceed = false;
                                    client = null;
                                    break;
                            }
                        }
                    } catch (SQLException ignored) {
                    }
                    break;
            }
    }

    @Override
    public void run() {
        System.out.println("Подключен клиент (id: " + Thread.currentThread().getName() + ')');
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            while (!socket.isClosed()) {
                switch (((TransferCode) in.readObject()).getCode()) {
                    case "login":
                        onLoginState();
                        break;
                    case "register":
                        onRegisterState();
                        break;
                    case "after_login":
                        afterLoginState();
                        break;
                    case "exit":
                        socket.close();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Возникли проблемы с обработкой данных сокетом (id: " + Thread.currentThread().getName() + ')');
        } finally {
            try {
                if (!socket.isClosed())
                    socket.close();
                in.close();
                out.close();
            } catch (IOException | NullPointerException e) {
                System.out.println("Невозможно закрыть подключение или потоки ввода/вывода (id: " + Thread.currentThread().getName() + ')');
            }
        }
        System.out.println("Отключился клиент (id: " + Thread.currentThread().getName() + ')');
        System.out.println(--clientAmount + " клиентов сейчас активно");
    }
}
