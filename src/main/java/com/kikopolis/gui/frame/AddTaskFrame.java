package com.kikopolis.gui.frame;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.gui.panel.AudioPlayerControls;
import com.kikopolis.service.ConfigService;
import com.kikopolis.task.TaskType;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

import static org.slf4j.LoggerFactory.getLogger;

public class AddTaskFrame extends JFrame implements SubFrame {
    private static final Logger LOGGER = getLogger(AddTaskFrame.class);
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    public static final String TITLE = "Add New Task";
    private final ConfigService configService;
    private boolean isDirty = false;
    
    @Inject
    public AddTaskFrame(final ConfigService configService) {
        this.configService = configService;
        // TODO add OK, Cancel, Apply buttons
        setLayout(layout);
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addFields();
        addCloseListeners();
    }
    
    private void addFields() {
        var typeLabel = new JLabel("Task Type");
        var typeField = new JList<String>();
        var types = new Vector<String>();
        types.add(TaskType.SINGULAR_AUDIO_TASK);
        types.add(TaskType.RECURRING_AUDIO_TASK);
        typeField.setListData(types);
        typeField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        typeField.setVisibleRowCount(2);
        var nameLabel = new JLabel("Task Name");
        var nameField = new JTextField();
        // TODO: maybe add the name of the sound file to the label
        var soundLabel = new JLabel("Sound File");
        var soundField = new AudioPlayerControls(null);
        var hourLabel = new JLabel("Hour");
        var hourField = new JFormattedTextField();
        var minuteLabel = new JLabel("Minute");
        var minuteField = new JFormattedTextField();
        addGridRow(Pair.of(typeLabel, typeField), 0, 0);
        addGridRow(Pair.of(nameLabel, nameField), 0, 1);
        addGridRow(Pair.of(soundLabel, soundField), 0, 2);
        addGridRow(Pair.of(hourLabel, hourField), 0, 3);
        addGridRow(Pair.of(minuteLabel, minuteField), 0, 4);
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
                    "Are you sure you wish to close this without saving the new Task?",
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
    
    private void addGridRow(Pair<Component, Component> componentPair, final int x, final int y) {
        gbc.ipady = 40;
        addGridComponent(componentPair.getLeft(), x, y);
        addGridComponent(componentPair.getRight(), x + 1, y);
    }
    
    private void addGridComponent(final Component component, final int x, final int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(component, gbc);
    }
}
