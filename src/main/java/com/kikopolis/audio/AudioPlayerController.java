package com.kikopolis.audio;

import com.google.inject.Inject;
import com.kikopolis.core.Events;
import com.kikopolis.event.EventSubscriber;
import com.kikopolis.event.Priority;
import com.kikopolis.event.audio.PauseEvent;
import com.kikopolis.event.audio.PlayEvent;
import com.kikopolis.event.audio.ResetEvent;
import com.kikopolis.event.audio.StopEvent;
import com.kikopolis.gui.Dialogs;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;

import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;

public class AudioPlayerController implements EventSubscriber {
    private static final Logger LOGGER = getLogger(AudioPlayerController.class);
    private Player player = null;
    private final Players players;
    
    @Inject
    public AudioPlayerController(final Players players) {
        Events.subscribe(this);
        this.players = players;
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onPlay(final PlayEvent event) {
        LOGGER.info("Play event received");
        var file = event.getFile();
        if (file == null) {
            LOGGER.info("No file provided, using default");
            Dialogs.showMessage("No file provided, using default");
            file = new File("src/main/resources/audio/01 - The Sound of Silence.mp3");
        }
        if (player != null) {
            LOGGER.warn("Player already initialized");
            player.stop();
            player.reset();
            player = null;
        }
        player = getPlayer(file);
        if (player == null) {
            LOGGER.warn("No player found for file: {}", file);
            return;
        }
        player.play();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onStop(final StopEvent event) {
        LOGGER.info("Stop event received");
        if (player == null) {
            genericAudioPlayerError();
            return;
        }
        player.stop();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onPause(final PauseEvent event) {
        LOGGER.info("Pause event received");
        if (player == null) {
            genericAudioPlayerError();
            return;
        }
        player.stop();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onReset(final ResetEvent event) {
        LOGGER.info("Reset event received");
        if (player == null) {
            genericAudioPlayerError();
            return;
        }
        player.reset();
    }
    
    private Player getPlayer(final File file) {
        return players.all()
                      .stream()
                      .filter(p -> p.canPlay(file))
                      .findFirst()
                      .orElse(null);
    }
    
    private static void genericAudioPlayerError() {
        LOGGER.warn("Player not initialized");
        Dialogs.genericApplicationError();
    }
}
