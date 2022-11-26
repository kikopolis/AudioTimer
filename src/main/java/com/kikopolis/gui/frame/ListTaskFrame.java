package com.kikopolis.gui.frame;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.gui.FrameManager;
import com.kikopolis.gui.component.button.ActionListenerButton;
import com.kikopolis.gui.component.task.TaskListCellRenderer;
import com.kikopolis.repository.TaskRepository;
import com.kikopolis.service.ConfigService;
import com.kikopolis.task.AudioTask;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ListTaskFrame extends JFrame implements ResettableFrame {
    private static final Logger LOGGER = getLogger(ListTaskFrame.class);
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    public static final String TITLE = "List of all Tasks";
    private final JList<AudioTask> taskJList;
    private final ConfigService configService;
    private final AudioTask[] tasks;
    private final FrameManager frameManager;
    
    @Inject
    public ListTaskFrame(final ConfigService configService, final TaskRepository taskRepository) {
        this.configService = configService;
        this.frameManager = FrameManager.getInstance();
        var allTasks = taskRepository.all();
        tasks = new AudioTask[allTasks.size()];
        allTasks.toArray(tasks);
        // TODO add OK, Cancel, Apply buttons
        setLayout(layout);
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        taskJList = new JList<>();
        taskJList.setListData(tasks);
        taskJList.setVisibleRowCount(20);
        taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskJList.setCellRenderer(new TaskListCellRenderer());
        add(taskJList);
        add(new JScrollPane(taskJList));
        taskJList.setVisible(true);
        addButtons();
        
        addCloseListeners();
    }
    
    private void addButtons() {
        JButton addButton = new ActionListenerButton("Add Task", e -> {
            var frame = frameManager.getAddTaskFrame();
            frame.showFrame();
        });
        JButton editButton = new ActionListenerButton("Edit", e -> {
            if (taskJList.getSelectedValue() != null) {
                var editTaskFrame = frameManager.getEditTaskFrame();
                editTaskFrame.setTask(taskJList.getSelectedValue());
                editTaskFrame.showFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to edit.");
            }
        });
        JButton closeButton = new ActionListenerButton("Close", e -> hideFrame());
        add(addButton, gbc);
        add(closeButton, gbc);
        add(editButton, gbc);
        // TODO: when closed, trigger either refresh event or a close event
        // TODO: if a refresh event is triggered, the close event can be deleted
    }
    
    @Override
    public void reset() {
    
    }
    
    @Override
    public void showFrame() {
        setVisible(true);
    }
    
    @Override
    public void hideFrame() {
        LOGGER.debug("Closing");
        setVisible(false);
        dispose();
    }
}
