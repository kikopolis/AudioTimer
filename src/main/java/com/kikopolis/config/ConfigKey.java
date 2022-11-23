package com.kikopolis.config;

public enum ConfigKey {
    APP_NAME_KEY("app.name"),
    APP_VERSION_KEY("app.version"),
    WIDTH_KEY("main.window.width"),
    HEIGHT_KEY("main.window.height"),
    ICON_PATH_KEY("app.icon.path"),
    LOG_FILE_PATH_KEY("log.file.path");
    
    private final String key;
    
    ConfigKey(String key) {
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }
}
