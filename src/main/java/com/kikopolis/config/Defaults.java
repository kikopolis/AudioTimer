package com.kikopolis.config;

import java.io.File;

public final class Defaults {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final String DATA_DIR = System.getProperty("user.home") + File.separator + ".kikopolis" + File.separator + "audio_timer";
    public static final String ICON_PATH = Defaults.class.getResource("/images/icon/icon.png").getPath();
    public static final String APP_NAME = "Kikopolis";
    public static final String APP_VERSION = "0.0.1";
    
    private Defaults() {
    }
}
