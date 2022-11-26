package com.kikopolis.core;

import com.kikopolis.event.Event;
import com.kikopolis.event.EventSubscriber;
import org.greenrobot.eventbus.EventBus;

public final class Events {
    private Events() {
    }
    
    public static void subscribe(final EventSubscriber subscriber) {
        EventBus.getDefault().register(subscriber);
    }
    
    public static void unsubscribe(final EventSubscriber subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }
    
    public static void post(final Event event) {
        EventBus.getDefault().post(event);
    }
}
