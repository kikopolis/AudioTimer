package com.kikopolis.event.audio;

import com.kikopolis.event.Event;

import java.io.File;

public class PlayEvent implements Event {
    private final File file;
    
    public PlayEvent(File file) {
        this.file = file;
    }
    
    public File getFile() {
        return file;
    }
}
