package com.kikopolis.gui.frame;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.service.ConfigService;
import org.slf4j.Logger;

import javax.swing.*;

import java.awt.*;

import static org.slf4j.LoggerFactory.getLogger;

public class AddTaskFrame extends JFrame implements SubFrame {
    private static final Logger LOGGER = getLogger(AddTaskFrame.class);
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    public static final String TITLE = "Add New Task";
    private ConfigService configService;
    private boolean isDirty = false;
    
    @Inject
    public AddTaskFrame(final ConfigService configService) {
        this.configService = configService;
        // TODO add OK, Cancel, Apply buttons
        setLayout(layout);
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        addCloseListeners();
    }
    
    @Override
    public void showFrame() {
        setVisible(true);
    }
    
    @Override
    public void hideFrame() {
        var answer = -999;
        if (isDirty) {
            var icon = new ImageIcon(configService.loadAppIcon().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
            answer = JOptionPane.showOptionDialog(
                    this,
                    "Are you sure you wish to close this without saving the new Task?",
                    "Exit without saving",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    icon,
                    null,
                    null
                                                 );
        }
        if (!isDirty || answer == JOptionPane.YES_OPTION) {
            LOGGER.debug("Closing");
            setVisible(false);
            dispose();
        }
    }
}
