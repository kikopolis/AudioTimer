package com.kikopolis.config;

import java.awt.*;

public class Defaults {
    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 768;
    public static final Image DEFAULT_ICON = Toolkit.getDefaultToolkit().getImage(Defaults.class.getResource("/images/icon/icon.png"));
    public static final String DEFAULT_APP_NAME = "Kikopolis";
    public static final String DEFAULT_APP_VERSION = "0.0.1";
    
    private Defaults() {
    }
}
