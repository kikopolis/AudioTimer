package com.kikopolis.gui.frame;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.repository.ConfigRepository;
import com.kikopolis.service.ConfigService;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;

import static org.slf4j.LoggerFactory.getLogger;

public class ConfigFrame extends JFrame implements ResettableFrame {
    private static final Logger LOGGER = getLogger(ConfigFrame.class);
    private static final GridBagLayout layout = new GridBagLayout();
    private static final GridBagConstraints gbc = new GridBagConstraints();
    public static final String TITLE = "Configuration";
    private final ConfigService configService;
    private final ConfigRepository configRepository;
    private boolean isDirty = false;
    
    @Inject
    public ConfigFrame(final ConfigService configService, final ConfigRepository configRepository) {
        this.configService = configService;
        this.configRepository = configRepository;
        // TODO add OK, Cancel, Apply buttons
        setLayout(layout);
        setSize(configService.getInt(ConfigKey.WIDTH_KEY), configService.getInt(ConfigKey.HEIGHT_KEY));
        setTitle(TITLE);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        
        addCloseListeners();
    }
    
    @Override
    public void reset() {
    
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
                    "Are you sure you wish to close this without saving changes to the application configuration?",
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
