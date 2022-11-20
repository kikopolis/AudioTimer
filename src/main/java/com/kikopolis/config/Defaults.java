package com.kikopolis.config;

import java.io.File;
import java.util.Objects;

public final class Defaults {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final String APP_NAME = "Kikopolis Audio Timer";
    public static final String APP_VERSION = "0.0.1";
    public static final String ICON_PATH = Objects.requireNonNull(Defaults.class.getResource("/images/icon/icon.png")).getPath();
    public static final String DATA_DIR = System.getProperty("user.home") + File.separator + ".kikopolis" + File.separator + APP_NAME;
    
    private Defaults() {
    }
}
