package com.kikopolis.event;

public class TestEscapePressedEvent implements Event {
    private final String message;
    
    public TestEscapePressedEvent(final String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
