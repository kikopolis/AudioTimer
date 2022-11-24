package com.kikopolis.gui.component.event;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.event.EventType;
import com.kikopolis.event.SingularAudioEvent;
import com.kikopolis.service.ConfigService;

import javax.swing.*;
import java.awt.*;

public class AudioEventListCellRenderer extends JLabel implements ListCellRenderer<AudioEvent> {
    @Override
    public Component getListCellRendererComponent(
            JList<? extends AudioEvent> list,
            final AudioEvent event,
            int index,
            boolean isSelected,
            boolean cellHasFocus
                                                 ) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setOpaque(true);
        setText(formattedText(event));
        if (isSelected) {
            setBackground(ConfigService.getHighlightColor());
            setForeground(Color.WHITE);
        } else {
            setBackground(ConfigService.getBackgroundColor());
            setForeground(Color.BLACK);
        }
        return this;
    }
    
    private String formattedText(final AudioEvent event) {
        if (event instanceof SingularAudioEvent singularAudioEvent) {
            return "%s : \"%s\" - %s . %s %s:%s"
                    .formatted(
                            EventType.SINGULAR_AUDIO_EVENT,
                            singularAudioEvent.getName(),
                            singularAudioEvent.getSound(),
                            singularAudioEvent.getDate(),
                            singularAudioEvent.getHour(),
                            singularAudioEvent.getMinute()
                              );
        } else if (event instanceof com.kikopolis.event.RecurringAudioEvent recurringAudioEvent) {
            return "%s : \"%s\" - %s . %s %s:%s"
                    .formatted(
                            EventType.RECURRING_AUDIO_EVENT,
                            recurringAudioEvent.getName(),
                            recurringAudioEvent.getSound(),
                            recurringAudioEvent.getDayOfWeek(),
                            recurringAudioEvent.getHour(),
                            recurringAudioEvent.getMinute()
                              );
        } else {
            return "UNDEFINED EVENT : \"%s\" - %s . UNDEFINED DATE/DAY OF WEEK %s:%s"
                    .formatted(
                            event.getName(),
                            event.getSound(),
                            event.getHour(),
                            event.getMinute()
                              );
        }
    }
}
