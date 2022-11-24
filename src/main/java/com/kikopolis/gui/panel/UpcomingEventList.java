package com.kikopolis.gui.panel;

import com.kikopolis.core.Events;
import com.kikopolis.event.AudioEvent;
import com.kikopolis.event.RecurringAudioEvent;
import com.kikopolis.event.SingularAudioEvent;
import com.kikopolis.eventbus.event.EventListRefreshBusEvent;
import com.kikopolis.eventbus.BusEventSubscriber;
import com.kikopolis.eventbus.Priority;
import org.apache.log4j.Logger;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.swing.*;
import java.util.List;

public class UpcomingEventList extends JPanel implements BusEventSubscriber {
    private static final Logger LOGGER = Logger.getLogger(UpcomingEventList.class.getName());
    
    public UpcomingEventList() {
        Events.subscribe(this);
    }
    
    // TODO: figure out to refactor to service maybe?
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.LOWEST)
    public void refreshEvents(final EventListRefreshBusEvent event) {
        removeAll();
        display(event.getEvents());
        revalidate();
        repaint();
    }
    
    public void display(final List<AudioEvent> events) {
        LOGGER.info("Displaying %s events".formatted(events.size()));
        Object[][] data = new Object[events.size()][];
        for (AudioEvent event : events) {
            String eventType = event instanceof RecurringAudioEvent ? "RepeatableEvent" : "SingularEvent";
            data[events.indexOf(event)] = new Object[]{
                    eventType,
                    event.getName(),
                    event.getSound(),
                    event.getHour(),
                    event.getMinute(),
                    eventType.equals("RepeatableEvent") ? ((RecurringAudioEvent) event).getDayOfWeek() : "N/A",
                    eventType.equals("SingularEvent") ? ((SingularAudioEvent) event).getDate() : "N/A"
            };
        }
        String[] columns = new String[]{"Type", "Name", "Sound", "Hour", "Minute", "Day of Week", "Date"};
        JTable table = new JTable(data, columns);
        add(new JScrollPane(table));
    }
}
