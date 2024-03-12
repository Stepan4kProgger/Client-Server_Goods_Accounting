package res;

import res.common.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

public class ClientInteractions {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 55555;
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public ClientInteractions() throws IOException {
        socket = new Socket(HOST, PORT);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
    }

    /**
     * <p>Returns 0 if logged in as user</p>
     * <p>Returns 1 if logged in as administrator</p>
     * ---------------------------------------
     * <p>Returns -1 if login not found</p>
     * <p>Returns -2 if password not found</p>
     * <p>Returns -3 if account is not verified</p>
     * <p>Returns -4 if any untypical error occurred</p>
     */
    public int onLogin(String login, String password) {
        try {
            out.writeObject(new TransferCode("login"));
            out.writeObject(new Client(login, password));
            switch (((TransferCode) in.readObject()).getCode()) {
                case "1":
                    return 1;
                case "0":
                    return 0;
                case "bad_login":
                    return -1;
                case "bad_password":
                    return -2;
                case "unverified":
                    return -3;
                case "err":
                    throw new IOException();
            }
        } catch (IOException | ClassNotFoundException e) {
            return -4;
        }
        return -4;
    }

    /**
     * <p>Returns 0 if registered successfully</p>
     * ---------------------------------------
     * <p>Returns -1 if login is already taken</p>
     * <p>Returns -2 if any untypical error occurred</p>
     */
    public int onRegister(String login, String password) {
        try {
            out.writeObject(new TransferCode("register"));
            out.writeObject(new Client(login, password));
            switch (((TransferCode) in.readObject()).getCode()) {
                case "success":
                    return 0;
                case "bad_login":
                    return -1;
                case "err":
                    throw new IOException();
            }
        } catch (IOException | ClassNotFoundException e) {
            return -2;
        }
        return -2;
    }

    public void onAfterLoginState() {
        try {
            out.writeObject(new TransferCode("after_login"));
            this.client = (Client) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void onChangePasswordState(String password) {
        try {
            client.setPassword(password);
            out.writeObject(new TransferCode("change_password"));
            out.writeObject(client);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public Collection<Worker> onSeeWorkersState() {
        try {
            out.writeObject(new TransferCode("see_workers"));
            return ((WorkerArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public Collection<Operation> onSeeOperationsAsUserState() {
        try {
            out.writeObject(new TransferCode("see_operations_user"));
            return ((OperationArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    //Product management begin
    public ArrayList<Product> displayProducts() {
        try {
            out.writeObject(new TransferCode("manage_products"));
            return ((ProductArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Product> refreshProducts() {
        try {
            out.writeObject(new TransferCode("refresh"));
            return ((ProductArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void addProduct(Product product) {
        try {
            out.writeObject(new TransferCode("add"));
            out.writeObject(product);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void removeProduct(Product product) {
        try {
            out.writeObject(new TransferCode("remove"));
            out.writeObject(product);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void editProduct(Product oldProduct, Product newProduct, String what_changed) {
        try {
            out.writeObject(new TransferCode("edit"));
            out.writeObject(new TransferCode(what_changed));
            out.writeObject(oldProduct);
            out.writeObject(newProduct);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getTypes() {
        try {
            out.writeObject(new TransferCode("prod_types"));
            ArrayList<String> list = new ArrayList<>();
            for (var value: ((ProductTypeArrayList) in.readObject()).getList()) {
                list.add(value.getProdType());
            }
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void addType(ProductType type) {
        try {
            out.writeObject(new TransferCode("add_type"));
            out.writeObject(type);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }
    //Product management end

    //Worker management begin
    public ArrayList<Worker> displayWorkers() {
        try {
            out.writeObject(new TransferCode("manage_workers"));
            return ((WorkerArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Worker> refreshWorkers() {
        try {
            out.writeObject(new TransferCode("refresh"));
            return ((WorkerArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void addWorker(Worker worker) {
        try {
            out.writeObject(new TransferCode("add"));
            out.writeObject(worker);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void removeWorker(Worker worker) {
        try {
            out.writeObject(new TransferCode("remove"));
            out.writeObject(worker);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getLogins() {
        try {
            out.writeObject(new TransferCode("get_logins"));
            ArrayList<String> list = new ArrayList<>();
            for (var value: ((ClientArrayList) in.readObject()).getList()) {
                list.add(value.getLogin());
            }
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void editWorker(String what_changed, Worker oldWorker, Worker newWorker) {
        try {
            out.writeObject(new TransferCode("edit"));
            out.writeObject(new TransferCode(what_changed));
            out.writeObject(oldWorker);
            out.writeObject(newWorker);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }
    //Worker management end

    //User management begin
    public ArrayList<Client> displayUsers() {
        try {
            out.writeObject(new TransferCode("manage_users"));
            return ((ClientArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Client> refreshUsers() {
        try {
            out.writeObject(new TransferCode("refresh"));
            return ((ClientArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void addUser(Client user) {
        try {
            out.writeObject(new TransferCode("add"));
            out.writeObject(user);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void removeUser(Client user) {
        try {
            out.writeObject(new TransferCode("remove"));
            out.writeObject(user);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getUnverified() {
        try {
            out.writeObject(new TransferCode("get_unverified"));
            ArrayList<String> list = new ArrayList<>();
            for (var value: ((ClientArrayList) in.readObject()).getList()) {
                list.add(value.getLogin());
            }
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getNames() {
        try {
            out.writeObject(new TransferCode("get_workers"));
            ArrayList<String> list = new ArrayList<>();
            for (var value: ((WorkerArrayList) in.readObject()).getList()) {
                list.add(value.getName());
            }
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void editUser(String what_changed, Client oldUser, Client newUser) {
        try {
            out.writeObject(new TransferCode("edit"));
            out.writeObject(new TransferCode(what_changed));
            out.writeObject(oldUser);
            out.writeObject(newUser);
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }
    //User management end

    public ArrayList<Operation> getAllOperations() {
        try {
            out.writeObject(new TransferCode("see_all_operations"));
            return ((OperationArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Operation> refreshOperations() {
        try {
            out.writeObject(new TransferCode("refresh"));
            return ((OperationArrayList) in.readObject()).getList();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void onBack() {
        try {
            out.writeObject(new TransferCode("back"));
        } catch (IOException e) {
            System.out.println("Exception");
            throw new RuntimeException(e);
        }
    }

    public void onLeaveState() {
        try {
            out.writeObject(new TransferCode("leave"));
            closeSocket();
        } catch (IOException e) {
            System.out.println("Exception at leave state");
            throw new RuntimeException(e);
        }
    }

    public void closeSocket() {
        try {
            out.writeObject(new TransferCode("exit"));
            in.close();
            out.close();
            if (!socket.isClosed())
                socket.close();
        } catch (IOException ignored) {
        }
    }
}
