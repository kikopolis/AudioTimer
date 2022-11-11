package com.kikopolis;

import com.kikopolis.config.WindowConfigurator;

public class App {
    private static final WindowConfigurator windowConfigurator = new WindowConfigurator();
    
    // TODO: Make main window that would hold the event list, and misc config
    // TODO: Make event editor with choices - time, day or date, repeatable, unique or default sound
    // TODO: Make audio selection window with a test player and enable audio length limiting in the config
    // TODO: Implement configuration adapter, ie. independent of the configuration file format or data location
    // TODO: Implement a wide range of audio file formats
    public static void main(String[] args) {
        windowConfigurator.setOsDefaults();
    }
}
