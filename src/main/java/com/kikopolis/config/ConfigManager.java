package com.kikopolis.config;

import com.kikopolis.config.exception.UnableToCreateConfigFileAtPathException;
import com.kikopolis.config.exception.UnableToCreateAppDataDirectoryException;
import com.kikopolis.config.exception.UnableToWriteConfigDataToDiskException;
import com.kikopolis.config.exception.UnableToWriteEventsDataToDiskException;
import com.kikopolis.event.EventManager;
import com.kikopolis.event.ScheduledEventManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);
    private static final String WIDTH_PARAM_NAME = "width=";
    private static final String HEIGHT_PARAM_NAME = "height=";
    private static final String ICON_PARAM_NAME = "icon=";
    private static final String APP_NAME_PARAM_NAME = "appName=";
    private static final String APP_VERSION_PARAM_NAME = "appVersion=";
    private final Config config;
    private final EventManager eventManager;
    private final String configFilePath;
    private final String eventFilePath;
    private final File configFile;
    private final File eventFile;
    
    public ConfigManager() {
        configFilePath = Defaults.APP_DATA_DIR + File.separator + ".config";
        eventFilePath = Defaults.APP_DATA_DIR + File.separator + ".events";
        configFile = new File(configFilePath);
        eventFile = new File(eventFilePath);
        createAppDataDirectory();
        createConfigFileIfNotExists();
        createEventFileIfNotExists();
        config = new Config(Defaults.APP_DATA_DIR);
        readFromFile();
        eventManager = new ScheduledEventManager();
    }
    
    public Config getConfig() {
        return config;
    }
    
    public void save() {
        saveConfig();
        saveEvents();
    }
    
    public void saveConfig() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write(WIDTH_PARAM_NAME + config.getWidth());
            writer.newLine();
            writer.write(HEIGHT_PARAM_NAME + config.getHeight());
            writer.newLine();
            writer.write(ICON_PARAM_NAME + config.getIconPath());
            writer.newLine();
            writer.write(APP_NAME_PARAM_NAME + config.getAppName());
            writer.newLine();
            writer.write(APP_VERSION_PARAM_NAME + config.getAppVersion());
        } catch (IOException e) {
            LOGGER.error("Unable to write config data to disk", e);
            throw new UnableToWriteConfigDataToDiskException(configFilePath);
        }
    }
    
    private void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(eventFile, false))) {
            oos.writeObject(eventManager.getEvents());
        } catch (IOException e) {
            LOGGER.error("Unable to write event data to disk", e);
            throw new UnableToWriteEventsDataToDiskException(eventFilePath);
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
    
    private void createConfigFileIfNotExists() {
        boolean created = false;
        try {
            File file = new File(configFilePath);
            created = file.createNewFile();
        } catch (IOException e) {
            LOGGER.error("Unable to create config file at path: {}", configFilePath);
        }
        if (!created) {
            throw new UnableToCreateConfigFileAtPathException(configFilePath);
        }
    }
    
    private void createEventFileIfNotExists() {
        boolean created = false;
        try {
            File file = new File(eventFilePath);
            created = file.createNewFile();
        } catch (IOException e) {
            LOGGER.error("Unable to create event file at path: {}", eventFilePath);
        }
        if (!created) {
            throw new UnableToCreateConfigFileAtPathException(eventFilePath);
        }
    }
    
    private static void createAppDataDirectory() {
        boolean created = false;
        File dir = new File(Defaults.APP_DATA_DIR);
        if (!dir.exists()) {
            created = dir.mkdirs();
        }
        if (!dir.isDirectory() || !created) {
            throw new UnableToCreateAppDataDirectoryException(Defaults.APP_DATA_DIR);
        }
    }
}
