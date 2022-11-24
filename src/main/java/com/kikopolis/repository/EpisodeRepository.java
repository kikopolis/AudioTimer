package com.kikopolis.repository;

import com.kikopolis.episode.AudioEpisode;
import com.kikopolis.episode.RecurringAudioEpisode;
import com.kikopolis.util.DayOfWeek;
import com.kikopolis.util.DirectoryUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EpisodeRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(EpisodeRepository.class);
    private static final File file = new File(DirectoryUtil.DATA_DIR + File.separator + "episodes.csv");
    
    public List<AudioEpisode> all() {
        return read();
    }
    
    public List<AudioEpisode> allByDayOfWeek(final DayOfWeek dayOfWeek) {
        List<AudioEpisode> episodes = all();
        episodes.removeIf(episode -> !(episode instanceof RecurringAudioEpisode recurringAudioEpisode) || recurringAudioEpisode.getDayOfWeek() != dayOfWeek);
        return episodes;
    }
    
    public AudioEpisode find(final UUID id) {
        return read().stream().filter(episode -> episode.getId().equals(id)).findFirst().orElse(null);
    }
    
    public AudioEpisode findByName(final String name) {
        return read().stream().filter(episode -> episode.getName().equals(name)).findFirst().orElse(null);
    }
    
    public void saveMany(final List<AudioEpisode> episodes) {
        write(episodes);
    }
    
    public void save(final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.add(episode);
        saveMany(episodes);
    }
    
    public void update(final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.set(episodes.indexOf(episode), episode);
        saveMany(episodes);
    }
    
    public void updateById(final UUID id, final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.set(episodes.indexOf(find(id)), episode);
        saveMany(episodes);
    }
    
    public void updateByName(final String name, final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.set(episodes.indexOf(findByName(name)), episode);
        saveMany(episodes);
    }
    
    public void delete(final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.remove(episode);
        saveMany(episodes);
    }
    
    public void deleteById(final UUID id) {
        List<AudioEpisode> episodes = all();
        episodes.remove(find(id));
        saveMany(episodes);
    }
    
    public void deleteByName(final String name) {
        List<AudioEpisode> episodes = all();
        episodes.remove(findByName(name));
        saveMany(episodes);
    }
    
    public void deleteAll() {
        write(List.of());
    }
    
    public void deleteAllInvalid() {
        List<AudioEpisode> episodes = all();
        episodes.removeIf(AudioEpisode::isInvalid);
        saveMany(episodes);
    }
    
    public void deleteDispatched() {
        List<AudioEpisode> episodes = all();
        episodes.removeIf(AudioEpisode::isDispatched);
        saveMany(episodes);
    }
    
    public boolean exists(final AudioEpisode episode) {
        return read().contains(episode);
    }
    
    public boolean existsById(final UUID id) {
        return read().stream().anyMatch(episode -> episode.getId().equals(id));
    }
    
    public boolean existsByName(final String name) {
        return read().stream().anyMatch(episode -> episode.getName().equals(name));
    }
    
    public boolean isEmpty() {
        return read().isEmpty();
    }
    
    public long count() {
        return read().size();
    }
    
    public boolean doesNameExist(final String name) {
        return read().stream().anyMatch(episode -> episode.getName().equals(name));
    }
    
    private void write(List<AudioEpisode> episodes) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT)) {
            for (AudioEpisode episode : episodes) {
                csvPrinter.printRecord(episode.toCsv());
            }
        } catch (IOException e) {
            LOGGER.error("Error writing episodes to file", e);
        }
    }
    
    private List<AudioEpisode> read() {
        List<AudioEpisode> episodes = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(new FileReader(file), CSVFormat.DEFAULT)) {
            for (CSVRecord line : csvParser) {
                AudioEpisode episode = createEpisodeFromCsvRecord(line);
                episodes.add(episode);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read episode data from disk, episodes list has been reset to empty", e);
        }
        return episodes;
    }
    
    private AudioEpisode createEpisodeFromCsvRecord(final CSVRecord line) {
        AudioEpisode episode = null;
        try {
            episode = AudioEpisode.createFromCsv(line.toList().toArray(new String[0]));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error reading episode from file, %s".formatted(line.toString()), e);
        }
        return episode;
    }
}
