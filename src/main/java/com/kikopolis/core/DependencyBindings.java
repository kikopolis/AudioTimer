package com.kikopolis.core;

import com.google.inject.AbstractModule;
import com.kikopolis.config.AppConfig;
import com.kikopolis.config.ConfigWriterAndReader;
import com.kikopolis.config.ConfigWriterAndReaderKeyValue;
import com.kikopolis.config.Configuration;
import com.kikopolis.config.logging.LogConfiguration;
import com.kikopolis.config.logging.Slf4JLogConfiguration;
import com.kikopolis.episode.EpisodeManagerInterface;
import com.kikopolis.episode.EpisodeManagerInterfaceWithScheduler;
import com.kikopolis.episode.EpisodeWriterAndReader;
import com.kikopolis.episode.EpisodeWriterAndReaderCsv;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.schedule.SchedulerByInterval;

public class DependencyBindings extends AbstractModule {
    @Override
    protected void configure() {
        bind(Configuration.class).to(AppConfig.class).asEagerSingleton();
        bind(LogConfiguration.class).to(Slf4JLogConfiguration.class).asEagerSingleton();
        bind(EpisodeManagerInterface.class).to(EpisodeManagerInterfaceWithScheduler.class).asEagerSingleton();
        bind(Scheduler.class).to(SchedulerByInterval.class).asEagerSingleton();
        bind(ConfigWriterAndReader.class).to(ConfigWriterAndReaderKeyValue.class);
        bind(EpisodeWriterAndReader.class).to(EpisodeWriterAndReaderCsv.class);
    }
}
