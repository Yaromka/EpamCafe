package com.epam.cafe.exception;

public class ConnectionPoolException extends Exception{

    public ConnectionPoolException(String s) {
        super(s);
    }

    public ConnectionPoolException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
