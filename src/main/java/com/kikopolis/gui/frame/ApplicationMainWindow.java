package com.kikopolis.gui.frame;

import com.kikopolis.config.Config;
import com.kikopolis.config.ConfigManager;

import javax.swing.*;
import java.awt.*;

public class ApplicationMainWindow extends JFrame {
    private static final ConfigManager configManager = new ConfigManager();
    private static final Config config = configManager.getConfig();
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    
    public ApplicationMainWindow() {
        init();
        // TODO: add components
        setVisible(true);
    }
    
    private void init() {
        config.setDefaultsByOperatingSystem();
        configureLayout();
        configureWindow();
        addConfigShutDownHook();
    }
    
    private void configureLayout() {
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
        Runtime.getRuntime().addShutdownHook(new Thread(configManager::saveConfig));
    }
}
