package com.kikopolis.gui.panel;

import com.kikopolis.event.Event;
import com.kikopolis.event.RepeatableEvent;
import com.kikopolis.event.SingularEvent;

import javax.swing.*;
import java.util.List;

public class EventList extends JPanel {
    public void display(final List<Event> events) {
        Object[][] data = new Object[events.size()][];
        for (Event event : events) {
            String eventType = event instanceof RepeatableEvent ? "RepeatableEvent" : "SingularEvent";
            data[events.indexOf(event)] = new Object[]{
                    eventType,
                    event.getName(),
                    event.getSound(),
                    event.getHour(),
                    event.getMinute(),
                    eventType.equals("RepeatableEvent") ? ((RepeatableEvent) event).getDayOfWeek() : "N/A",
                    eventType.equals("SingularEvent") ? ((SingularEvent) event).getDate() : "N/A"
            };
        }
        String[] columns = new String[]{"Type", "Name", "Sound", "Hour", "Minute", "Day of Week", "Date"};
        JTable table = new JTable(data, columns);
        add(new JScrollPane(table));
    }
}
