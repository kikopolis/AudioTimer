package com.kikopolis.config;

import com.kikopolis.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.kikopolis.config.ConfigParams.APP_NAME;
import static com.kikopolis.config.ConfigParams.APP_VERSION;
import static com.kikopolis.config.ConfigParams.DATA_DIR;
import static com.kikopolis.config.ConfigParams.HEIGHT;
import static com.kikopolis.config.ConfigParams.ICON_PATH;
import static com.kikopolis.config.ConfigParams.WIDTH;

public class ConfigWriterAndReaderKeyValue implements ConfigWriterAndReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigWriterAndReaderKeyValue.class);
    private final String appDataDir;
    private final String configFilePath;
    private final File configFile;
    
    public ConfigWriterAndReaderKeyValue(final String appDataDir) {
        this.appDataDir = appDataDir;
        configFilePath = appDataDir + File.separator + ".config";
        configFile = new File(configFilePath);
    }
    
    @Override
    public void write(final Map<String, String> configData) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            for (Map.Entry<String, String> entry : configData.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("Unable to write config data to disk", e);
        }
    }
    
    @Override
    public Map<String, String> read() {
        HashMap<String, String> configMap = new HashMap<>();
        if (!FileUtil.createFileIfNotExists(configFilePath)) {
            return configMap;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(configFilePath))) {
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith(WIDTH)) {
                    configMap.put(WIDTH, line.split("=")[1]);
                } else if (line.startsWith(HEIGHT)) {
                    configMap.put(HEIGHT, line.split("=")[1]);
                } else if (line.startsWith(ICON_PATH)) {
                    configMap.put(ICON_PATH, line.split("=")[1]);
                } else if (line.startsWith(APP_NAME)) {
                    configMap.put(APP_NAME, line.split("=")[1]);
                } else if (line.startsWith(APP_VERSION)) {
                    configMap.put(APP_VERSION, line.split("=")[1]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            LOGGER.error("%{}. Using DEFAULT configuration.", e.getMessage());
        }
        // add app data directory to config
        configMap.put(DATA_DIR, appDataDir);
        return configMap;
    }
}
