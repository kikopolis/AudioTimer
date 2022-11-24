package com.kikopolis.gui.frame;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.gui.button.RefreshButton;
import com.kikopolis.gui.panel.EpisodeList;
import com.kikopolis.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class ApplicationMainWindow extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMainWindow.class.getName());
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private final transient ConfigService configService;
    
    @Inject
    public ApplicationMainWindow(final ConfigService configService) {
        this.configService = configService;
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
        setTitle(configService.get(ConfigKey.APP_NAME_KEY));
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setIconImage(configService.loadAppIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
