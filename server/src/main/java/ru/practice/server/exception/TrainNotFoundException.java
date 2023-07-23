package ru.practice.server.exception;

public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException() {
        super("Train not found.");
    }

    public TrainNotFoundException(String message) {
        super(message);
    }

    public TrainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
