package ru.practice.server.exception;

public class MaxWagonLimitExceededException extends Exception{
    public MaxWagonLimitExceededException() {
        super("Превышено максимальное количество вагонов.");
    }

    public MaxWagonLimitExceededException(String message) {
        super(message);
    }

    public MaxWagonLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
