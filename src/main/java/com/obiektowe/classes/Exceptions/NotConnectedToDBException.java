package com.obiektowe.classes.Exceptions;

public class NotConnectedToDBException extends Exception {

    public NotConnectedToDBException(String message) {
        super(message);
    }
}
