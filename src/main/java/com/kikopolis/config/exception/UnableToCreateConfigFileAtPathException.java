package com.kikopolis.config.exception;

public class UnableToCreateConfigFileAtPathException extends RuntimeException {
    public UnableToCreateConfigFileAtPathException(final String filePath) {
        super("Unable to create config file to path \"%s\"".formatted(filePath));
    }
}
