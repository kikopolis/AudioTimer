package com.kikopolis.config;

import java.io.File;
import java.net.URL;

public class Defaults {
    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 768;
    public static final String APP_DATA_DIR = System.getProperty("user.home") + File.separator + ".kikopolis" + File.separator + "audio_timer";
    public static final URL DEFAULT_ICON_PATH = Defaults.class.getResource("/images/icon/icon.png");
    public static final String DEFAULT_APP_NAME = "Kikopolis";
    public static final String DEFAULT_APP_VERSION = "0.0.1";
    
    private Defaults() {
    }
}
