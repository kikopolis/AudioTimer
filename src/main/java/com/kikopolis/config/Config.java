package com.kikopolis.config;

import com.kikopolis.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.kikopolis.config.ConfigParams.APP_NAME;
import static com.kikopolis.config.ConfigParams.APP_VERSION;
import static com.kikopolis.config.ConfigParams.HEIGHT;
import static com.kikopolis.config.ConfigParams.ICON_PATH;
import static com.kikopolis.config.ConfigParams.WIDTH;
import static com.kikopolis.util.SystemInfo.isLinux;
import static com.kikopolis.util.SystemInfo.isMacOs;
import static com.kikopolis.util.SystemInfo.isWindows;

public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private final String dataDir;
    private int width;
    private int height;
    private Image icon;
    private String iconPath;
    private String appName;
    private String appVersion;
    
    public Config(final Map<String, String> configData) {
        // get values if present and if not set from defaults
        this.dataDir = configData.getOrDefault(ConfigParams.DATA_DIR, Defaults.DATA_DIR);
        this.width = Integer.parseInt(configData.getOrDefault(WIDTH, String.valueOf(Defaults.WIDTH)));
        this.height = Integer.parseInt(configData.getOrDefault(HEIGHT, String.valueOf(Defaults.HEIGHT)));
        this.appName = configData.getOrDefault(APP_NAME, Defaults.APP_NAME);
        this.appVersion = configData.getOrDefault(APP_VERSION, Defaults.APP_VERSION);
        this.iconPath = configData.get(ICON_PATH);
        icon = loadIcon();
    }
    
    public String getDataDir() {
        return dataDir;
    }
    
    public int getWidth() {
        return width;
    }
    
    public Config setWidth(int width) {
        this.width = width;
        return this;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Config setHeight(int height) {
        this.height = height;
        return this;
    }
    
    public Image getIcon() {
        return icon;
    }
    
    public Config setIcon(Image icon) {
        this.icon = icon;
        return this;
    }
    
    public String getIconPath() {
        return iconPath;
    }
    
    public Config setIconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }
    
    public String getAppName() {
        return appName;
    }
    
    public Config setAppName(String appName) {
        this.appName = appName;
        return this;
    }
    
    public String getAppVersion() {
        return appVersion;
    }
    
    public Config setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
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
        System.setProperty("apple.awt.application.name", appName);
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", appName);
        System.setProperty("apple.awt.application.appearance", "system");
    }
    
    private void setLinuxConfig() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
    }
    
    private void setWindowsConfig() {
        // TODO: Implement
    }
    
    private Image loadIcon() {
        String path = iconPath;
        if (path == null) {
            path = Defaults.ICON_PATH;
        }
        File iconFile = new File(path);
        // Check if the icon file exists in the app data directory and copy if it doesn't
        File dataDirIconFile = new File(dataDir + File.separator + iconFile.getName());
        if (!dataDirIconFile.exists()) {
            FileUtil.copyImageToDirectory(iconFile.getPath(), dataDir);
        }
        try {
            return ImageIO.read(iconFile);
        } catch (IOException e) {
            LOGGER.error("Unable to read icon from path: {}", path);
        }
        return null;
    }
    
    public Map<String, String> getConfigMap() {
        return Map.of(
                WIDTH, String.valueOf(width),
                HEIGHT, String.valueOf(height),
                ICON_PATH, String.valueOf(iconPath),
                APP_NAME, String.valueOf(appName),
                APP_VERSION, String.valueOf(appVersion)
                     );
    }
}
