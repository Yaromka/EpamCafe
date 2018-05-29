package com.epam.cafe.manager;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigurationManager {
    private static final Logger LOGGER = LogManager.getLogger(ConfigurationManager.class);

    private ConfigurationManager() { }
    /**
     * Returns all page paths by key.
     * @param key value from .property file.
     */
    public static String getProperty(String key) {
        String res;
        ResourceBundle resourceBundle;
        try {
            resourceBundle = ResourceBundle.getBundle("config");
        } catch (MissingResourceException e) {
            LOGGER.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new IllegalArgumentException("Exception during reading resourceBundle", e);
        }
        try {
            res = resourceBundle.getString(key);
        } catch (MissingResourceException | ExceptionInInitializerError e) {
            LOGGER.log(Level.FATAL, "Exception during reading resourceBundle", e);
            throw new IllegalArgumentException("Exception during reading resourceBundle", e);
        }
        return res;
    }
}
