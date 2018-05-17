package com.epam.cafe.exception;

public class CafeException extends Exception {
    CafeException() {}

    CafeException(String message) {
        super(message);
    }
    CafeException(String message, Throwable cause) {
        super(message, cause);
    }

    CafeException(Throwable cause) {
        super(cause);
    }

    CafeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

