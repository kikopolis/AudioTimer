package com.kikopolis.config;

import com.kikopolis.event.Event;

import java.util.List;

public interface EventWriterAndReader {
    void write(final List<Event> event);
    List<Event> read();
}
