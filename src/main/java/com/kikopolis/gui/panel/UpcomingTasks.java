package com.kikopolis.gui.panel;

import com.kikopolis.event.EventSubscriber;
import com.kikopolis.task.AudioTask;
import com.kikopolis.task.RecurringAudioTask;
import com.kikopolis.task.SingularAudioTask;
import com.kikopolis.task.TaskType;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.List;

public class UpcomingTasks extends JPanel implements EventSubscriber {
    private static final Logger LOGGER = Logger.getLogger(UpcomingTasks.class.getName());
    
    public UpcomingTasks() {
        setVisible(true);
    }
    
    public void display(final List<AudioTask> tasks) {
        LOGGER.info("Displaying %s events".formatted(tasks.size()));
        Object[][] data = new Object[tasks.size()][];
        for (AudioTask task : tasks) {
            String taskType = getTaskType(task);
            data[tasks.indexOf(task)] =
                    new Object[]{taskType,
                            task.getName(),
                            task.getSound(),
                            task.getHour(),
                            task.getMinute(),
                            taskType.equals("RepeatableEvent") ? ((RecurringAudioTask) task).getDayOfWeek() : "N/A",
                            taskType.equals("SingularEvent") ? ((SingularAudioTask) task).getDate() : "N/A"};
        }
        String[] columns = new String[]{"Type", "Name", "Sound", "Hour", "Minute", "Day of Week", "Date"};
        JTable table = new JTable(data, columns);
        add(new JScrollPane(table));
    }
    
    private String getTaskType(final AudioTask task) {
        if (task instanceof RecurringAudioTask) {
            return TaskType.RECURRING_AUDIO_TASK;
        } else if (task instanceof SingularAudioTask) {
            return TaskType.SINGULAR_AUDIO_TASK;
        }
        return TaskType.UNDEFINED;
    }
}
