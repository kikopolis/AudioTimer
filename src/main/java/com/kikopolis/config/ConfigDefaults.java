package com.kikopolis.config;

import java.util.Objects;

public enum ConfigDefaults {
    WIDTH(ConfigParam.WIDTH, "1024"),
    HEIGHT(ConfigParam.HEIGHT, "768"),
    APP_NAME(ConfigParam.APP_NAME, "Kikopolis Audio Timer"),
    APP_VERSION(ConfigParam.APP_VERSION, "0.0.1"),
    ICON_PATH(ConfigParam.ICON_PATH, Objects.requireNonNull(ConfigDefaults.class.getResource("/images/icon/icon.png")).getPath());
    
    private final ConfigParam key;
    private final String value;
    
    ConfigDefaults(final ConfigParam key, final String value) {
        this.key = key;
        this.value = value;
    }
    
    public static String getDefaultValue(final ConfigParam configParam) {
        for (ConfigDefaults configDefault : ConfigDefaults.values()) {
            if (configDefault.key == configParam) {
                return configDefault.value;
            }
        }
        return null;
    }
    
    public ConfigParam getKey() {
        return key;
    }
    
    public String getValue() {
        return value;
    }
}
