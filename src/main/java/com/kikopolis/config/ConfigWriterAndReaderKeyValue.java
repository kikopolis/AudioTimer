package com.kikopolis.config;

import com.google.inject.Inject;
import com.kikopolis.util.DirectoryUtil;
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

public class ConfigWriterAndReaderKeyValue implements ConfigWriterAndReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigWriterAndReaderKeyValue.class);
    private final String configFilePath;
    private final File configFile;
    
    @Inject
    public ConfigWriterAndReaderKeyValue() {
        configFilePath = DirectoryUtil.DATA_DIR + File.separator + ".config";
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
                if (line.contains("=")) {
                    String[] split = line.split("=");
                    configMap.put(split[0], split[1]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            LOGGER.error("%{}. Using DEFAULT configuration.", e.getMessage());
        }
        return configMap;
    }
}
