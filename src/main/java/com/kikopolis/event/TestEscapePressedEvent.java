package com.kikopolis.event;

public class TestEscapePressedEvent {
    private final String message;
    
    public TestEscapePressedEvent(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
