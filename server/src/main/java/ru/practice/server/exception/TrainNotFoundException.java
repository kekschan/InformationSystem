package ru.practice.server.exception;

public class TrainNotFoundException extends Exception {
    public TrainNotFoundException() {
        super("Поезд не найден.");
    }

    public TrainNotFoundException(String message) {
        super(message);
    }

    public TrainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
