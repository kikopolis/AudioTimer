package com.kikopolis.config.exception;

public class UnableToCreateEventsFileAtPathException extends RuntimeException {
    public UnableToCreateEventsFileAtPathException(final String filePath) {
        super("Unable to create events file at path \"%s\"".formatted(filePath));
    }
}
