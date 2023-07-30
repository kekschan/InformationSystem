package ru.practice.server.exception;

public class TrainAlreadyExistsException extends Exception {
    public TrainAlreadyExistsException() {
        super("Train not found.");
    }

    public TrainAlreadyExistsException(String message) {
        super(message);
    }

    public TrainAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
