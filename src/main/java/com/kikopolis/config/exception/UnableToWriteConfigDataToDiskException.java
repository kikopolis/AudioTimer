package com.kikopolis.config.exception;

public class UnableToWriteConfigDataToDiskException extends RuntimeException {
    public UnableToWriteConfigDataToDiskException(final String filePath) {
        super("Unable to write to config file \"%s\"".formatted(filePath));
    }
}
