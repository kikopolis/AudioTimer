package com.kikopolis.config;

import java.awt.*;

import static com.kikopolis.util.SystemInfo.isLinux;
import static com.kikopolis.util.SystemInfo.isMacOs;
import static com.kikopolis.util.SystemInfo.isWindows;

public class Config {
    private int width;
    private int height;
    private Image icon;
    private String appName;
    private String appVersion;
    
    public Config() {
        this.width = Defaults.DEFAULT_WIDTH;
        this.height = Defaults.DEFAULT_HEIGHT;
        this.icon = Defaults.DEFAULT_ICON;
        this.appName = Defaults.DEFAULT_APP_NAME;
        this.appVersion = Defaults.DEFAULT_APP_VERSION;
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
}
