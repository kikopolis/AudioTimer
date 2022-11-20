package com.kikopolis.config;

import com.kikopolis.event.Event;
import com.kikopolis.util.DirectoryUtil;
import com.kikopolis.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class EventWriterAndReaderCsv implements EventWriterAndReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventWriterAndReaderCsv.class);
    private final String eventFilePath;
    private final File eventFile;
    
    @Inject
    public EventWriterAndReaderCsv() {
        eventFilePath = DirectoryUtil.DATA_DIR + File.separator + ".events";
        eventFile = new File(eventFilePath);
    }
    
    @Override
    public void write(final List<Event> events) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(eventFile, false))) {
            oos.writeObject(events);
        } catch (IOException e) {
            LOGGER.error("Unable to write event data to disk", e);
        }
    }
    
    @Override
    public List<Event> read() {
        List<Event> events = new ArrayList<>();
        if (FileUtil.createFileIfNotExists(eventFilePath)) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(eventFile))) {
                List<Event> diskEvents = new ArrayList<>(Arrays.asList((Event[]) ois.readObject()));
                if (!diskEvents.isEmpty()) {
                    events.addAll(diskEvents);
                }
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error("Unable to read event data from disk, events list has been reset to empty", e);
            }
        }
        return events;
    }
}
