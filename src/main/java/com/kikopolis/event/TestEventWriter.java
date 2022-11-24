package com.kikopolis.event;

import com.kikopolis.core.Events;
import com.kikopolis.eventbus.event.SaveEventListBusEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.kikopolis.util.Randomizer.randomDayOfMonth;
import static com.kikopolis.util.Randomizer.randomDayOfWeek;
import static com.kikopolis.util.Randomizer.randomHour;
import static com.kikopolis.util.Randomizer.randomMinute;
import static com.kikopolis.util.Randomizer.randomMonth;
import static com.kikopolis.util.Randomizer.randomNumber;

public class TestEventWriter {
    public TestEventWriter() {
        List<AudioEvent> events = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            events.add(randomSingularEvent());
        }
        for (int i = 0; i < 56; i++) {
            events.add(randomRecurringEvent());
        }
        Events.post(new SaveEventListBusEvent(events));
    }
    
    private AudioEvent randomRecurringEvent() {
        return new RecurringAudioEvent(
                "Test Event %s".formatted(randomNumber(1000)),
                "sound%s.wav".formatted(randomNumber(1000)),
                randomHour(),
                randomMinute(),
                randomDayOfWeek(),
                randomDayOfWeek()
        );
    }
    
    private AudioEvent randomSingularEvent() {
        return new SingularAudioEvent(
                "Test Event %s".formatted(randomNumber(1000)),
                "sound%s.wav".formatted(randomNumber(1000)),
                randomHour(),
                randomMinute(),
                false,
                LocalDate.of(2023, randomMonth(), randomDayOfMonth())
        );
    }
}
