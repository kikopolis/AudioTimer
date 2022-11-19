package com.kikopolis.config;

import com.kikopolis.config.exception.ConfigFileDoesNotExistException;
import com.kikopolis.config.exception.UnableToWriteConfigDataToDiskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);
    private static final String WIDTH_PARAM_NAME = "width=";
    private static final String HEIGHT_PARAM_NAME = "height=";
    private static final String ICON_PARAM_NAME = "icon=";
    private static final String APP_NAME_PARAM_NAME = "appName=";
    private static final String APP_VERSION_PARAM_NAME = "appVersion=";
    private final Config config;
    private final String configFilePath;
    private final File configFile;
    
    public ConfigManager() {
        configFilePath = getConfigFilePath();
        configFile = new File(configFilePath);
        config = new Config();
        if (!configFile.exists() && !createConfigFile() && !configFile.canRead()) {
            throw new ConfigFileDoesNotExistException(configFilePath);
        }
        readFromFile();
    }
    
    public Config getConfig() {
        return config;
    }
    
    public void saveConfig() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write(WIDTH_PARAM_NAME + config.getWidth());
            writer.newLine();
            writer.write(HEIGHT_PARAM_NAME + config.getHeight());
            writer.newLine();
            writer.write(ICON_PARAM_NAME + config.getIcon());
            writer.newLine();
            writer.write(APP_NAME_PARAM_NAME + config.getAppName());
            writer.newLine();
            writer.write(APP_VERSION_PARAM_NAME + config.getAppVersion());
        } catch (IOException e) {
            throw new UnableToWriteConfigDataToDiskException(configFilePath);
        }
    }
    
    private void readFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(configFilePath))) {
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
    }
    
    private boolean createConfigFile() {
        File file = new File(configFilePath);
        try {
            if (!configFile.getParentFile().exists() && !configFile.getParentFile().mkdirs()) {
                return false;
            }
            return file.createNewFile();
        } catch (IOException e) {
            LOGGER.error("Could not create configuration file", e);
        }
        return false;
    }
    
    private static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".kikopolis" + File.separator + "data";
    }
    
    private static String getConfigFilePath() {
        return getUserDataDirectory() + File.separator + "config";
    }
}
