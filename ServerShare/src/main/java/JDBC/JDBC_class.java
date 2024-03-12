package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC_class {
    private static Connection connection = null;

    private static final String database_name = "shopdatabase";

    public static Connection getConnection() {
        return connection;
    }

    public static void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC драйвер не найден");
        }
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost/" + database_name,
                        "root",
                        "1111");
        if (connection == null)
            throw new SQLException("Драйвер не подключен");
        System.out.println("БД успешно подключена к серверу.");
    }

    public static void close() throws SQLException {
        if (connection != null)
            connection.close();
    }

    public static void atDebugMode() throws SQLException {
        close();
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost/mysql",
                        "root",
                        "1111");
        System.out.println("Подключение для создания бд установлено.");
    }
}
