package com.kikopolis.audio;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;

public interface Player {
    void play();
    void stop();
    void reset();
    boolean isPlaying();
    long getDuration();
    long getPosition();
    void setFile(File file);
    File getFile();
    boolean isInitialized();
    boolean canPlay(File file);
    boolean canPlay(String path);
    AudioFileFormat.Type[] getSupportedFileTypes();
}
