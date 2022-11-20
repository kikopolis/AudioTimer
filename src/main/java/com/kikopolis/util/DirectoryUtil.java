package com.kikopolis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class DirectoryUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryUtil.class.getName());
    
    private DirectoryUtil() {
    }
    
    public static void createDirectoryIfNotExists(final String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                LOGGER.warn("Could not create directory: {}", path);
            }
        }
    }
}
