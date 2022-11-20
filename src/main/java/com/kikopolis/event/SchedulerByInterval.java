package com.kikopolis.event;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class SchedulerByInterval implements Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerByInterval.class.getName());
    private static final AtomicBoolean running = new AtomicBoolean(false);
    private static final int INTERVAL = 1000;
    private final Timer timer;
    private final EventManager eventManager;
    
    @Inject
    public SchedulerByInterval(final EventManager eventManager) {
        timer = new Timer();
        this.eventManager = eventManager;
    }
    
    @Override
    public boolean isRunning() {
        return running.get();
    }
    
    @Override
    public void start() {
        if (running.get()) {
            LOGGER.debug("Scheduler is already running. Cannot start again.");
            return;
        }
        LOGGER.debug("Starting scheduler.");
        running.set(true);
        timer.scheduleAtFixedRate(task(), 0, INTERVAL);
    }
    
    @Override
    public void stop() {
        if (!running.get()) {
            LOGGER.debug("Scheduler is not running. Cannot stop.");
            return;
        }
        LOGGER.debug("Stopping scheduler.");
        running.set(false);
        timer.cancel();
    }
    
    private TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                eventManager.checkAndDispatchEvents();
            }
        };
    }
}
