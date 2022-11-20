package com.kikopolis;

import com.google.inject.AbstractModule;
import com.kikopolis.config.AppConfig;
import com.kikopolis.config.ConfigWriterAndReader;
import com.kikopolis.config.ConfigWriterAndReaderKeyValue;
import com.kikopolis.config.Configuration;
import com.kikopolis.config.EventWriterAndReader;
import com.kikopolis.config.EventWriterAndReaderCsv;
import com.kikopolis.event.EventDispatcher;
import com.kikopolis.event.EventDispatcherWithScheduler;
import com.kikopolis.event.EventManager;
import com.kikopolis.event.EventManagerWithScheduler;
import com.kikopolis.event.Scheduler;
import com.kikopolis.event.SchedulerByInterval;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ConfigWriterAndReader.class).to(ConfigWriterAndReaderKeyValue.class);
        bind(EventWriterAndReader.class).to(EventWriterAndReaderCsv.class);
        
        bind(Configuration.class).to(AppConfig.class);
        
        bind(EventDispatcher.class).to(EventDispatcherWithScheduler.class);
        // EventManager needs EventDispatcher
        bind(EventManager.class).to(EventManagerWithScheduler.class);
        // Scheduler needs EventManager
        bind(Scheduler.class).to(SchedulerByInterval.class);
    }
}
