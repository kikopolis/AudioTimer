package com.kikopolis;

import com.kikopolis.config.Config;
import com.kikopolis.config.ConfigManager;
import com.kikopolis.config.ConfigWriterAndReader;
import com.kikopolis.config.ConfigWriterAndReaderKeyValue;
import com.kikopolis.config.Defaults;
import com.kikopolis.config.EventWriterAndReader;
import com.kikopolis.config.EventWriterAndReaderCsv;
import com.kikopolis.event.Event;
import com.kikopolis.event.EventDispatcher;
import com.kikopolis.event.EventManager;
import com.kikopolis.event.SchedulerByInterval;
import com.kikopolis.event.EventDispatcherWithScheduler;
import com.kikopolis.event.EventManagerWithScheduler;
import com.kikopolis.event.Scheduler;
import com.kikopolis.gui.frame.ApplicationMainWindow;
import com.kikopolis.util.DirectoryUtil;

import java.util.List;

public class App {
    private static ConfigManager configManager;
    private static Config config;
    private static Scheduler scheduler;
    
    // TODO: Make main window that would hold the event list, and misc config
    // TODO: Make event editor with choices - time, day or date, repeatable, unique or default sound
    // TODO: Make audio selection window with a test player and enable audio length limiting in the config
    // TODO: Implement configuration adapter, ie. independent of the configuration file format or data location
    // TODO: Implement a wide range of audio file formats
    public static void main(String[] args) {
        DirectoryUtil.createDirectoryIfNotExists(Defaults.DATA_DIR);
        ConfigWriterAndReader configWriterAndReader = new ConfigWriterAndReaderKeyValue(Defaults.DATA_DIR);
        EventWriterAndReader eventWriterAndReader = new EventWriterAndReaderCsv(Defaults.DATA_DIR);
        configManager = new ConfigManager(configWriterAndReader, eventWriterAndReader);
        config = new Config(configManager.readConfig());
        // Set some defaults dependent on OS, not strictly necessary for now but nice to have
        config.setDefaultsByOperatingSystem();
        // Create event manager and dispatcher
        EventDispatcher eventDispatcher = new EventDispatcherWithScheduler();
        List<Event> events = configManager.readEvents();
        EventManager eventManager = new EventManagerWithScheduler(eventDispatcher, events);
        // Start the scheduler, which in turn will initialize the EventManager and EventDispatcher
        scheduler = new SchedulerByInterval(eventManager);
        scheduler.start();
        
        new ApplicationMainWindow(config);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            configManager.saveConfig(config.getConfigMap());
            configManager.saveEvents(eventManager.getEvents());
        }));
    }
}
