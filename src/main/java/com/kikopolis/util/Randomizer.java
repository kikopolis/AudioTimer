package com.kikopolis.util;

import java.util.Random;

public final class Randomizer {
    private static final Random random = new Random();
    
    private Randomizer() {}
    
    public static int randomHour() {
        return (random.nextInt(1, 24) + 1);
    }
    
    public static int randomMinute() {
        return (random.nextInt(1, 60) + 1);
    }
    
    public static int randomDayOfMonth() {
        return (random.nextInt(1, 28) + 1);
    }
    
    public static int randomMonth() {
        return (random.nextInt(1, 12) + 1);
    }
    
    public static int randomNumber(int max) {
        return (random.nextInt(1, max) + 1);
    }
    
    public static DayOfWeek randomDayOfWeek() {
        return DayOfWeek.values()[randomNumber(DayOfWeek.values().length - 1)];
    }
}
