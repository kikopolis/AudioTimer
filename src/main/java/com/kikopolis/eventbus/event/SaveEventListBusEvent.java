package com.kikopolis.eventbus.event;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;

import java.util.ArrayList;
import java.util.List;

public class SaveEventListBusEvent implements BusEvent {
    private List<AudioEvent> events;
    
    public SaveEventListBusEvent() {
        events = new ArrayList<>();
    }
    
    public SaveEventListBusEvent(List<AudioEvent> events) {
        this.events = events;
    }
    
    public List<AudioEvent> getEvents() {
        return events;
    }
    
    public void setEvents(List<AudioEvent> events) {
        this.events = events;
    }
}
