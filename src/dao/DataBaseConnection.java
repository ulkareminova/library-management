package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DataBaseConnection {
    INSTANCE;
    private Connection connection;
    public Connection getConnection() throws SQLException {
        if (connection==null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        }
        return connection;
    }
}
