package com.kikopolis.eventbus.event;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;

import java.util.ArrayList;
import java.util.List;

public class EventListRefreshBusEvent implements BusEvent {
    private List<AudioEvent> events;
    
    public EventListRefreshBusEvent() {
        this.events = new ArrayList<>();
    }
    
    public EventListRefreshBusEvent(List<AudioEvent> events) {
        this.events = events;
    }
    
    public List<AudioEvent> getEvents() {
        return events;
    }
    
    public void setEvents(List<AudioEvent> events) {
        this.events = events;
    }
}
