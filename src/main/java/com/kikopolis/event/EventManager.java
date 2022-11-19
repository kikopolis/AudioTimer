package com.kikopolis.event;

public interface EventManager {
    void addEvent(Event event);
    void removeEvent(Event event);
    void dispatchEvents();
}
