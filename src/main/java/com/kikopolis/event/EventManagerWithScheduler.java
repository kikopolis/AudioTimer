package com.kikopolis.event;

import com.google.inject.Inject;
import com.kikopolis.config.EventWriterAndReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventManagerWithScheduler implements EventManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventManagerWithScheduler.class.getName());
    private final EventWriterAndReader eventWriterAndReader;
    private final EventDispatcher dispatcher;
    private final List<Event> events;
    
    @Inject
    public EventManagerWithScheduler(final EventWriterAndReader eventWriterAndReader, final EventDispatcher eventDispatcher) {
        this.eventWriterAndReader = eventWriterAndReader;
        dispatcher = eventDispatcher;
        events = new ArrayList<>();
        // TODO: implement locks
    }
    
    public void save() {
        // TODO: check singular events that have run, and remove them
//        eventWriterAndReader.write(events);
        List<Event> ets = new ArrayList<>();
        RepeatableEvent evt1 = new RepeatableEvent("test1", "test1.wav", 12, 12, DayOfWeek.MONDAY, DayOfWeek.THURSDAY);
        SingularEvent evt2 = new SingularEvent("test2", "test1.wav", 12, 12, false, LocalDate.now());
        RepeatableEvent evt3 = new RepeatableEvent("test3", "test1.wav", 12, 12, DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        SingularEvent evt4 = new SingularEvent("test4", "test1.wav", 14, 55, true, LocalDate.now());
        SingularEvent invalid1 = new SingularEvent(Event.EMPTY_NAME, Event.EMPTY_SOUND, Event.EMPTY_HOUR, Event.EMPTY_MINUTE, false, SingularEvent.EMPTY_DATE);
        RepeatableEvent
                invalid2 =
                new RepeatableEvent(
                        Event.EMPTY_NAME,
                        Event.EMPTY_SOUND,
                        Event.EMPTY_HOUR,
                        Event.EMPTY_MINUTE,
                        DayOfWeek.EMPTY_DAY_OF_WEEK,
                        DayOfWeek.EMPTY_DAY_OF_WEEK
                );
        ets.add(evt1);
        ets.add(evt2);
        ets.add(evt3);
        ets.add(evt4);
        ets.add(invalid1);
        ets.add(invalid2);
        eventWriterAndReader.write(ets);
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
        if (events.isEmpty()) {
            setEvents(eventWriterAndReader.read());
        }
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
