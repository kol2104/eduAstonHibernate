package org.example.exception;

public class PositionNotFoundException extends RuntimeException{
    public PositionNotFoundException(Long id) {
        super(String.format("Position with id = '%s' not found", id));
    }
}
