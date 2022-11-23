package com.kikopolis.util;

import java.io.File;

import static com.kikopolis.config.ConfigDefaults.APP_NAME;

public final class DirectoryUtil {
    public static final String DATA_DIR = System.getProperty("user.home") + File.separator + ".kikopolis" + File.separator + APP_NAME.getValue();
    
    private DirectoryUtil() {
    }
}
