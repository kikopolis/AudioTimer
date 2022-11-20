package com.kikopolis.event;

import com.google.inject.Inject;

public class EventDispatcherWithScheduler implements EventDispatcher {
    @Inject
    public EventDispatcherWithScheduler() {
    }
    
    @Override
    public void dispatch(Event event) {
        // TODO: implement
        // TODO: if repeatable event, set lastrunonday
    }
}
