package com.kikopolis.event;

import com.google.inject.Inject;
import com.kikopolis.config.EventWriterAndReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;

public class EventManagerWithScheduler implements EventManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventManagerWithScheduler.class.getName());
    private final EventWriterAndReader eventWriterAndReader;
    private final EventDispatcher dispatcher;
    private final List<Event> events;
    private final StampedLock lock;
    
    @Inject
    public EventManagerWithScheduler(final EventWriterAndReader eventWriterAndReader, final EventDispatcher eventDispatcher) {
        this.eventWriterAndReader = eventWriterAndReader;
        dispatcher = eventDispatcher;
        events = new ArrayList<>();
        lock = new StampedLock();
    }
    
    public void save() {
        long stamp = lock.writeLock();
        try {
            purgeEvents();
            eventWriterAndReader.write(events);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public void addEvent(Event event) {
        long stamp = lock.writeLock();
        try {
            events.add(event);
            LOGGER.debug("Added event: {}", event);
            purgeEvents();
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public void removeEvent(Event event) {
        long stamp = lock.writeLock();
        try {
            events.remove(event);
            LOGGER.debug("Removed event: {}", event);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public void checkAndDispatchEvents() {
        long stamp = lock.writeLock();
        try {
            if (events.isEmpty()) {
                setEvents(eventWriterAndReader.read());
            }
            purgeEvents();
            setRunTimes();
            for (Event event : events) {
                if (event.isReadyForDispatch()) {
                    dispatcher.dispatch(event);
                }
            }
            clearRunTimes();
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public List<Event> getEvents() {
        long stamp = lock.readLock();
        try {
            return events;
        } finally {
            lock.unlockRead(stamp);
        }
    }
    
    @Override
    public void setEvents(List<Event> events) {
        long stamp = lock.writeLock();
        try {
            this.events.clear();
            if (events != null) {
                this.events.addAll(events);
            }
            purgeEvents();
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    private void setRunTimes() {
        CurrentTimeHolder.setDate(LocalDate.now());
        CurrentTimeHolder.setDayOfWeek(DayOfWeek.fromLocalDate(CurrentTimeHolder.getDate()));
        CurrentTimeHolder.setHour(LocalDateTime.now().getHour());
        CurrentTimeHolder.setMinute(LocalDateTime.now().getMinute());
        LOGGER.debug("Set current run times.");
    }
    
    private void clearRunTimes() {
        CurrentTimeHolder.setDate(null);
        CurrentTimeHolder.setDayOfWeek(null);
        CurrentTimeHolder.setHour(null);
        CurrentTimeHolder.setMinute(null);
        LOGGER.debug("Cleared current run times.");
    }
    
    private void purgeEvents() {
        events.removeIf(event -> event.isValid() && !event.isDispatched());
    }
    
    public static final class CurrentTimeHolder {
        
        private static LocalDate date;
        private static DayOfWeek dayOfWeek;
        private static Integer hour;
        private static Integer minute;
        
        private CurrentTimeHolder() {
        }
        
        public static LocalDate getDate() {
            return date;
        }
        
        public static void setDate(LocalDate date) {
            CurrentTimeHolder.date = date;
        }
        
        public static DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }
        
        public static void setDayOfWeek(DayOfWeek dayOfWeek) {
            CurrentTimeHolder.dayOfWeek = dayOfWeek;
        }
        
        public static Integer getHour() {
            return hour;
        }
        
        public static void setHour(Integer hour) {
            CurrentTimeHolder.hour = hour;
        }
        
        public static Integer getMinute() {
            return minute;
        }
        
        public static void setMinute(Integer minute) {
            CurrentTimeHolder.minute = minute;
        }
    }
}
