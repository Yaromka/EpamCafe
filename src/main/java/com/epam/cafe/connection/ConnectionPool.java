package com.epam.cafe.connection;

import com.epam.cafe.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static Lock instanceLock = new ReentrantLock();
    private static Lock readWriteLock = new ReentrantLock();
    private static AtomicBoolean isInstanceExist = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private Semaphore semaphore;
    private Queue<ConnectionProxy> connectionQueue;

    private ConnectionPool() {
        initConnectionPool();
    }

    public static ConnectionPool getInstance() {
        if (!isInstanceExist.get()) {
            instanceLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInstanceExist.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    private void initConnectionPool() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch(SQLException e) {
            LOGGER.log(Level.FATAL, "JDBC driver is not registered", e);
            throw new RuntimeException("JDBC driver is not registered", e);
        }

        semaphore = new Semaphore(DataBaseInit.getPoolSize(), true);
        connectionQueue = new LinkedList<ConnectionProxy>();

        for (int i = 0; i < DataBaseInit.getPoolSize(); i++) {
            try {
                ConnectionProxy connection = ConnectionCreator.createConnection();
                connectionQueue.offer(connection);
            } catch (ConnectionPoolException e) {
                LOGGER.fatal("Connection is not created", e );
                throw new IllegalArgumentException("Connection is not created", e);
            }
        }
    }

    public int getSize() {
        return connectionQueue.size();
    }

    public ConnectionProxy getConnection() {
        ConnectionProxy connection = null;
        try {
            readWriteLock.lock();
            semaphore.acquire();
            connection = connectionQueue.poll();
        } catch(InterruptedException e) {
            LOGGER.log(Level.ERROR, "An exception occurred during getting connection", e);
        } finally {
            readWriteLock.unlock();
        }
        return connection;
    }

    public void terminatePool() {

        for (int i = 0; i < connectionQueue.size(); i++) {
            try {
                connectionQueue.poll().closeConnection();
            } catch  (SQLException e) {
                LOGGER.log(Level.ERROR, "An exception occurred during pool termination", e);
            }
        }
        deregisterAllDrivers();
    }

    private void deregisterAllDrivers() {
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Deregister driver error");
        }
    }

    void releaseConnection(ConnectionProxy connection) {
        readWriteLock.lock();
        connectionQueue.offer(connection);
        semaphore.release();
        readWriteLock.unlock();
    }
}
