package com.kikopolis.gui.frame;

import com.kikopolis.config.Config;
import com.kikopolis.config.ConfigManager;

import javax.swing.*;
import java.awt.*;

public class ApplicationMainWindow extends JFrame {
    private final ConfigManager configManager;
    private final Config config;
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    
    public ApplicationMainWindow() {
        configManager = new ConfigManager();
        config = configManager.readFromDisk();
        init();
        // TODO: add components
        setVisible(true);
    }
    
    private void init() {
        configureLayout();
        configureWindow();
        addConfigShutDownHook();
    }
    
    private void configureLayout() {
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(layout);
    }
    
    private void configureWindow() {
        setTitle(config.getAppName());
        setSize(config.getWidth(), config.getHeight());
        setIconImage(config.getIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void addConfigShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(configManager::writeToDisk));
    }
}
