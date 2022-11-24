package com.kikopolis.eventbus.event;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;

import java.util.ArrayList;
import java.util.List;

public class CheckEventsForDispatchBusEvent implements BusEvent {
    private List<AudioEvent> events;
    
    public CheckEventsForDispatchBusEvent() {
        this.events = new ArrayList<>();
    }
    
    public CheckEventsForDispatchBusEvent(List<AudioEvent> events) {
        this.events = events;
    }
    
    public List<AudioEvent> getEvents() {
        return events;
    }
    
    public void setEvents(List<AudioEvent> events) {
        this.events = events;
    }
}
