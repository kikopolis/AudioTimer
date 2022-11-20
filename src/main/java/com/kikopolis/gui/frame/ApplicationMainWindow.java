package com.kikopolis.gui.frame;

import com.kikopolis.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class ApplicationMainWindow extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMainWindow.class.getName());
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private final transient Config config;
    
    public ApplicationMainWindow(final Config config) {
        this.config = config;
        init();
        // TODO: add components
        setVisible(true);
    }
    
    private void init() {
        configureLayout();
        configureWindow();
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
}
