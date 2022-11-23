package com.kikopolis.config;

import com.google.inject.Inject;
import org.apache.commons.lang3.SystemUtils;
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

public class AppConfig implements Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
    private final ConfigWriterAndReader configWriterAndReader;
    private final Map<String, String> configData;
    private static final List<String>
            ALLOWED_TO_READ_AND_WRITE = List.of(
            ConfigKey.HEIGHT_KEY.getKey(),
            ConfigKey.WIDTH_KEY.getKey(),
            ConfigKey.ICON_PATH_KEY.getKey(),
            ConfigKey.LOG_FILE_PATH_KEY.getKey()
                                               );
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
        for (ConfigKey configKey : ConfigKey.values()) {
            if (rawData.containsKey(configKey.getKey()) && ALLOWED_TO_READ_AND_WRITE.contains(configKey.getKey())) {
                validatedData.put(configKey.getKey(), rawData.get(configKey.getKey()));
            } else {
                validatedData.put(configKey.getKey(), ConfigDefaults.getDefaultValue(configKey));
            }
        }
        return validatedData;
    }
    
    @Override
    public String get(ConfigKey key) {
        final long stamp = lock.readLock();
        try {
            return configData.getOrDefault(key.getKey(), null);
        } finally {
            lock.unlockRead(stamp);
        }
    }
    
    @Override
    public Integer getInt(ConfigKey key) {
        final long stamp = lock.readLock();
        try {
            return Integer.parseInt(configData.getOrDefault(key.getKey(), null));
        } finally {
            lock.unlockRead(stamp);
        }
    }
    
    @Override
    public void set(ConfigKey key, String value) {
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
        for (ConfigKey configKey : ConfigKey.values()) {
            if (ALLOWED_TO_READ_AND_WRITE.contains(configKey.getKey())) {
                final long stamp = lock.readLock();
                try {
                    dataToSave.put(configKey.getKey(), configData.get(configKey.getKey()));
                } finally {
                    lock.unlockRead(stamp);
                }
            }
        }
        configWriterAndReader.write(dataToSave);
    }
    
    public Image loadAppIcon() {
        BufferedImage icon = null;
        String path = get(ConfigKey.ICON_PATH_KEY);
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
            set(ConfigKey.ICON_PATH_KEY, dataDirIconFile.getPath());
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
        if (SystemUtils.IS_OS_MAC_OSX) {
            setMacConfig();
        } else if (SystemUtils.IS_OS_WINDOWS) {
            setWindowsConfig();
        } else if (SystemUtils.IS_OS_LINUX) {
            setLinuxConfig();
        }
    }
    
    private void setMacConfig() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", get(ConfigKey.APP_NAME_KEY));
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", get(ConfigKey.APP_NAME_KEY));
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
