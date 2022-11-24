package com.kikopolis.event;

import com.kikopolis.service.CurrentDispatchTimeService;
import com.kikopolis.util.DayOfWeek;

import java.util.List;
import java.util.Objects;

public final class RecurringAudioEvent extends AudioEvent {
    private DayOfWeek dayOfWeek;
    private DayOfWeek dispatchedOnLatest;
    
    public RecurringAudioEvent(
            final String name,
            final String sound,
            final Integer hour,
            final Integer minute,
            final DayOfWeek dayOfWeek,
            final DayOfWeek dispatchedOnLatest
                              ) {
        super(name, sound, hour, minute, false);
        this.dayOfWeek = dayOfWeek != null ? dayOfWeek : DayOfWeek.EMPTY_DAY_OF_WEEK;
        this.dispatchedOnLatest = dispatchedOnLatest != null ? dispatchedOnLatest : DayOfWeek.EMPTY_DAY_OF_WEEK;
    }
    
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    @Override
    public boolean isDispatched() {
        return false;
    }
    
    @Override
    public void setDispatched(boolean dispatched) {
        if (dispatched) {
            dispatchedOnLatest = CurrentDispatchTimeService.getDayOfWeek();
        }
        super.setDispatched(false);
    }
    
    @Override
    public boolean isReadyForDispatch() {
        return dispatchedOnLatest != CurrentDispatchTimeService.getDayOfWeek() &&
                dayOfWeek == CurrentDispatchTimeService.getDayOfWeek() &&
                Objects.equals(getHour(), CurrentDispatchTimeService.getHour()) &&
                Objects.equals(getMinute(), CurrentDispatchTimeService.getMinute());
    }
    
    @Override
    public Iterable<String> toCsv() {
        return List.of(
                "RepeatableEvent",
                getName() != null ? getName() : EMPTY_NAME,
                getSound() != null ? getSound() : EMPTY_SOUND,
                getHour() != null ? getHour().toString() : EMPTY_HOUR.toString(),
                getMinute() != null ? getMinute().toString() : EMPTY_MINUTE.toString(),
                dayOfWeek != null ? dayOfWeek.toString() : DayOfWeek.EMPTY_DAY_OF_WEEK.toString(),
                dispatchedOnLatest != null ? dispatchedOnLatest.toString() : DayOfWeek.EMPTY_DAY_OF_WEEK.toString()
                      );
    }
    
    @Override
    public boolean isInvalid() {
        return super.isInvalid()
                || dayOfWeek == DayOfWeek.EMPTY_DAY_OF_WEEK
                || dispatchedOnLatest == DayOfWeek.EMPTY_DAY_OF_WEEK;
    }
}
