package com.kikopolis.config;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

import static com.kikopolis.util.DirectoryUtil.DATA_DIR;
import static com.kikopolis.util.SystemInfo.isLinux;
import static com.kikopolis.util.SystemInfo.isMacOs;
import static com.kikopolis.util.SystemInfo.isWindows;

public class AppConfig implements Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
    private final ConfigWriterAndReader configWriterAndReader;
    private final Map<String, String> configData;
    private static final List<String>
            ALLOWED_TO_READ_AND_WRITE = List.of(ConfigParam.HEIGHT.getKey(), ConfigParam.WIDTH.getKey(), ConfigParam.ICON_PATH.getKey());
    private final StampedLock lock;
    
    @Inject
    public AppConfig(final ConfigWriterAndReader configWriterAndReader) {
        this.configWriterAndReader = configWriterAndReader;
        this.lock = new StampedLock();
        final long stamp = lock.writeLock();
        try {
            this.configData = this.readAndValidateConfigData();
        } finally {
            lock.unlockWrite(stamp);
        }
        setDefaultsByOperatingSystem();
    }
    
    private Map<String, String> readAndValidateConfigData() {
        Map<String, String> rawData = configWriterAndReader.read();
        Map<String, String> validatedData = new HashMap<>();
        for (ConfigParam configParam : ConfigParam.values()) {
            if (rawData.containsKey(configParam.getKey()) && ALLOWED_TO_READ_AND_WRITE.contains(configParam.getKey())) {
                validatedData.put(configParam.getKey(), rawData.get(configParam.getKey()));
            } else {
                validatedData.put(configParam.getKey(), ConfigDefaults.getDefaultValue(configParam));
            }
        }
        return validatedData;
    }
    
    @Override
    public String get(ConfigParam key) {
        final long stamp = lock.readLock();
        try {
            return configData.getOrDefault(key.getKey(), null);
        } finally {
            lock.unlockRead(stamp);
        }
    }
    
    @Override
    public Integer getInt(ConfigParam key) {
        final long stamp = lock.readLock();
        try {
            return Integer.parseInt(configData.getOrDefault(key.getKey(), null));
        } finally {
            lock.unlockRead(stamp);
        }
    }
    
    @Override
    public void set(ConfigParam key, String value) {
        final long stamp = lock.writeLock();
        try {
            configData.put(key.getKey(), value);
            configWriterAndReader.write(configData);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    public void save() {
        HashMap<String, String> dataToSave = new HashMap<>();
        for (ConfigParam configParam : ConfigParam.values()) {
            if (ALLOWED_TO_READ_AND_WRITE.contains(configParam.getKey())) {
                final long stamp = lock.readLock();
                try {
                    dataToSave.put(configParam.getKey(), configData.get(configParam.getKey()));
                } finally {
                    lock.unlockRead(stamp);
                }
            }
        }
        configWriterAndReader.write(dataToSave);
    }
    
    public Image loadAppIcon() {
        BufferedImage icon = null;
        String path = get(ConfigParam.ICON_PATH);
        if (path == null) {
            path = ConfigDefaults.ICON_PATH.getValue();
        }
        File iconFile = new File(path);
        // Check if the icon file exists in the app data directory and copy if it doesn't
        File dataDirIconFile = new File(DATA_DIR + File.separator + iconFile.getName());
        if (!dataDirIconFile.exists()) {
            copyImageToDirectory(iconFile.getPath());
        }
        try {
            icon = ImageIO.read(dataDirIconFile);
            set(ConfigParam.ICON_PATH, dataDirIconFile.getPath());
        } catch (IOException e) {
            LOGGER.error("Unable to read icon from path: {}", path);
        }
        return icon;
    }
    
    private void copyImageToDirectory(final String imagePath) {
        File image = new File(imagePath);
        File directory = new File(DATA_DIR);
        if (image.exists() && directory.exists()) {
            try {
                Files.copy(image.toPath(), new File(DATA_DIR + File.separator + image.getName()).toPath());
            } catch (IOException e) {
                LOGGER.warn("Could not copy image to directory: {}", DATA_DIR);
            }
        }
    }
    
    public void setDefaultsByOperatingSystem() {
        if (isMacOs()) {
            setMacConfig();
        } else if (isWindows()) {
            setWindowsConfig();
        } else if (isLinux()) {
            setLinuxConfig();
        }
    }
    
    private void setMacConfig() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", get(ConfigParam.APP_NAME));
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", get(ConfigParam.APP_NAME));
        System.setProperty("apple.awt.application.appearance", "system");
    }
    
    private void setLinuxConfig() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }
    
    private void setWindowsConfig() {
        // TODO: Implement
    }
}
