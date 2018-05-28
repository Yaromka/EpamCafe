package com.epam.cafe.pool;

import com.epam.cafe.connection.ConnectionPool;
import com.epam.cafe.connection.ConnectionProxy;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConnectionPoolTest {
    private final static int expectedSize = 32;

    public ConnectionPoolTest() {}

    @Test
    public void getInstance() {
        assertNotNull(ConnectionPool.getInstance());
    }

    @Test
    public void getSize() {
        int actual = ConnectionPool.getInstance().getSize();
        assertEquals(expectedSize, actual);
    }

    @Test
    public void getConnection() {
        ConnectionProxy connection = ConnectionPool.getInstance().getConnection();
        assertNotNull(connection);
        connection.close();
    }
}

