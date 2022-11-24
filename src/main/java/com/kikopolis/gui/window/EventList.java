package com.kikopolis.gui.window;

import com.kikopolis.core.Events;
import com.kikopolis.eventbus.BusEventSubscriber;
import com.kikopolis.eventbus.Priority;
import com.kikopolis.eventbus.event.EventListRefreshBusEvent;
import com.kikopolis.eventbus.window.EventEditOpenedBusEvent;
import com.kikopolis.eventbus.window.EventListClosedBusEvent;
import com.kikopolis.eventbus.window.EventListOpenBusEvent;
import com.kikopolis.gui.component.button.ActionListenerButton;
import com.kikopolis.gui.component.event.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.swing.*;
import java.awt.*;

public class EventList extends JFrame implements BusEventSubscriber {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private List list;
    
    public EventList() {
        Events.subscribe(this);
        setSize(WIDTH, HEIGHT);
        setTitle("Event List");
        setLayout(layout);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.WINDOW_CONFIGURE)
    public void onEventListWindowOpenEvent(final EventListOpenBusEvent event) {
        // First, dispose of the old event list
        if (list != null) {
            remove(list);
        }
        // Then, create a new event list
        list = new List(event.getEvents());
        
        // Finally, add the new event list to the window and make it visible
        add(list);
        add(new JScrollPane(list));
        list.setVisible(true);
        setVisible(true);
        // add an event edit button somewhere
        addButtons();
    }
    
    // TODO: add a close button for the list
    public void addButtons() {
        JButton closeButton = new ActionListenerButton("Close", e -> {
            Events.post(new EventListClosedBusEvent());
            dispose();
        });
        JButton editButton = new ActionListenerButton("Edit", e -> {
            if (list.getSelectedValue() != null) {
                Events.post(new EventEditOpenedBusEvent(new EventEdit(), list.getSelectedValue()));
            } else {
                JOptionPane.showMessageDialog(this, "Please select an event to edit.");
            }
        });
        add(closeButton, gbc);
        add(editButton, gbc);
        // TODO: when closed, trigger either refresh event or a close event
        // TODO: if a refresh event is triggered, the close event can be deleted
    }
}
