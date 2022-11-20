package com.kikopolis.event;

public interface Scheduler {
    boolean isRunning();
    void start();
    void stop();
    
}
