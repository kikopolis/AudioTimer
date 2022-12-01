package com.kikopolis.event.audio;

import com.kikopolis.event.Event;

import java.io.File;

public class FileSelectedEvent implements Event {
    private final File file;
    
    public FileSelectedEvent(final File file) {
        this.file = file;
    }
    
    public File getFile() {
        return file;
    }
}
