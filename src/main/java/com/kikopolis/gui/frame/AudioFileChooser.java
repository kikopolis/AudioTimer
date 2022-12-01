package com.kikopolis.gui.frame;

import javax.swing.*;

public class AudioFileChooser extends JFileChooser {
    public AudioFileChooser() {
        super();
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setMultiSelectionEnabled(false);
        setFileHidingEnabled(true);
        setAcceptAllFileFilterUsed(false);
        setDialogTitle("Select Audio File");
    }
}
