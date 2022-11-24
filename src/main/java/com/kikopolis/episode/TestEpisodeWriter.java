package com.kikopolis.episode;

import com.google.inject.Inject;
import com.kikopolis.core.Events;
import com.kikopolis.event.episode.SaveEpisodeListEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.kikopolis.util.Randomizer.randomDayOfMonth;
import static com.kikopolis.util.Randomizer.randomDayOfWeek;
import static com.kikopolis.util.Randomizer.randomHour;
import static com.kikopolis.util.Randomizer.randomMinute;
import static com.kikopolis.util.Randomizer.randomMonth;
import static com.kikopolis.util.Randomizer.randomNumber;

public class TestEpisodeWriter {
    public TestEpisodeWriter() {
        List<AudioEpisode> episodes = new ArrayList<>();
        for (int i = 0; i < 29; i++) {
            episodes.add(randomSingularEpisode());
        }
        for (int i = 0; i < 56; i++) {
            episodes.add(randomRecurringEpisode());
        }
        Events.post(new SaveEpisodeListEvent(episodes));
    }
    
    private AudioEpisode randomRecurringEpisode() {
        return new RecurringAudioEpisode(
                "Test Episode %s".formatted(randomNumber(1000)),
                "sound%s.wav".formatted(randomNumber(1000)),
                randomHour(),
                randomMinute(),
                randomDayOfWeek(),
                randomDayOfWeek()
        );
    }
    
    private AudioEpisode randomSingularEpisode() {
        return new SingularAudioEpisode(
                "Test Episode %s".formatted(randomNumber(1000)),
                "sound%s.wav".formatted(randomNumber(1000)),
                randomHour(),
                randomMinute(),
                false,
                LocalDate.of(2023, randomMonth(), randomDayOfMonth())
        );
    }
}
