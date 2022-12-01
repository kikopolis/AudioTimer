package com.kikopolis.gui;

import com.kikopolis.audio.Player;

import javax.sound.sampled.AudioFileFormat;
import javax.swing.*;
import java.util.Arrays;

public final class Dialogs {
    private Dialogs() {}
    
    public static void showMessage(final String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    public static void showUnsupportedFileTypes(final AudioFileFormat.Type[] supportedTypes) {
        JOptionPane.showMessageDialog(null, "Unsupported file type. Supported file types are: " + Arrays.toString(supportedTypes));
    }
    
    public static void showUnsupportedFileTypes(final Player player) {
        showUnsupportedFileTypes(player.getSupportedFileTypes());
    }
    
    public static void genericApplicationError() {
        JOptionPane.showMessageDialog(null, "An error has occurred. Please try again. If this does not fix the problem, please restart the application.");
    }
}
