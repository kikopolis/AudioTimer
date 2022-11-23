package com.kikopolis.gui.frame;

import com.kikopolis.core.Events;
import com.kikopolis.config.ConfigParam;
import com.kikopolis.config.Configuration;
import com.kikopolis.episode.EpisodeManager;
import com.kikopolis.event.EventSubscriber;
import com.kikopolis.event.TestEscapePressedEvent;
import com.kikopolis.gui.panel.EpisodeList;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class ApplicationMainWindow extends JFrame implements EventSubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMainWindow.class.getName());
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private final transient Configuration config;
    private EpisodeManager episodeManager;
    
    public ApplicationMainWindow(final Configuration config, final EpisodeManager episodeManager) {
        Events.subscribe(this);
        
        this.config = config;
        this.episodeManager = episodeManager;
        init();
        
        EpisodeList episodeList = new EpisodeList();
        add(episodeList, gbc);
        episodeList.display(episodeManager.getEpisodes());
        episodeList.setVisible(true);
        
        setVisible(true);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvt(final TestEscapePressedEvent event) {
        System.exit(0);
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
