package ru.practice.server.exception;

public class WagonNotFoundException extends Exception{
    public WagonNotFoundException() {
        super("Вагон не найден.");
    }

    public WagonNotFoundException(String message) {
        super(message);
    }

    public WagonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
