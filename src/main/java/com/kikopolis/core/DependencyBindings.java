package com.kikopolis.core;

import com.google.inject.AbstractModule;
import com.kikopolis.config.AppConfig;
import com.kikopolis.config.ConfigWriterAndReader;
import com.kikopolis.config.ConfigWriterAndReaderKeyValue;
import com.kikopolis.config.Configuration;
import com.kikopolis.episode.EpisodeWriterAndReader;
import com.kikopolis.episode.EpisodeWriterAndReaderCsv;
import com.kikopolis.episode.EpisodeManager;
import com.kikopolis.episode.EpisodeManagerWithScheduler;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.schedule.SchedulerByInterval;

public class DependencyBindings extends AbstractModule {
    @Override
    protected void configure() {
        bind(ConfigWriterAndReader.class).to(ConfigWriterAndReaderKeyValue.class);
        bind(EpisodeWriterAndReader.class).to(EpisodeWriterAndReaderCsv.class);
        bind(Configuration.class).to(AppConfig.class);
        bind(EpisodeManager.class).to(EpisodeManagerWithScheduler.class);
        bind(Scheduler.class).to(SchedulerByInterval.class);
    }
}
