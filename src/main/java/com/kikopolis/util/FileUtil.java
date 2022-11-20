package com.kikopolis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public final class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class.getName());
    
    private FileUtil() {
    }
    
    public static boolean createFileIfNotExists(final String path) {
        boolean created = false;
        File file = new File(path);
        if (!file.exists()) {
            try {
                created = file.createNewFile();
            } catch (IOException e) {
                LOGGER.warn("Could not create file: {}", path);
            }
        }
        return created;
    }
}
