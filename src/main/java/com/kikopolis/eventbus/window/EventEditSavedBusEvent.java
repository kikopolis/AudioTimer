package com.kikopolis.eventbus.window;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;

public class EventEditSavedBusEvent implements BusEvent {
    private AudioEvent event;
    
    public EventEditSavedBusEvent() {
        this.event = null;
    }
    
    public EventEditSavedBusEvent(final AudioEvent event) {
        this.event = event;
    }
    
    public AudioEvent getEvent() {
        return event;
    }
    
    public void setEvent(final AudioEvent event) {
        this.event = event;
    }
}
