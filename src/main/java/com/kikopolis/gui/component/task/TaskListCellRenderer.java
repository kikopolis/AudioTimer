package com.kikopolis.gui.component.task;

import com.kikopolis.task.AudioTask;
import com.kikopolis.task.RecurringAudioTask;
import com.kikopolis.task.TaskType;
import com.kikopolis.task.SingularAudioTask;
import com.kikopolis.service.ConfigService;

import javax.swing.*;
import java.awt.*;

public class TaskListCellRenderer extends JLabel implements ListCellRenderer<AudioTask> {
    @Override
    public Component getListCellRendererComponent(
            JList<? extends AudioTask> list,
            final AudioTask task,
            int index,
            boolean isSelected,
            boolean cellHasFocus
                                                 ) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setOpaque(true);
        setText(formattedText(task));
        if (isSelected) {
            setBackground(ConfigService.getHighlightColor());
            setForeground(Color.WHITE);
        } else {
            setBackground(ConfigService.getBackgroundColor());
            setForeground(Color.BLACK);
        }
        return this;
    }
    
    private String formattedText(final AudioTask task) {
        if (task instanceof SingularAudioTask singularAudioTask) {
            return "%s : \"%s\" - %s . %s %s:%s"
                    .formatted(
                            TaskType.SINGULAR_AUDIO_TASK,
                            singularAudioTask.getName(),
                            singularAudioTask.getSound(),
                            singularAudioTask.getDate(),
                            singularAudioTask.getHour(),
                            singularAudioTask.getMinute()
                              );
        } else if (task instanceof RecurringAudioTask recurringAudioTask) {
            return "%s : \"%s\" - %s . %s %s:%s"
                    .formatted(
                            TaskType.RECURRING_AUDIO_TASK,
                            recurringAudioTask.getName(),
                            recurringAudioTask.getSound(),
                            recurringAudioTask.getDayOfWeek(),
                            recurringAudioTask.getHour(),
                            recurringAudioTask.getMinute()
                              );
        } else {
            return "%s : \"%s\" - %s . UNDEFINED DATE/DAY OF WEEK %s:%s"
                    .formatted(
                            TaskType.UNDEFINED,
                            task.getName(),
                            task.getSound(),
                            task.getHour(),
                            task.getMinute()
                              );
        }
    }
}
