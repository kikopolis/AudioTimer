package com.kikopolis.gui.window;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.core.Events;
import com.kikopolis.eventbus.BusEventSubscriber;
import com.kikopolis.eventbus.Priority;
import com.kikopolis.eventbus.event.EventListRefreshBusEvent;
import com.kikopolis.eventbus.window.ConfigOpenedBusEvent;
import com.kikopolis.eventbus.window.EventListClosedBusEvent;
import com.kikopolis.eventbus.window.EventListOpenBusEvent;
import com.kikopolis.gui.component.button.ActionListenerButton;
import com.kikopolis.gui.panel.UpcomingEventList;
import com.kikopolis.service.ConfigService;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame implements BusEventSubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private final transient ConfigService configService;
    private ActionListenerButton eventListButton;
    
    @Inject
    public Main(final ConfigService configService) {
        Events.subscribe(this);
        this.configService = configService;
        init();
        eventListButton = new ActionListenerButton("Show Full List", e -> Events.post(new EventListOpenBusEvent(new EventList())));
        // Add a button to refresh the event list
        add(eventListButton, gbc);
        add(new ActionListenerButton("Configuration", e -> Events.post(new ConfigOpenedBusEvent(new Config()))), gbc);
        
        // TODO: refactor out all components
//        UpcomingEventList upcomingEventList = new UpcomingEventList();
//        add(upcomingEventList, gbc);
//        upcomingEventList.setVisible(true);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.WINDOW_CONFIGURE)
    public void onEventListWindowOpen(final EventListOpenBusEvent event) {
        eventListButton.setEnabled(false);
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.WINDOW_CONFIGURE)
    public void onEventListWindowClose(final EventListClosedBusEvent event) {
        eventListButton.setEnabled(true);
    }
    
    // TODO: add upcoming event list with ca 10 entries
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.WINDOW_SHOW)
    public void addUpcomingEventList(final EventListRefreshBusEvent event) {
    
    }
    
    // TODO: add a button to show the full event list with all events
    public void addShowEventListButton() {
    
    }
    
    public void addShowConfigurationButton() {
    
    }
    
    private void init() {
        configureLayout();
        configureWindow();
    }
    
    private void configureLayout() {
        setLayout(layout);
    }
    
    private void configureWindow() {
        setTitle(configService.get(ConfigKey.APP_NAME_KEY));
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setIconImage(configService.loadAppIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
