package com.kikopolis.repository;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.event.RecurringAudioEvent;
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

public class EventRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventRepository.class);
    private static final File file = new File(DirectoryUtil.DATA_DIR + File.separator + "events.csv");
    
    public List<AudioEvent> all() {
        return read();
    }
    
    public List<AudioEvent> allByDayOfWeek(final DayOfWeek dayOfWeek) {
        List<AudioEvent> events = all();
        events.removeIf(event -> !(event instanceof RecurringAudioEvent recurringAudioEvent) || recurringAudioEvent.getDayOfWeek() != dayOfWeek);
        return events;
    }
    
    public AudioEvent find(final UUID id) {
        return read().stream().filter(event -> event.getId().equals(id)).findFirst().orElse(null);
    }
    
    public AudioEvent findByName(final String name) {
        return read().stream().filter(event -> event.getName().equals(name)).findFirst().orElse(null);
    }
    
    public void saveMany(final List<AudioEvent> events) {
        write(events);
    }
    
    public void save(final AudioEvent event) {
        List<AudioEvent> events = all();
        events.add(event);
        saveMany(events);
    }
    
    public void update(final AudioEvent event) {
        List<AudioEvent> events = all();
        events.set(events.indexOf(event), event);
        saveMany(events);
    }
    
    public void updateById(final UUID id, final AudioEvent event) {
        List<AudioEvent> events = all();
        events.set(events.indexOf(find(id)), event);
        saveMany(events);
    }
    
    public void updateByName(final String name, final AudioEvent event) {
        List<AudioEvent> events = all();
        events.set(events.indexOf(findByName(name)), event);
        saveMany(events);
    }
    
    public void delete(final AudioEvent event) {
        List<AudioEvent> events = all();
        events.remove(event);
        saveMany(events);
    }
    
    public void deleteById(final UUID id) {
        List<AudioEvent> events = all();
        events.remove(find(id));
        saveMany(events);
    }
    
    public void deleteByName(final String name) {
        List<AudioEvent> events = all();
        events.remove(findByName(name));
        saveMany(events);
    }
    
    public void deleteAll() {
        write(List.of());
    }
    
    public void deleteAllInvalid() {
        List<AudioEvent> events = all();
        events.removeIf(AudioEvent::isInvalid);
        saveMany(events);
    }
    
    public void deleteDispatched() {
        List<AudioEvent> events = all();
        events.removeIf(AudioEvent::isDispatched);
        saveMany(events);
    }
    
    public boolean exists(final AudioEvent event) {
        return read().contains(event);
    }
    
    public boolean existsById(final UUID id) {
        return read().stream().anyMatch(event -> event.getId().equals(id));
    }
    
    public boolean existsByName(final String name) {
        return read().stream().anyMatch(event -> event.getName().equals(name));
    }
    
    public boolean isEmpty() {
        return read().isEmpty();
    }
    
    public long count() {
        return read().size();
    }
    
    public boolean doesNameExist(final String name) {
        return read().stream().anyMatch(event -> event.getName().equals(name));
    }
    
    private void write(List<AudioEvent> events) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT)) {
            for (AudioEvent event : events) {
                csvPrinter.printRecord(event.toCsv());
            }
        } catch (IOException e) {
            LOGGER.error("Error writing events to file", e);
        }
    }
    
    private List<AudioEvent> read() {
        List<AudioEvent> events = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(new FileReader(file), CSVFormat.DEFAULT)) {
            for (CSVRecord line : csvParser) {
                AudioEvent event = createEventFromCsvRecord(line);
                events.add(event);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read event data from disk, events list has been reset to empty", e);
        }
        return events;
    }
    
    private AudioEvent createEventFromCsvRecord(final CSVRecord line) {
        AudioEvent event = null;
        try {
            event = AudioEvent.createFromCsv(line.toList().toArray(new String[0]));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error reading event from file, %s".formatted(line.toString()), e);
        }
        return event;
    }
}
