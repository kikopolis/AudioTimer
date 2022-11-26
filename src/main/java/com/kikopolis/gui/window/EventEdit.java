package com.kikopolis.gui.window;

import com.kikopolis.event.AudioEvent;
import com.kikopolis.event.EventType;
import com.kikopolis.event.RecurringAudioEvent;
import com.kikopolis.event.SingularAudioEvent;
import com.kikopolis.eventbus.Priority;
import com.kikopolis.eventbus.window.EventEditOpenedBusEvent;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.swing.*;
import java.awt.*;

public class EventEdit extends JFrame {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    
    public EventEdit() {
        setSize(WIDTH, HEIGHT);
        setTitle("Event Edit");
        setLayout(layout);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    // TODO: add fields for event details
    public void addEventDetailFields() {
    
    }
    
    // TODO: add OK, Cancel and Apply buttons
    public void addButtons() {
        //TODO: on close, trigger an event list refresh
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.WINDOW_SHOW)
    public void onEventEditWindowOpen(final EventEditOpenedBusEvent busEvent) {
        AudioEvent event = busEvent.getEvent();
        JTextField name = new JTextField(event.getName());
        // TODO: figure out the event sound
        String[] sounds = new String[]{"sound1.wav", "sound2.wav", "sound3.wav"};
        JComboBox<String> sound = new JComboBox<>(sounds);
        JFormattedTextField hour = new JFormattedTextField(event.getHour());
        JFormattedTextField minute = new JFormattedTextField(event.getMinute());
        if (event instanceof RecurringAudioEvent recurringAudioEvent) {
        
        } else if (event instanceof SingularAudioEvent singularAudioEvent) {
        
        }
    }
}
