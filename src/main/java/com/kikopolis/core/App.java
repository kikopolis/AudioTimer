package com.kikopolis.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kikopolis.config.Configuration;
import com.kikopolis.config.logging.LogConfiguration;
import com.kikopolis.episode.EpisodeManager;
import com.kikopolis.event.TestEscapePressedEvent;
import com.kikopolis.gui.frame.ApplicationMainWindow;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.util.DirectoryUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class App {
    // TODO: Make main window that would hold the episode list, and misc config
    // TODO: Make episode editor with choices - time, day or date, repeatable, unique or default sound
    // TODO: Make audio selection window with a test player and enable audio length limiting in the config
    // TODO: Implement configuration adapter, ie. independent of the configuration file format or data location
    // TODO: Implement a wide range of audio file formats
    public static void main(String[] args) {
        // First make sure our data directory exists
        createAppDataDirectory();
        //Create Guice injector and configure dependencies in AppModule
        Injector injector = Guice.createInjector(new DependencyBindings());
        // Load the configuration
        Configuration config = injector.getInstance(Configuration.class);
        // Initialize logging, should either be done before any logging is done or a fallback logger should be configured.
        // However, the fallback logger should be cleared before configuring the actual logging in the application.
        // Or don't configure a logger here at all, configure it in some other way... Whatever you want. It's fine.
        // I'm a passive-aggressive comment, not a cop.
        LogConfiguration logConfiguration = injector.getInstance(LogConfiguration.class);
        logConfiguration.configure();
        
        EpisodeManager episodeManager = injector.getInstance(EpisodeManager.class);
        
        Scheduler scheduler = injector.getInstance(Scheduler.class);
        
        scheduler.start();
        
        new ApplicationMainWindow(config, episodeManager);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            config.save();
            episodeManager.save();
        }));
        
        addCloseListener();
    }
    
    private static void createAppDataDirectory() {
        try {
            Files.createDirectories(new File(DirectoryUtil.DATA_DIR).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can not create app data directory", e);
        }
    }
    
    private static void addCloseListener() {
        // close when escape is pressed
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Events.post(new TestEscapePressedEvent("asd"));
            }
            return false;
        });
    }
}
