package com.kikopolis.eventbus.window;

import com.kikopolis.config.ConfigKey;
import com.kikopolis.eventbus.BusEvent;

import java.util.EnumMap;
import java.util.Map;

public class ConfigSavedBusEvent implements BusEvent {
    private Map<ConfigKey, String> config;
    
    public ConfigSavedBusEvent() {
        this.config = new EnumMap<>(ConfigKey.class);
    }
    
    public ConfigSavedBusEvent(final Map<ConfigKey, String> config) {
        this.config = config;
    }
    
    public Map<ConfigKey, String> getConfig() {
        return config;
    }
    
    public void setConfig(final Map<ConfigKey, String> config) {
        this.config = config;
    }
}
