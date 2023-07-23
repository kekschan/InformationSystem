package ru.practice.server.exception;

public class WagonNotFoundException extends RuntimeException {
    public WagonNotFoundException() {
        super("Wagon not found.");
    }

    public WagonNotFoundException(String message) {
        super(message);
    }

    public WagonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
