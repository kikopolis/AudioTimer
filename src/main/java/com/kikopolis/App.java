package com.kikopolis;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kikopolis.config.Configuration;
import com.kikopolis.episode.EpisodeManager;
import com.kikopolis.event.TestEscapePressedEvent;
import com.kikopolis.gui.frame.ApplicationMainWindow;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.util.DirectoryUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

public class App {
    // TODO: Make main window that would hold the episode list, and misc config
    // TODO: Make episode editor with choices - time, day or date, repeatable, unique or default sound
    // TODO: Make audio selection window with a test player and enable audio length limiting in the config
    // TODO: Implement configuration adapter, ie. independent of the configuration file format or data location
    // TODO: Implement a wide range of audio file formats
    public static void main(String[] args) {
        // First make sure our data directory exists
        DirectoryUtil.createDirectoryIfNotExists(DirectoryUtil.DATA_DIR);
        //Create Guice injector and configure dependencies in AppModule
        Injector injector = Guice.createInjector(new DependencyBindings());
        
        Configuration config = injector.getInstance(Configuration.class);
        
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
