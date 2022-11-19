package com.kikopolis.config.exception;

public class UnableToCreateAppDataDirectoryException extends RuntimeException {
    public UnableToCreateAppDataDirectoryException(final String filePath) {
        super("Unable to create app data directory at \"%s\"".formatted(filePath));
    }
}
