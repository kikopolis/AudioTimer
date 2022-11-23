package com.kikopolis.event;

import org.slf4j.Logger;

public class TestEscapePressedEvent implements Event {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TestEscapePressedEvent.class.getName());
    private final String message;
    
    public TestEscapePressedEvent(final String message) {
        LOGGER.debug("TestEscapePressedEvent created");
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
