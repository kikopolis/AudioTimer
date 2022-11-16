package com.kikopolis.config;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ConcurrentModificationException;

import com.kikopolis.config.exception.ConfigFileDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);
    private static final String CONFIG_FILE_NAME = "config";
    private static final String WIDTH_PARAM_NAME = "width=";
    private static final String HEIGHT_PARAM_NAME = "height=";
    private static final String ICON_PARAM_NAME = "icon=";
    private static final String APP_NAME_PARAM_NAME = "appName=";
    private static final String APP_VERSION_PARAM_NAME = "appVersion=";
    private final Config config;
    
    public ConfigManager() {
        config = new Config();
    }
    
    public Config readFromDisk() {
        return readFromFile(getConfigurationFile());
    }
    
    public void writeToDisk() {
    
    }
    
    private Config readFromFile(File configFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith(WIDTH_PARAM_NAME)) {
                    config.setWidth(Integer.parseInt(line.substring(WIDTH_PARAM_NAME.length())));
                } else if (line.startsWith(HEIGHT_PARAM_NAME)) {
                    config.setHeight(Integer.parseInt(line.substring(HEIGHT_PARAM_NAME.length())));
                } else if (line.startsWith(ICON_PARAM_NAME)) {
                    config.setIcon(new ImageIcon(line.substring(ICON_PARAM_NAME.length())).getImage());
                } else if (line.startsWith(APP_NAME_PARAM_NAME)) {
                    config.setAppName(line.substring(APP_NAME_PARAM_NAME.length()));
                } else if (line.startsWith(APP_VERSION_PARAM_NAME)) {
                    config.setAppVersion(line.substring(APP_VERSION_PARAM_NAME.length()));
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            LOGGER.error("%{}. Using DEFAULT configuration.", e.getMessage());
        }
        return config;
    }
    
    private File getConfigurationFile() throws RuntimeException {
        File file = new File(getConfigurationFilePath());
        if (!file.exists() || !file.canRead()) {
            throw new ConfigFileDoesNotExistException(file.getPath());
        }
        return file;
    }
    
    private String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".kikopolis" + File.separator + "data";
    }
    
    private String getConfigurationFilePath() {
        return getUserDataDirectory() + File.separator + CONFIG_FILE_NAME;
    }
}
