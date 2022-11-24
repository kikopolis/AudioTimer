package com.kikopolis.eventbus.window;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;

public class EventDeletedBusEvent implements BusEvent {
    private AudioEvent event;
    
    public EventDeletedBusEvent() {
        this.event = null;
    }
    
    public EventDeletedBusEvent(final AudioEvent event) {
        this.event = event;
    }
    
    public AudioEvent getEvent() {
        return event;
    }
    
    public void setEvent(final AudioEvent event) {
        this.event = event;
    }
}
