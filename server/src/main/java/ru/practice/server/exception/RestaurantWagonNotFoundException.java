package ru.practice.server.exception;

public class RestaurantWagonNotFoundException extends RuntimeException{
    public RestaurantWagonNotFoundException() {
        super("Maximum wagon limit exceeded.");
    }

    public RestaurantWagonNotFoundException(String message) {
        super(message);
    }

    public RestaurantWagonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
