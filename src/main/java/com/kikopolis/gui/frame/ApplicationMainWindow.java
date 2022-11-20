package com.kikopolis.gui.frame;

import com.kikopolis.config.ConfigParam;
import com.kikopolis.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class ApplicationMainWindow extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMainWindow.class.getName());
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private final transient Configuration config;
    
    public ApplicationMainWindow(final Configuration config) {
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
        setTitle(config.get(ConfigParam.APP_NAME));
        setSize(config.getInt(ConfigParam.WIDTH), config.getInt(ConfigParam.HEIGHT));
        setIconImage(config.loadAppIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
