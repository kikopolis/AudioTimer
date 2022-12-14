package com.kikopolis.core;

import com.google.inject.Guice;
import com.kikopolis.audio.AudioPlayerController;
import com.kikopolis.config.logging.LogConfiguration;
import com.kikopolis.gui.FrameManager;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.task.TestTaskWriter;
import com.kikopolis.util.DirectoryUtil;

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
        var injector = Guice.createInjector(new DependencyBindings());
        // Initialize logging
        // TODO: Maybe configure logging in log4j.properties and throw exception if it fails
        // TODO: maybe also throw when the log file is not writable etc
        var logConfiguration = injector.getInstance(LogConfiguration.class);
        logConfiguration.configure();
        
        new TestTaskWriter();
        
        var audioController = injector.getInstance(AudioPlayerController.class);
        
        var scheduler = injector.getInstance(Scheduler.class);
        scheduler.start();
        
        FrameManager.getInstance(injector).getMainFrame().showFrame();
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
}
