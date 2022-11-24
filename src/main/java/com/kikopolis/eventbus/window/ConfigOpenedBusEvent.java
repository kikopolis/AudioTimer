package com.kikopolis.eventbus.window;

import com.kikopolis.config.ConfigKey;
import com.kikopolis.eventbus.BusEvent;
import com.kikopolis.gui.window.Config;

import java.util.EnumMap;
import java.util.Map;

public class ConfigOpenedBusEvent implements BusEvent {
    private final Config configWindow;
    private Map<ConfigKey, String> config;
    
    public ConfigOpenedBusEvent(final Config configWindow) {
        this(configWindow, new EnumMap<>(ConfigKey.class));
    }
    
    public ConfigOpenedBusEvent(final Config configWindow, final Map<ConfigKey, String> config) {
        this.configWindow = configWindow;
        this.config = config;
    }
    
    public Map<ConfigKey, String> getConfig() {
        return config;
    }
    
    public void setConfig(final Map<ConfigKey, String> config) {
        this.config = config;
    }
    
    public Config getConfigWindow() {
        return configWindow;
    }
}
