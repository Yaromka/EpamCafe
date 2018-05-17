package com.epam.cafe.connection;

import com.epam.cafe.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreator {
    public static ConnectionProxy createConnection() throws ConnectionPoolException {
        try {
            String url = DataBaseInit.getUrl();
            String user = DataBaseInit.getUser();
            String password = DataBaseInit.getPassword();
            Connection connection = DriverManager.getConnection(url, user, password);
            return new ConnectionProxy(connection);
        } catch (SQLException e) {
            throw new ConnectionPoolException("An exception occurred during creating connection: " , e);
        }
    }
}