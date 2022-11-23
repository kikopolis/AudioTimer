package com.kikopolis.episode;

import com.google.inject.Inject;
import com.kikopolis.util.DayOfWeek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;

import static com.kikopolis.util.Randomizer.randomDayOfMonth;
import static com.kikopolis.util.Randomizer.randomDayOfWeek;
import static com.kikopolis.util.Randomizer.randomHour;
import static com.kikopolis.util.Randomizer.randomMinute;
import static com.kikopolis.util.Randomizer.randomMonth;
import static com.kikopolis.util.Randomizer.randomNumber;

public class EpisodeManagerWithScheduler implements EpisodeManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EpisodeManagerWithScheduler.class.getName());
    private final EpisodeWriterAndReader episodeWriterAndReader;
    private final List<AudioEpisode> episodes;
    private final StampedLock lock;
    
    @Inject
    public EpisodeManagerWithScheduler(final EpisodeWriterAndReader episodeWriterAndReader) {
        this.episodeWriterAndReader = episodeWriterAndReader;
        episodes = new ArrayList<>();
        lock = new StampedLock();
        
        // TODO: write test episodes to file
        for (int i = 0; i < 29; i++) {
            episodes.add(randomSingularEpisode());
        }
        for (int i = 0; i < 56; i++) {
            episodes.add(randomRecurringEpisode());
        }
        save();
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
    
    public void save() {
        long stamp = lock.writeLock();
        try {
            episodeWriterAndReader.write(episodes);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public void addEpisode(AudioEpisode episode) {
        long stamp = lock.writeLock();
        try {
            episodes.add(episode);
            LOGGER.debug("Added episode: {}", episode);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public void removeEpisode(AudioEpisode episode) {
        long stamp = lock.writeLock();
        try {
            episodes.remove(episode);
            LOGGER.debug("Removed episode: {}", episode);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public void checkAndDispatchEpisodes() {
        long stamp = lock.writeLock();
        try {
            if (episodes.isEmpty()) {
                setEpisodes(episodeWriterAndReader.read());
            }
            setRunTimes();
            for (AudioEpisode episode : episodes) {
                if (episode.isReadyForDispatch()) {
                    LOGGER.info("Dispatching episode: {}", episode);
                    // TODO : dispatch episode
                }
            }
            clearRunTimes();
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    @Override
    public List<AudioEpisode> getEpisodes() {
        long stamp = lock.readLock();
        try {
            return episodes;
        } finally {
            lock.unlockRead(stamp);
        }
    }
    
    @Override
    public void setEpisodes(List<AudioEpisode> episodes) {
        deleteAll();
        long stamp = lock.writeLock();
        try {
            if (episodes != null) {
                this.episodes.addAll(episodes);
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    public void purgeInvalid() {
        long stamp = lock.writeLock();
        try {
            episodes.removeIf(AudioEpisode::isInvalid);
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    public void purgeDispatched() {
        long stamp = lock.writeLock();
        try {
            episodes.removeIf(episode -> episode instanceof SingularAudioEpisode && episode.isDispatched());
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    public void deleteAll() {
        long stamp = lock.writeLock();
        try {
            episodes.clear();
        } finally {
            lock.unlockWrite(stamp);
        }
    }
    
    private void setRunTimes() {
        CurrentTimeHolder.setDate(LocalDate.now());
        CurrentTimeHolder.setDayOfWeek(DayOfWeek.fromLocalDate(CurrentTimeHolder.getDate()));
        CurrentTimeHolder.setHour(LocalDateTime.now().getHour());
        CurrentTimeHolder.setMinute(LocalDateTime.now().getMinute());
        LOGGER.debug("Set current run times.");
    }
    
    private void clearRunTimes() {
        CurrentTimeHolder.setDate(null);
        CurrentTimeHolder.setDayOfWeek(null);
        CurrentTimeHolder.setHour(null);
        CurrentTimeHolder.setMinute(null);
        LOGGER.debug("Cleared current run times.");
    }
    
    public static final class CurrentTimeHolder {
        
        private static LocalDate date;
        private static DayOfWeek dayOfWeek;
        private static Integer hour;
        private static Integer minute;
        
        private CurrentTimeHolder() {
        }
        
        public static LocalDate getDate() {
            return date;
        }
        
        public static void setDate(LocalDate date) {
            CurrentTimeHolder.date = date;
        }
        
        public static DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }
        
        public static void setDayOfWeek(DayOfWeek dayOfWeek) {
            CurrentTimeHolder.dayOfWeek = dayOfWeek;
        }
        
        public static Integer getHour() {
            return hour;
        }
        
        public static void setHour(Integer hour) {
            CurrentTimeHolder.hour = hour;
        }
        
        public static Integer getMinute() {
            return minute;
        }
        
        public static void setMinute(Integer minute) {
            CurrentTimeHolder.minute = minute;
        }
    }
}
