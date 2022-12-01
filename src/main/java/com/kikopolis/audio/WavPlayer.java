package com.kikopolis.audio;

import org.slf4j.Logger;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class WavPlayer implements Player {
    private static final Logger LOGGER = getLogger(WavPlayer.class);
    private Clip clip;
    private AudioInputStream stream;
    private File file;
    
    public WavPlayer() {}
    
    public WavPlayer(final File file) {
        this.file = file;
        init();
    }
    
    @Override
    public void play() {
        if (!isInitialized()) {
            LOGGER.warn("Player not initialized, cannot play");
            return;
        }
        LOGGER.info("Play method called in WavPlayer");
        clip.start();
    }
    
    @Override
    public void stop() {
        if (!isInitialized()) {
            LOGGER.warn("Player not initialized, cannot stop");
            return;
        }
        LOGGER.info("Stop method called in WavPlayer");
        clip.stop();
    }
    
    @Override
    public void reset() {
        if (!isInitialized()) {
            LOGGER.warn("Player not initialized, cannot reset");
            return;
        }
        LOGGER.info("Reset method called in WavPlayer");
        clip.setMicrosecondPosition(0);
    }
    
    @Override
    public boolean isPlaying() {
        return isInitialized() && clip.isRunning();
    }
    
    @Override
    public long getDuration() {
        return isInitialized() ? clip.getMicrosecondLength() : 0L;
    }
    
    @Override
    public long getPosition() {
        return isInitialized() ? clip.getMicrosecondPosition() : 0L;
    }
    
    @Override
    public void setFile(File file) {
        this.file = file;
        init();
    }
    
    @Override
    public File getFile() {
        return file;
    }
    
    @Override
    public boolean isInitialized() {
        return clip != null && stream != null && file != null;
    }
    
    @Override
    public boolean canPlay(File file) {
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return false;
        }
        try {
            var ais = AudioSystem.getAudioInputStream(file);
            var c = AudioSystem.getClip();
            c.open(ais);
            c.start();
            c.stop();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean canPlay(String path) {
        return canPlay(new File(path));
    }
    
    @Override
    public AudioFileFormat.Type[] getSupportedFileTypes() {
        return AudioSystem.getAudioFileTypes();
    }
    
    private void init() {
        try {
            if (!file.exists()) {
                LOGGER.info("File does not exist, cannot initialize");
                throw new IOException("File does not exist");
            }
            clip = AudioSystem.getClip();
            stream = AudioSystem.getAudioInputStream(file);
            clip.open(stream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
