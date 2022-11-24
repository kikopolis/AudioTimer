package com.kikopolis.service;

import com.google.inject.Inject;
import com.kikopolis.core.Events;
import com.kikopolis.eventbus.event.CheckEventsForDispatchBusEvent;
import com.kikopolis.eventbus.event.EventListRefreshBusEvent;
import com.kikopolis.eventbus.BusEventSubscriber;
import com.kikopolis.eventbus.Priority;
import com.kikopolis.eventbus.event.SaveEventListBusEvent;
import com.kikopolis.eventbus.window.EventListOpenBusEvent;
import com.kikopolis.repository.EventRepository;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventService implements BusEventSubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    
    @Inject
    public EventService(final EventRepository eventRepository) {
        Events.subscribe(this);
        this.eventRepository = eventRepository;
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onRefreshEvent(final EventListRefreshBusEvent busEvent) {
        if (busEvent != null) {
            LOGGER.info("Event list refresh event received");
            busEvent.setEvents(eventRepository.all());
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onEventListWindowOpenEvent(final EventListOpenBusEvent busEvent) {
        if (busEvent != null) {
            LOGGER.info("Event list refresh event received");
            busEvent.setEvents(eventRepository.all());
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.LOWEST)
    public void onCheckEventsEvent(final CheckEventsForDispatchBusEvent busEvent) {
        LOGGER.info("Checking events for dispatch");
        eventRepository.all().forEach(event -> {
            if (event.isReadyForDispatch()) {
                LOGGER.info("Dispatching event %s - \"%s\"".formatted(event.getName(), event.getId()));
                // TODO: dispatch event
            }
        });
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.LOWEST)
    public void onSaveListEvent(final SaveEventListBusEvent busEvent) {
        LOGGER.info("Saving event list");
        eventRepository.saveMany(busEvent.getEvents());
    }
}
