package com.kikopolis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
    
    public Config(final String appDataDir) {
        dataDir = appDataDir;
        width = Defaults.DEFAULT_WIDTH;
        height = Defaults.DEFAULT_HEIGHT;
        iconPath = dataDir + File.separator + "icon.png";
        appName = Defaults.DEFAULT_APP_NAME;
        appVersion = Defaults.DEFAULT_APP_VERSION;
        icon = loadIcon();
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
            setWindowsConfig();
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
        File iconFile = new File(iconPath);
        if (!iconFile.exists()) {
            copyIconToAppDataDir();
        }
        try {
            return ImageIO.read(iconFile);
        } catch (IOException e) {
            LOGGER.error("Unable to read icon from path: {}", iconPath);
        }
        return null;
    }
    
    private void copyIconToAppDataDir() {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(Defaults.DEFAULT_ICON_PATH));
            ImageIO.write(image, "png", new File(getIconPath()));
        } catch (IOException e) {
            LOGGER.error("Could not copy icon to app data dir", e);
        }
    }
}
