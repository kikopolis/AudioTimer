package com.kikopolis.gui.button;

import com.kikopolis.core.Events;
import com.kikopolis.event.episode.EpisodeListRefreshEvent;

import javax.swing.*;

public class RefreshButton extends JButton {
    public RefreshButton() {
        super("Refresh");
        addActionListener(e -> Events.post(new EpisodeListRefreshEvent()));
    }
}
