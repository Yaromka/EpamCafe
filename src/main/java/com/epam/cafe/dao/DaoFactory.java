package com.epam.cafe.dao;

import com.epam.cafe.connection.ConnectionPool;
import com.epam.cafe.connection.ConnectionProxy;
import com.epam.cafe.dao.impl.CategoryDaoImpl;
import com.epam.cafe.dao.impl.DishDaoImpl;
import com.epam.cafe.dao.impl.OrderDaoImpl;
import com.epam.cafe.dao.impl.UserDaoImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DaoFactory implements AutoCloseable{
    private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);
    private ConnectionProxy connection = ConnectionPool.getInstance().getConnection();

    public UserDao getUserDao(){
        return new UserDaoImpl(connection);
    }

    public CategoryDao getCategoryDao(){
        return new CategoryDaoImpl(connection);
    }

    public DishDao getDishDao(){
        return new DishDaoImpl(connection);
    }

    public OrderDao getOrderDao(){
        return new OrderDaoImpl(connection);
    }

    public void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "An exception occurred during setting auto commit in false.");
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "An exception occurred during setting auto commit in false.");
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "An exception occurred during committing.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "An exception occurred during rolling back transaction.", e);
        }
    }

    @Override
    public void close() {
        connection.close();
    }
}