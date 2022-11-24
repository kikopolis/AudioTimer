package com.kikopolis.util;

import com.kikopolis.config.ConfigKey;
import com.kikopolis.service.ConfigService;

import java.io.File;

public final class DirectoryUtil {
    public static final String DATA_DIR = System.getProperty("user.home")
            + File.separator
            + ".kikopolis"
            + File.separator
            + ConfigService.getDefaults().get(ConfigKey.APP_NAME_KEY);
    
    private DirectoryUtil() {
    }
}
