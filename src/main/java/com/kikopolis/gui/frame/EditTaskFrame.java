package com.kikopolis.gui.frame;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.service.ConfigService;
import com.kikopolis.task.AudioTask;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;

import static org.slf4j.LoggerFactory.getLogger;

public class EditTaskFrame extends JFrame implements SubFrame {
    private static final Logger LOGGER = getLogger(EditTaskFrame.class);
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    public static final String TITLE = "Edit Task";
    private final transient ConfigService configService;
    private boolean isDirty = false;
    private AudioTask task;
    
    @Inject
    public EditTaskFrame(final ConfigService configService) {
        this.configService = configService;
        // TODO add OK, Cancel, Apply buttons
        setLayout(layout);
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        addCloseListeners();
    }
    
    @Override
    public void showFrame() {
        setVisible(true);
    }
    
    @Override
    public void hideFrame() {
        var answer = -999;
        if (isDirty) {
            var icon = new ImageIcon(configService.loadAppIcon().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            answer = JOptionPane.showOptionDialog(
                    this,
                    "Are you sure you want to discard the changes to this Task?",
                    "Exit without saving",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    icon,
                    null,
                    null
                                                 );
        }
        if (!isDirty || answer == JOptionPane.YES_OPTION) {
            LOGGER.debug("Closing");
            setVisible(false);
            dispose();
        }
    }
    
    public AudioTask getTask() {
        return task;
    }
    
    public void setTask(final AudioTask task) {
        this.task = task;
        this.renderTask();
    }
    
    private void renderTask() {
        //        JTextField name = new JTextField(task.getName());
//        // TODO: figure out the event sound
//        String[] sounds = new String[]{"sound1.wav", "sound2.wav", "sound3.wav"};
//        JComboBox<String> sound = new JComboBox<>(sounds);
//        JFormattedTextField hour = new JFormattedTextField(task.getHour());
//        JFormattedTextField minute = new JFormattedTextField(task.getMinute());
//        if (task instanceof RecurringAudioTask recurringAudioEvent) {
//
//        } else if (task instanceof SingularAudioTask singularAudioEvent) {
//
//        }
    }
}
