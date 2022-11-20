package com.kikopolis;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kikopolis.config.Configuration;
import com.kikopolis.event.EventManager;
import com.kikopolis.event.Scheduler;
import com.kikopolis.gui.frame.ApplicationMainWindow;
import com.kikopolis.util.DirectoryUtil;

public class App {
    // TODO: Make main window that would hold the event list, and misc config
    // TODO: Make event editor with choices - time, day or date, repeatable, unique or default sound
    // TODO: Make audio selection window with a test player and enable audio length limiting in the config
    // TODO: Implement configuration adapter, ie. independent of the configuration file format or data location
    // TODO: Implement a wide range of audio file formats
    public static void main(String[] args) {
        // First make sure our data directory exists
        DirectoryUtil.createDirectoryIfNotExists(DirectoryUtil.DATA_DIR);
        //Create Guice injector and configure dependencies in AppModule
        Injector injector = Guice.createInjector(new AppModule());
        
        Configuration config = injector.getInstance(Configuration.class);
        
        EventManager eventManager = injector.getInstance(EventManager.class);
        
        Scheduler scheduler = injector.getInstance(Scheduler.class);
        
        scheduler.start();
        
        new ApplicationMainWindow(config);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            config.save();
            eventManager.save();
        }));
    }
}
