package com.kikopolis.core;

import com.google.inject.AbstractModule;
import com.kikopolis.config.logging.LogConfiguration;
import com.kikopolis.config.logging.Slf4JLogConfiguration;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.schedule.SchedulerByInterval;
import com.kikopolis.service.ConfigService;
import com.kikopolis.service.EpisodeService;

public class DependencyBindings extends AbstractModule {
    @Override
    protected void configure() {
        bind(LogConfiguration.class).to(Slf4JLogConfiguration.class).asEagerSingleton();
        bind(Scheduler.class).to(SchedulerByInterval.class).asEagerSingleton();
        bind(EpisodeService.class).asEagerSingleton();
        bind(ConfigService.class).asEagerSingleton();
    }
}
