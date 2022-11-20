package com.kikopolis;

import org.greenrobot.eventbus.EventBus;

public final class Events {
    private Events() {
    }
    
    public static void subscribe(final Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }
    
    public static void unsubscribe(final Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }
    
    public static void post(final Object event) {
        EventBus.getDefault().post(event);
    }
}
