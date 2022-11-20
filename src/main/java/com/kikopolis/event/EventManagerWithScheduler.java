package com.kikopolis.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventManagerWithScheduler implements EventManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventManagerWithScheduler.class.getName());
    private final EventDispatcher dispatcher;
    private final List<Event> events;
    
    public EventManagerWithScheduler(final EventDispatcher eventDispatcher, final List<Event> events) {
        dispatcher = eventDispatcher;
        this.events = events;
        // TODO: implement locks
    }
    
    @Override
    public void addEvent(Event event) {
        events.add(event);
        LOGGER.debug("Added event: {}", event);
    }
    
    @Override
    public void removeEvent(Event event) {
        events.remove(event);
        LOGGER.debug("Removed event: {}", event);
    }
    
    @Override
    public void checkAndDispatchEvents() {
        setRunTimes();
        for (Event event : events) {
            if (event.isReadyForDispatch()) {
                dispatcher.dispatch(event);
            }
        }
        clearRunTimes();
    }
    
    @Override
    public void setEvents(List<Event> events) {
        this.events.clear();
        if (events != null) {
            this.events.addAll(events);
        }
    }
    
    private void setRunTimes() {
        CurrentTimeHolder.setDate(LocalDate.now());
        CurrentTimeHolder.setDayOfWeek(LocalDate.now().getDayOfWeek());
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
    
    @Override
    public List<Event> getEvents() {
        return events;
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
