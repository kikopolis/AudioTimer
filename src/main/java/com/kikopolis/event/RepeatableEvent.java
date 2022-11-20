package com.kikopolis.event;

import com.kikopolis.event.EventManagerWithScheduler.CurrentTimeHolder;

import java.time.DayOfWeek;
import java.util.Objects;

public final class RepeatableEvent extends Event {
    private DayOfWeek dayOfWeek;
    private DayOfWeek dispatchedOnLatest;
    
    public RepeatableEvent(final String name, final String sound, final Integer hour, final Integer minute, final DayOfWeek dayOfWeek) {
        super(name, sound, hour, minute);
        this.dayOfWeek = dayOfWeek;
    }
    
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    @Override
    public boolean isReadyForDispatch() {
        return dispatchedOnLatest != CurrentTimeHolder.getDayOfWeek()
                && dayOfWeek == CurrentTimeHolder.getDayOfWeek()
                && Objects.equals(getHour(), CurrentTimeHolder.getHour())
                && Objects.equals(getMinute(), CurrentTimeHolder.getMinute());
    }
}
