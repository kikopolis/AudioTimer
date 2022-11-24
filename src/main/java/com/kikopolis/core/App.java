package com.kikopolis.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kikopolis.config.logging.LogConfiguration;
import com.kikopolis.event.TestEventWriter;
import com.kikopolis.gui.window.Main;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.util.DirectoryUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class App {
    // TODO: Make main window that would hold the event list, and misc config
    // TODO: Make event editor with choices - time, day or date, repeatable, unique or default sound
    // TODO: Make audio selection window with a test player and enable audio length limiting in the config
    // TODO: Implement configuration adapter, ie. independent of the configuration file format or data location
    // TODO: Implement a wide range of audio file formats
    public static void main(String[] args) {
        // First make sure our data directory exists
        createAppDataDirectory();
        //Create Guice injector and configure dependencies in AppModule
        Injector injector = Guice.createInjector(new DependencyBindings());
        // Initialize logging
        // TODO: Maybe configure logging in log4j.properties and throw exception if it fails
        // TODO: maybe also throw when the log file is not writable etc
        LogConfiguration logConfiguration = injector.getInstance(LogConfiguration.class);
        logConfiguration.configure();
        
        Scheduler scheduler = injector.getInstance(Scheduler.class);
        
        scheduler.start();
        
        Main appMainWindow = injector.getInstance(Main.class);
        appMainWindow.setVisible(true);
        
        new TestEventWriter();
        
        addCloseListener();
    }
    
    // TODO: maybe refactor this to a util class
    // TODO: maybe create similar methods for logging directory
    private static void createAppDataDirectory() {
        try {
            Files.createDirectories(new File(DirectoryUtil.DATA_DIR).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can not create app data directory", e);
        }
    }
    
    // TODO: remove this after testing is done
    private static void addCloseListener() {
        // close when escape is pressed
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            return false;
        });
    }
}
