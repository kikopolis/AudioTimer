package com.kikopolis.eventbus.window;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.eventbus.BusEvent;
import com.kikopolis.gui.window.EventList;

import java.util.List;

public class EventListOpenBusEvent implements BusEvent {
    private final EventList eventListWindow;
    private List<AudioEvent> events;
    
    public EventListOpenBusEvent(final EventList eventListWindow) {
        this(eventListWindow, null);
    }
    
    public EventListOpenBusEvent(final EventList eventListWindow, final List<AudioEvent> events) {
        this.eventListWindow = eventListWindow;
        this.events = events;
    }
    
    public List<AudioEvent> getEvents() {
        return events;
    }
    
    public void setEvents(final List<AudioEvent> events) {
        this.events = events;
    }
    
    public EventList getEventListWindow() {
        return eventListWindow;
    }
}
