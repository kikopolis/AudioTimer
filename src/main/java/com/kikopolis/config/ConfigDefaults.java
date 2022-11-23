package com.kikopolis.config;

import java.util.Objects;

public enum ConfigDefaults {
    WIDTH(ConfigKey.WIDTH_KEY, "1024"),
    HEIGHT(ConfigKey.HEIGHT_KEY, "768"),
    APP_NAME(ConfigKey.APP_NAME_KEY, "Kikopolis Audio Timer"),
    APP_VERSION(ConfigKey.APP_VERSION_KEY, "0.0.1"),
    ICON_PATH(ConfigKey.ICON_PATH_KEY, Objects.requireNonNull(ConfigDefaults.class.getResource("/images/icon/icon.png")).getPath()),
    LOG_FILE_PATH(ConfigKey.LOG_FILE_PATH_KEY, "");
    
    private final ConfigKey key;
    private final String value;
    
    ConfigDefaults(final ConfigKey key, final String value) {
        this.key = key;
        this.value = value;
    }
    
    public static String getDefaultValue(final ConfigKey configKey) {
        for (ConfigDefaults configDefault : ConfigDefaults.values()) {
            if (configDefault.key == configKey) {
                return configDefault.value;
            }
        }
        return null;
    }
    
    public ConfigKey getKey() {
        return key;
    }
    
    public String getValue() {
        return value;
    }
}
