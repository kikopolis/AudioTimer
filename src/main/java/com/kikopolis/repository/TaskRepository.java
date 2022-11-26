package com.kikopolis.repository;

import com.kikopolis.task.AudioTask;
import com.kikopolis.task.RecurringAudioTask;
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

public class TaskRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRepository.class);
    private static final File file = new File(DirectoryUtil.DATA_DIR + File.separator + "tasks.csv");
    
    public List<AudioTask> all() {
        return read();
    }
    
    public List<AudioTask> allByDayOfWeek(final DayOfWeek dayOfWeek) {
        List<AudioTask> tasks = all();
        tasks.removeIf(task -> !(task instanceof RecurringAudioTask recurringAudioTask) || recurringAudioTask.getDayOfWeek() != dayOfWeek);
        return tasks;
    }
    
    public AudioTask find(final UUID id) {
        return read().stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }
    
    public AudioTask findByName(final String name) {
        return read().stream().filter(task -> task.getName().equals(name)).findFirst().orElse(null);
    }
    
    public void saveMany(final List<AudioTask> tasks) {
        write(tasks);
    }
    
    public void save(final AudioTask task) {
        List<AudioTask> tasks = all();
        tasks.add(task);
        saveMany(tasks);
    }
    
    public void update(final AudioTask task) {
        List<AudioTask> tasks = all();
        tasks.set(tasks.indexOf(task), task);
        saveMany(tasks);
    }
    
    public void updateById(final UUID id, final AudioTask task) {
        List<AudioTask> tasks = all();
        tasks.set(tasks.indexOf(find(id)), task);
        saveMany(tasks);
    }
    
    public void updateByName(final String name, final AudioTask task) {
        List<AudioTask> tasks = all();
        tasks.set(tasks.indexOf(findByName(name)), task);
        saveMany(tasks);
    }
    
    public void delete(final AudioTask task) {
        List<AudioTask> tasks = all();
        tasks.remove(task);
        saveMany(tasks);
    }
    
    public void deleteById(final UUID id) {
        List<AudioTask> tasks = all();
        tasks.remove(find(id));
        saveMany(tasks);
    }
    
    public void deleteByName(final String name) {
        List<AudioTask> tasks = all();
        tasks.remove(findByName(name));
        saveMany(tasks);
    }
    
    public void deleteAll() {
        write(List.of());
    }
    
    public void deleteAllInvalid() {
        List<AudioTask> tasks = all();
        tasks.removeIf(AudioTask::isInvalid);
        saveMany(tasks);
    }
    
    public void deleteDispatched() {
        List<AudioTask> tasks = all();
        tasks.removeIf(AudioTask::isDispatched);
        saveMany(tasks);
    }
    
    public boolean exists(final AudioTask task) {
        return read().contains(task);
    }
    
    public boolean existsById(final UUID id) {
        return read().stream().anyMatch(task -> task.getId().equals(id));
    }
    
    public boolean existsByName(final String name) {
        return read().stream().anyMatch(task -> task.getName().equals(name));
    }
    
    public boolean isEmpty() {
        return read().isEmpty();
    }
    
    public long count() {
        return read().size();
    }
    
    public boolean doesNameExist(final String name) {
        return read().stream().anyMatch(task -> task.getName().equals(name));
    }
    
    private void write(List<AudioTask> tasks) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(file), CSVFormat.DEFAULT)) {
            for (AudioTask task : tasks) {
                csvPrinter.printRecord(task.toCsv());
            }
        } catch (IOException e) {
            LOGGER.error("Error writing tasks to file", e);
        }
    }
    
    private List<AudioTask> read() {
        List<AudioTask> tasks = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(new FileReader(file), CSVFormat.DEFAULT)) {
            for (CSVRecord line : csvParser) {
                AudioTask task = createTaskFromCsvLine(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read task data from disk, tasks list has been reset to empty", e);
        }
        return tasks;
    }
    
    private AudioTask createTaskFromCsvLine(final CSVRecord line) {
        AudioTask task = null;
        try {
            task = AudioTask.createFromCsv(line.toList().toArray(new String[0]));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error reading task from file, %s".formatted(line.toString()), e);
        }
        return task;
    }
}
