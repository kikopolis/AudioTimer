package com.kikopolis.core;

import com.kikopolis.eventbus.BusEvent;
import com.kikopolis.eventbus.BusEventSubscriber;
import org.greenrobot.eventbus.EventBus;

public final class Events {
    private Events() {
    }
    
    public static void subscribe(final BusEventSubscriber subscriber) {
        EventBus.getDefault().register(subscriber);
    }
    
    public static void unsubscribe(final BusEventSubscriber subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }
    
    public static void post(final BusEvent busEvent) {
        EventBus.getDefault().post(busEvent);
    }
}
