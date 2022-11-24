package com.kikopolis.eventbus.window;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;

import java.util.List;

public class EventListClosedBusEvent implements BusEvent {
    private List<AudioEvent> events;
    
    public EventListClosedBusEvent() {
        events = null;
    }
    
    public EventListClosedBusEvent(final List<AudioEvent> events) {
        this.events = events;
    }
    
    public List<AudioEvent> getEvents() {
        return events;
    }
    
    public void setEvents(final List<AudioEvent> events) {
        this.events = events;
    }
}
