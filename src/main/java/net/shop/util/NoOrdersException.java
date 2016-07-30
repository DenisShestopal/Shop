package net.shop.util;

public class NoOrdersException extends Exception {
    public NoOrdersException() {
    }

    public NoOrdersException(String message) {
        super(message);
    }
}
