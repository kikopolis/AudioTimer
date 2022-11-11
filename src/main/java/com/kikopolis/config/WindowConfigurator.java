package com.kikopolis.config;

import static com.kikopolis.util.SystemInfo.isLinux;
import static com.kikopolis.util.SystemInfo.isMacOs;
import static com.kikopolis.util.SystemInfo.isWindows;

public class WindowConfigurator {
    public void setOsDefaults() {
        if (isMacOs()) {
            setMacConfig();
        } else if (isWindows()) {
            setWindowsConfig();
        } else if (isLinux()) {
            setWindowsConfig();
        }
    }
    
    private void setMacConfig() {
//            java -Xdock:name="AppName" -Xdock:icon=macos_dock_icon.jpg
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", UiConstants.APP_NAME);
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", UiConstants.APP_NAME);
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
