package com.kikopolis.gui.frame;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.gui.FrameManager;
import com.kikopolis.gui.component.button.ActionListenerButton;
import com.kikopolis.gui.panel.UpcomingTasks;
import com.kikopolis.service.ConfigService;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.slf4j.LoggerFactory.getLogger;

public class MainFrame extends JFrame implements SubFrame {
    private static final Logger LOGGER = getLogger(MainFrame.class);
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private final transient ConfigService configService;
    private final transient FrameManager frameManager;
    
    @Inject
    public MainFrame(final ConfigService configService) {
        this.configService = configService;
        this.frameManager = FrameManager.getInstance();
        setLayout(layout);
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setTitle(configService.get(ConfigKey.APP_NAME_KEY));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        setTitle(configService.get(ConfigKey.APP_NAME_KEY));
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setIconImage(configService.loadAppIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Add a button to refresh the event list
        add(new ActionListenerButton("Show Full List", e -> {
            var frame = frameManager.getListTaskFrame();
            frame.showFrame();
        }), gbc);
        add(new ActionListenerButton("Configuration", e -> {
            var frame = frameManager.getConfigFrame();
            frame.showFrame();
        }), gbc);
        
        UpcomingTasks upcomingTasks = new UpcomingTasks();
        add(upcomingTasks, gbc);
        
        addCloseListeners();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                hideFrame();
            }
        });
    }
    
    @Override
    public void showFrame() {
        setVisible(true);
    }
    
    @Override
    public void hideFrame() {
        //this is main window, here we don't hide, we exit
        var icon = new ImageIcon(configService.loadAppIcon().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        var answer = JOptionPane.showOptionDialog(
                this,
                "Are you sure you want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                null,
                null
                                                 );
        if (answer == JOptionPane.YES_OPTION) {
            exit();
        }
    }
    
    private void exit() {
        LOGGER.info("Closing");
        LOGGER.info("Exiting Application");
        System.exit(0);
    }
}
