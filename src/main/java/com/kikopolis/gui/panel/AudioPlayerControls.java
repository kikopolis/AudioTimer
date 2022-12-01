package com.kikopolis.gui.panel;

import com.kikopolis.core.Events;
import com.kikopolis.event.EventSubscriber;
import com.kikopolis.event.Priority;
import com.kikopolis.event.audio.FileSelectedEvent;
import com.kikopolis.event.audio.OpenAudioChooserEvent;
import com.kikopolis.event.audio.PauseEvent;
import com.kikopolis.event.audio.PlayEvent;
import com.kikopolis.event.audio.ResetEvent;
import com.kikopolis.event.audio.StopEvent;
import com.kikopolis.gui.component.button.ActionListenerButton;
import com.kikopolis.gui.frame.AudioFileChooser;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class AudioPlayerControls extends JPanel implements EventSubscriber {
    private static final Logger LOGGER = getLogger(AudioPlayerControls.class);
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private final Icon playIcon;
    private final Icon pauseIcon;
    private final Icon stopIcon;
    private final Icon openFileIcon;
    private final Icon resetIcon;
    private final ActionListenerButton playButton;
    private final ActionListenerButton pauseButton;
    private final ActionListenerButton stopButton;
    private final ActionListenerButton openFileButton;
    private final ActionListenerButton resetButton;
    private File file;
    
    public AudioPlayerControls(final File file) {
        Events.subscribe(this);
        this.file = file;
        setLayout(layout);
        var unScaledPlayIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/player/play.png")));
        var unScaledPauseIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/player/pause.png")));
        var unScaledStopIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/player/stop.png")));
        var unScaledOpenFileIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/player/open-file.png")));
        var unScaledResetIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/player/reset.png")));
        playIcon = new ImageIcon(unScaledPlayIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        pauseIcon = new ImageIcon(unScaledPauseIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        stopIcon = new ImageIcon(unScaledStopIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        openFileIcon = new ImageIcon(unScaledOpenFileIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        resetIcon = new ImageIcon(unScaledResetIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        playButton = new ActionListenerButton(playIcon, e -> Events.post(new PlayEvent(file)));
        pauseButton = new ActionListenerButton(pauseIcon, e -> Events.post(new PauseEvent()));
        stopButton = new ActionListenerButton(stopIcon, e -> Events.post(new StopEvent()));
        openFileButton = new ActionListenerButton(openFileIcon, e -> Events.post(new OpenAudioChooserEvent()));
        resetButton = new ActionListenerButton(resetIcon, e -> Events.post(new ResetEvent()));
        addGridComponent(openFileButton, 0, 0);
        addGridComponent(playButton, 1, 0);
        addGridComponent(pauseButton, 2, 0);
        addGridComponent(stopButton, 3, 0);
        addGridComponent(resetButton, 4, 0);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onFileSelected(final FileSelectedEvent event) {
        LOGGER.info("File selected: {}", event.getFile());
        this.file = event.getFile();
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onAudioChooserOpen(final OpenAudioChooserEvent event) {
        LOGGER.info("Open audio chooser");
        final AudioFileChooser audioFileChooser = new AudioFileChooser();
        audioFileChooser.setVisible(true);
        var fileChooser = new AudioFileChooser();
        var result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            var selectedFile = fileChooser.getSelectedFile();
            LOGGER.debug("Selected file: {}", selectedFile.getAbsolutePath());
            Events.post(new FileSelectedEvent(selectedFile));
        }
    }
    
    private void addGridComponent(final Component component, final int x, final int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(component, gbc);
    }
}
