package com.kikopolis.event;

import com.kikopolis.event.EventManagerWithScheduler.CurrentTimeHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class SingularEvent extends Event {
    public static final LocalDate EMPTY_DATE = LocalDate.of(0, 1, 1);
    private LocalDate date;
    
    public SingularEvent(
            final String name, final String sound, final Integer hour, final Integer minute, final boolean isDispatched, final LocalDate date
                        ) {
        super(name, sound, hour, minute, isDispatched);
        this.date = date;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    @Override
    public boolean isReadyForDispatch() {
        return !isDispatched() &&
                date.equals(CurrentTimeHolder.getDate()) &&
                Objects.equals(getHour(), CurrentTimeHolder.getHour()) &&
                Objects.equals(getMinute(), CurrentTimeHolder.getMinute());
    }
    
    @Override
    public Iterable<String> toCsv() {
        return List.of(
                "SingularEvent",
                getName() != null ? getName() : EMPTY_NAME,
                getSound() != null ? getSound() : EMPTY_SOUND,
                getHour() != null ? getHour().toString() : EMPTY_HOUR.toString(),
                getMinute() != null ? getMinute().toString() : EMPTY_MINUTE.toString(),
                Boolean.toString(isDispatched()),
                date != null ? date.toString() : EMPTY_DATE.toString()
                      );
    }
    
    @Override
    public boolean isValid() {
        return super.isValid()
                && date != null
                && date != EMPTY_DATE;
    }
}
