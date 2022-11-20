package com.kikopolis.event;

import com.kikopolis.event.EventManagerWithScheduler.CurrentTimeHolder;

import java.time.LocalDate;
import java.util.Objects;

public final class SingularEvent extends Event {
    private LocalDate date;
    private boolean isDispatched;
    
    public SingularEvent(final String name, final String sound, final Integer hour, final Integer minute, final LocalDate date, final boolean isDispatched) {
        super(name, sound, hour, minute);
        this.date = date;
        this.isDispatched = isDispatched;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public boolean isDispatched() {
        return isDispatched;
    }
    
    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }
    
    @Override
    public boolean isReadyForDispatch() {
        return !isDispatched
                && date.equals(CurrentTimeHolder.getDate())
                && Objects.equals(getHour(), CurrentTimeHolder.getHour())
                && Objects.equals(getMinute(), CurrentTimeHolder.getMinute());
    }
}
