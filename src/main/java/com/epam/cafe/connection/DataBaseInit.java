package com.epam.cafe.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DataBaseInit {
    private static final Logger LOGGER = LogManager.getLogger(DataBaseInit.class);
    private static final String RESOURCE_BUNDLE_NAME = "db/dbconnection_config";
    private static final String USER = "db_user";
    private static final String PASSWORD = "db_password";
    private static final String CONNECTION = "db_connection";
    private static final String POOL_SIZE = "db_pool_size";

    private static String user;
    private static String password;
    private static String url;
    private static int poolSize;

    static {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
            user = resourceBundle.getString(USER);
            password = resourceBundle.getString(PASSWORD);
            url = resourceBundle.getString(CONNECTION);
            poolSize = Integer.parseInt(resourceBundle.getString(POOL_SIZE));
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "An exception occurred during reading resourceBundle", e);
            throw new IllegalArgumentException("An exception occurred during reading resourceBundle", e);
        }
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DataBaseInit.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DataBaseInit.password = password;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DataBaseInit.url = url;
    }

    public static int getPoolSize() {
        return poolSize;
    }

    public static void setPoolSize(int poolSize) {
        DataBaseInit.poolSize = poolSize;
    }
}

