package ru.practice.server.exception;

public class PassengerWagonNotFoundException extends RuntimeException{
    public PassengerWagonNotFoundException() {
        super("Maximum wagon limit exceeded.");
    }

    public PassengerWagonNotFoundException(String message) {
        super(message);
    }

    public PassengerWagonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
