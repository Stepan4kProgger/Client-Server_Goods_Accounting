import JDBC.JDBC_class;
import JDBC.Queries;
import TCP.TCPClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

import static TCP.TCPClientThread.clientAmount;

public class Server {
    private static final int PORT = 55555;

    private static void onFirstTime() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Пройдите по шагам для создания бд\n" +
                "Введите требуемый логин стартовой учётной записи");
        final String req_answer = "admin";
        if (scanner.next().equals(req_answer)){
            System.out.println("Введите требуемый пароль стартовой учётной записи");
            if (scanner.next().equals(req_answer)) {
                JDBC_class.atDebugMode();
                Queries.makeDB(JDBC_class.getConnection().createStatement());
                System.out.println("БД сгенерирована и готова к работе. Запустите сервер повторно.");
                return;
            }
        }
        System.out.println("Создание БД отменено.");
    }

    public static void main(String[] args) {
        ServerSocket socket = null;
        try {
            JDBC_class.connect();
            socket = new ServerSocket(PORT);
            System.out.println("Сервер запущен. Адрес: " + socket.getLocalSocketAddress() + '\n' +
                    "К обслуживанию клиентов готов");
            long id = 1;
            while (!socket.isClosed()) {
                Socket connection = socket.accept();
                Thread thread = new Thread(new TCPClientThread(connection), "" + id++);
                thread.start();
                System.out.println(++clientAmount + " клиентов сейчас активно");
            }
            JDBC_class.close();
        } catch (SQLException e) {
            System.out.println("Работа с SQL невозможна\nПричина: " + e.getMessage());
            if (e.getErrorCode() == 1049)
                try {
                    onFirstTime();
                } catch (SQLException ignored) {
                }
        } catch (IOException e) {
            System.out.println("Возникли проблемы при работе с протоколом");
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Сервер остановлен вручную");
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                System.out.println("Невозможно закрыть серверный сокет");
            }
        }
    }
}