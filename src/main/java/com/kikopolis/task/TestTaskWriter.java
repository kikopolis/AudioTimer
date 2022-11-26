package com.kikopolis.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.kikopolis.util.Randomizer.randomDayOfMonth;
import static com.kikopolis.util.Randomizer.randomDayOfWeek;
import static com.kikopolis.util.Randomizer.randomHour;
import static com.kikopolis.util.Randomizer.randomMinute;
import static com.kikopolis.util.Randomizer.randomMonth;
import static com.kikopolis.util.Randomizer.randomNumber;

public class TestTaskWriter {
    public TestTaskWriter() {
        List<AudioTask> events = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            events.add(randomSingularTask());
        }
        for (int i = 0; i < 56; i++) {
            events.add(randomRecurringTask());
        }
    }
    
    private AudioTask randomRecurringTask() {
        return new RecurringAudioTask(
                "Test Task %s".formatted(randomNumber(1000)),
                "sound%s.wav".formatted(randomNumber(1000)),
                randomHour(),
                randomMinute(),
                randomDayOfWeek(),
                randomDayOfWeek()
        );
    }
    
    private AudioTask randomSingularTask() {
        return new SingularAudioTask(
                "Test Task %s".formatted(randomNumber(1000)),
                "sound%s.wav".formatted(randomNumber(1000)),
                randomHour(),
                randomMinute(),
                false,
                LocalDate.of(2023, randomMonth(), randomDayOfMonth())
        );
    }
}
