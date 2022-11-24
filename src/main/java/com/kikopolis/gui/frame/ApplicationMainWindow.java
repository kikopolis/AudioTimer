package com.kikopolis.gui.frame;

import com.kikopolis.config.ConfigKey;
import com.kikopolis.config.Configuration;
import com.kikopolis.gui.button.RefreshButton;
import com.kikopolis.gui.panel.EpisodeList;
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
        
        // Add a button to refresh the event list
        add(new RefreshButton(), gbc);
        
        // TODO: refactor out all components
        EpisodeList episodeList = new EpisodeList();
        add(episodeList, gbc);
        episodeList.setVisible(true);
    }
    
    private void init() {
        configureLayout();
        configureWindow();
    }
    
    private void configureLayout() {
        setLayout(layout);
    }
    
    private void configureWindow() {
        setTitle(config.get(ConfigKey.APP_NAME_KEY));
        setSize(config.getInt(ConfigKey.WIDTH_KEY), config.getInt(ConfigKey.HEIGHT_KEY));
        setIconImage(config.loadAppIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
