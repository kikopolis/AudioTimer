package com.kikopolis.service;

import com.kikopolis.util.DayOfWeek;

import java.time.LocalDate;

public class CurrentDispatchTimeService {
    private static LocalDate date;
    private static int hour;
    private static int minute;
    private static DayOfWeek dayOfWeek;
    
    public static LocalDate getDate() {
        return date;
    }
    
    public static void setDate(LocalDate date) {
        CurrentDispatchTimeService.date = date;
    }
    
    public static int getHour() {
        return hour;
    }
    
    public static void setHour(int hour) {
        CurrentDispatchTimeService.hour = hour;
    }
    
    public static int getMinute() {
        return minute;
    }
    
    public static void setMinute(int minute) {
        CurrentDispatchTimeService.minute = minute;
    }
    
    public static DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public static void setDayOfWeek(DayOfWeek dayOfWeek) {
        CurrentDispatchTimeService.dayOfWeek = dayOfWeek;
    }
}
