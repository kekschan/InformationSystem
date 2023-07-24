package ru.practice.server.exception;

public class MailWagonNotFoundException extends RuntimeException{
    public MailWagonNotFoundException() {
        super("Maximum wagon limit exceeded.");
    }

    public MailWagonNotFoundException(String message) {
        super(message);
    }

    public MailWagonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
