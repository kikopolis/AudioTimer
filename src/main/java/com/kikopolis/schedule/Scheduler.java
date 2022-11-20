package com.kikopolis.schedule;

public interface Scheduler {
    boolean isRunning();
    void start();
    void stop();
    
}
