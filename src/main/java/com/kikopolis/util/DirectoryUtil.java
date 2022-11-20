package com.kikopolis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.kikopolis.config.ConfigDefaults.APP_NAME;

public final class DirectoryUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryUtil.class.getName());
    public static final String DATA_DIR = System.getProperty("user.home") + File.separator + ".kikopolis" + File.separator + APP_NAME.getValue();
    
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
