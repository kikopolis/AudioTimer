package com.kikopolis.gui.frame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

public interface SubFrame {
    void showFrame();
    void hideFrame();
    
    default void addCloseListeners() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            var focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            if (focusManager == null) {
                return false;
            }
            if (e.getID() == KeyEvent.KEY_PRESSED
                    && e.getKeyCode() == KeyEvent.VK_ESCAPE
                    && isFocused(focusManager)) {
                hideFrame();
            }
            return false;
        });
    }
    
    private boolean isFocused(final KeyboardFocusManager focusManager) {
        return Objects.equals(focusManager.getFocusedWindow(), this)
                || Objects.equals(focusManager.getActiveWindow(), this)
                || Objects.equals(focusManager.getCurrentFocusCycleRoot(), this);
    }
}
