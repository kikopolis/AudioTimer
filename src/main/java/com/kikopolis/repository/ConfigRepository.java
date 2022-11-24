package com.kikopolis.repository;

import com.kikopolis.config.ConfigKey;
import com.kikopolis.util.DirectoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class ConfigRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigRepository.class);
    private static final File file = new File(DirectoryUtil.DATA_DIR + File.separator + ".config");
    
    public void save(final Map<ConfigKey, String> config) {
        write(config);
    }
    
    public Map<ConfigKey, String> all() {
        return read();
    }
    
    public String get(final ConfigKey key) {
        return read().get(key);
    }
    
    public void set(final ConfigKey key, final String value) {
        EnumMap<ConfigKey, String> config = read();
        config.put(key, value);
        write(config);
    }
    
    private EnumMap<ConfigKey, String> read() {
        EnumMap<ConfigKey, String> configMap = new EnumMap<>(ConfigKey.class);
        if (canReadConfigFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                String line = br.readLine();
                while (line != null) {
                    if (line.contains("=")) {
                        String[] split = line.split("=");
                        ConfigKey key = ConfigKey.fromString(split[0]);
                        if (key != null) {
                            configMap.put(key, split[1]);
                        }
                    }
                    line = br.readLine();
                }
            } catch (IOException e) {
                LOGGER.error("%{}. Using DEFAULT configuration.", e.getMessage());
            }
        } else {
            LOGGER.error("Unable to read config file. Using DEFAULT configuration.");
        }
        return configMap;
    }
    
    private void write(final Map<ConfigKey, String> config) {
        Map<ConfigKey, String> configMap = read();
        configMap.putAll(config);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<ConfigKey, String> entry : config.entrySet()) {
                writer.write(entry.getKey().getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("Unable to write config data to disk", e);
        }
    }
    
    private boolean canReadConfigFile() {
        return file.exists() && file.canRead();
    }
}
