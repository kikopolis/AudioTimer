package com.kikopolis.gui.window;

import com.kikopolis.core.Events;
import com.kikopolis.eventbus.BusEventSubscriber;

import javax.swing.*;
import java.awt.*;

public class Config extends JFrame implements BusEventSubscriber {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    
    public Config() {
        Events.subscribe(this);
        setLayout(layout);
        setSize(WIDTH, HEIGHT);
        setTitle("Configuration");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    
    // TODO add config options
    public void addConfigOptions() {
    
    }
    
    // TODO add OK, Cancel, Apply buttons
    public void addButtons() {
    
    }
}
