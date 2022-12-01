package com.kikopolis.core;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.kikopolis.audio.Player;
import com.kikopolis.audio.Players;
import com.kikopolis.audio.WavPlayer;
import com.kikopolis.config.logging.LogConfiguration;
import com.kikopolis.config.logging.Slf4JLogConfiguration;
import com.kikopolis.schedule.Scheduler;
import com.kikopolis.schedule.SchedulerByInterval;
import com.kikopolis.service.ConfigService;
import com.kikopolis.service.TaskService;

public class DependencyBindings extends AbstractModule {
    @Override
    protected void configure() {
        bind(LogConfiguration.class).to(Slf4JLogConfiguration.class).asEagerSingleton();
        bind(Scheduler.class).to(SchedulerByInterval.class).asEagerSingleton();
        bind(TaskService.class).asEagerSingleton();
        bind(ConfigService.class).asEagerSingleton();
        bind(Players.class).asEagerSingleton();
        
        var playerBinder = Multibinder.newSetBinder(binder(), Player.class);
        playerBinder.addBinding().to(WavPlayer.class);
    }
}
