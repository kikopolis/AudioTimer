package com.kikopolis.service;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.repository.ConfigRepository;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.kikopolis.util.DirectoryUtil.DATA_DIR;

public class ConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigService.class);
    private static final List<String> ALLOWED_TO_READ_AND_WRITE =
            List.of(
                    ConfigKey.HEIGHT_KEY.getKey(),
                    ConfigKey.WIDTH_KEY.getKey(),
                    ConfigKey.ICON_PATH_KEY.getKey(),
                    ConfigKey.LOG_FILE_PATH_KEY.getKey()
                   );
    private static final Map<ConfigKey, String> DEFAULTS =
            Map.of(
                    ConfigKey.APP_NAME_KEY, "Kikopolis Audio Timer",
                    ConfigKey.APP_VERSION_KEY, "0.0.1",
                    ConfigKey.WIDTH_KEY, "1024",
                    ConfigKey.HEIGHT_KEY, "768",
                    ConfigKey.ICON_PATH_KEY, Objects.requireNonNull(ConfigService.class.getResource("/images/icon/icon.png")).getPath(),
                    ConfigKey.LOG_FILE_PATH_KEY, ""
                  );
    private final ConfigRepository configRepository;
    
    @Inject
    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }
    
    public static Map<ConfigKey, String> getDefaults() {
        return DEFAULTS;
    }
    
    public static Color getMainColor() {
        return new Color(56, 159, 190);
    }
    
    public static Color getHighlightColor() {
        return new Color(142, 150, 0);
    }
    
    public static Color getBackgroundColor() {
        return new Color(232, 232, 232);
    }
    
    public String get(final ConfigKey key) {
        String value = configRepository.get(key);
        if (value == null && DEFAULTS.containsKey(key)) {
            value = DEFAULTS.get(key);
        } else if (value == null) {
            LOGGER.error("No value found for key: \"{}\" in config file, or default", key.getKey());
        }
        return value;
    }
    
    public Integer getInt(final ConfigKey key) {
        return Integer.parseInt(get(key));
    }
    
    public void set(final ConfigKey key, final String value) {
        if (ALLOWED_TO_READ_AND_WRITE.contains(key.getKey())) {
            configRepository.set(key, value);
        } else {
            LOGGER.error("Unable to set config key: \"{}\". Not allowed to read and write.", key.getKey());
        }
    }
    
    public boolean isDataValid(final Map<String, String> data) {
        for (String key : data.keySet()) {
            if (!ALLOWED_TO_READ_AND_WRITE.contains(key)) {
                LOGGER.warn("Invalid key {} found in configuration data.", key);
                return false;
            }
        }
        return true;
    }
    
    public static List<String> getListOfValidKeys() {
        return ALLOWED_TO_READ_AND_WRITE;
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
    
    public Image loadAppIcon() {
        BufferedImage icon = null;
        String path = get(ConfigKey.ICON_PATH_KEY);
        if (path == null) {
            path = DEFAULTS.get(ConfigKey.ICON_PATH_KEY);
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
