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
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private ConnectionProxy connection;

    public UserDao getUserDao(){
        connection = connectionPool.getConnection();
        UserDaoImpl userDao = new UserDaoImpl( connection);
        userDao.setConnection(connection);
        return userDao;
    }

    public CategoryDao getCategoryDao(){
        connection = connectionPool.getConnection();
        CategoryDaoImpl categoryDao = new CategoryDaoImpl(connection);
        categoryDao.setConnection(connection);
        return categoryDao;
    }

    public DishDao getDishDao(){
        connection = connectionPool.getConnection();
        DishDaoImpl dishDao = new DishDaoImpl(connection);
        dishDao.setConnection(connection);
        return dishDao;
    }

    public OrderDao getOrderDao(){
        connection = connectionPool.getConnection();
        OrderDaoImpl orderDao = new OrderDaoImpl(connection);
        orderDao.setConnection(connection);
        return orderDao;
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
