package com.kikopolis.gui.component.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ActionListenerButton extends JButton {
    public ActionListenerButton(final String text, final ActionListener actionListener) {
        super(text);
        addActionListener(actionListener);
    }
}
