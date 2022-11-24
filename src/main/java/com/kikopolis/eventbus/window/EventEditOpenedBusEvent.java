package com.kikopolis.eventbus.window;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;
import com.kikopolis.gui.window.EventEdit;

public class EventEditOpenedBusEvent implements BusEvent {
    private final EventEdit eventEditWindow;
    private AudioEvent event;
    
    public EventEditOpenedBusEvent(final EventEdit eventEditWindow) {
        this(eventEditWindow, null);
    }
    
    public EventEditOpenedBusEvent(final EventEdit eventEditWindow, final AudioEvent event) {
        this.eventEditWindow = eventEditWindow;
        this.event = event;
    }
    
    public AudioEvent getEvent() {
        return event;
    }
    
    public void setEvent(final AudioEvent event) {
        this.event = event;
    }
    
    public EventEdit getEventEditWindow() {
        return eventEditWindow;
    }
}
