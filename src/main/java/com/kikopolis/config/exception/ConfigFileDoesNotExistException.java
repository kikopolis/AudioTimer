package com.kikopolis.config.exception;

public class ConfigFileDoesNotExistException extends RuntimeException {
    public ConfigFileDoesNotExistException(final String filePath) {
        super("Config file - \"%s\" does not exist or is not readable".formatted(filePath));
    }
}
