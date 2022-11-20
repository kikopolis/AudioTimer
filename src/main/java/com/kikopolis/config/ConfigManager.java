package com.kikopolis.config;

import com.kikopolis.event.Event;

import java.util.List;
import java.util.Map;

public class ConfigManager {
    private final ConfigWriterAndReader configWriterAndReader;
    private final EventWriterAndReader eventDiskWriterAndReader;
    
    public ConfigManager(final ConfigWriterAndReader configWriterAndReader, final EventWriterAndReader eventDiskWriterAndReader) {
        this.eventDiskWriterAndReader = eventDiskWriterAndReader;
        this.configWriterAndReader = configWriterAndReader;
    }
    
    public void saveConfig(final Map<String, String> configData) {
        configWriterAndReader.write(configData);
    }
    
    public Map<String, String> readConfig() {
        return configWriterAndReader.read();
    }
    
    public void saveEvents(List<Event> events) {
        eventDiskWriterAndReader.write(events);
    }
    
    public List<Event> readEvents() {
        return eventDiskWriterAndReader.read();
    }
}
