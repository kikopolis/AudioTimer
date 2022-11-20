package com.kikopolis.event;

import java.io.Serializable;

public abstract sealed class Event implements Serializable permits RepeatableEvent, SingularEvent {
    private String name;
    private String sound;
    private Integer hour;
    private Integer minute;
    
    protected Event(final String name, final String sound, final Integer hour, final Integer minute) {
        this.name = name;
        this.sound = sound;
        this.hour = hour;
        this.minute = minute;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSound() {
        return sound;
    }
    
    public void setSound(String sound) {
        this.sound = sound;
    }
    
    public Integer getHour() {
        return hour;
    }
    
    public void setHour(Integer hour) {
        this.hour = hour;
    }
    
    public Integer getMinute() {
        return minute;
    }
    
    public void setMinute(Integer minute) {
        this.minute = minute;
    }
    
    public abstract boolean isReadyForDispatch();
}
