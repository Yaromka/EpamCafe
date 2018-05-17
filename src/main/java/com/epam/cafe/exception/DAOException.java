package com.epam.cafe.exception;

public class DAOException extends Exception {

    public DAOException(String s) {
        super(s);
    }

    public DAOException(String s, Throwable throwable) {
        super(s, throwable);
    }
}

