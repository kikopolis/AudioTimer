package com.kikopolis.config;

import java.awt.*;

public class UiConstants {
    private UiConstants() {
    }
    
    public static final String APP_NAME = "Kikopolis";
    public static final String APP_VERSION = "0.0.1";
    
    public static final Image APP_ICON = Toolkit.getDefaultToolkit().getImage(UiConstants.class.getResource("/images/icon.png"));
}
