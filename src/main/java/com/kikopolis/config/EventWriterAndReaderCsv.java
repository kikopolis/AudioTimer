package com.kikopolis.config;

import com.kikopolis.event.Event;
import com.kikopolis.util.DirectoryUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class EventWriterAndReaderCsv implements EventWriterAndReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventWriterAndReaderCsv.class);
    private final File eventFile;
    
    @Inject
    public EventWriterAndReaderCsv() {
        eventFile = new File(DirectoryUtil.DATA_DIR + File.separator + "events.csv");
    }
    
    @Override
    public void write(final List<Event> events) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(eventFile), CSVFormat.DEFAULT)) {
            for (Event event : events) {
                csvPrinter.printRecord(event.toCsv());
            }
        } catch (IOException e) {
            LOGGER.error("Error writing events to file", e);
        }
    }
    
    @Override
    public List<Event> read() {
        List<Event> events = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(new FileReader(eventFile), CSVFormat.DEFAULT)) {
            for (CSVRecord line : csvParser) {
                Event event = createEventFromCsvRecord(line);
                events.add(event);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read event data from disk, events list has been reset to empty", e);
        }
        return events;
    }
    
    private static Event createEventFromCsvRecord(final CSVRecord line) {
        Event event = null;
        try {
            event = Event.createFromCsv(line.toList().toArray(new String[0]));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error reading event from file, %s".formatted(line.toString()), e);
        }
        return event;
    }
}
