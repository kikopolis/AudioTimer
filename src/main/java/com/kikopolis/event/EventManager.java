package com.kikopolis.event;

import java.util.List;

public interface EventManager {
    void addEvent(final Event event);
    void removeEvent(final Event event);
    void checkAndDispatchEvents();
    List<Event> getEvents();
    void setEvents(final List<Event> events);
}
