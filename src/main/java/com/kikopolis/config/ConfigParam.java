package com.kikopolis.config;

public enum ConfigParam {
    APP_NAME("app.name"),
    APP_VERSION("app.version"),
    WIDTH("main.window.width"),
    HEIGHT("main.window.height"),
    ICON_PATH("app.icon.path");
    
    private final String key;
    
    ConfigParam(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
}
