package com.kikopolis.config.exception;

public class UnableToWriteEventsDataToDiskException extends RuntimeException {
    public UnableToWriteEventsDataToDiskException(final String filePath) {
        super("Unable to write to events file \"%s\"".formatted(filePath));
    }
}
