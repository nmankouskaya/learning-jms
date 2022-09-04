package org.learning.exception;

public class UnprocessableNotificationException extends Exception {
    public UnprocessableNotificationException() {
    }

    public UnprocessableNotificationException(String message) {
        super(message);
    }

    public UnprocessableNotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
