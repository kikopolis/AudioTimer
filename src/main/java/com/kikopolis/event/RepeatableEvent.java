package com.kikopolis.event;

import java.time.DayOfWeek;
import java.util.Date;

public interface RepeatableEvent extends Event {
    DayOfWeek getDay();
    void setDay(DayOfWeek day);
    String getRepeatable();
    void setRepeatable(String repeatable);
}
