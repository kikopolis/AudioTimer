package com.kikopolis.gui;

import com.google.inject.Injector;
import com.kikopolis.gui.frame.AddTaskFrame;
import com.kikopolis.gui.frame.ConfigFrame;
import com.kikopolis.gui.frame.EditTaskFrame;
import com.kikopolis.gui.frame.ListTaskFrame;
import com.kikopolis.gui.frame.MainFrame;

public class FrameManager {
    private static FrameManager instance = null;
    private final Injector injector;
    
    private FrameManager(final Injector injector) {
        this.injector = injector;
    }
    
    public static FrameManager getInstance(final Injector injector) {
        if (instance == null) {
            instance = new FrameManager(injector);
        }
        return instance;
    }
    
    public static FrameManager getInstance() {
        return instance;
    }
    
    public MainFrame getMainFrame() {
        return injector.getInstance(MainFrame.class);
    }
    
    public AddTaskFrame getAddTaskFrame() {
        return injector.getInstance(AddTaskFrame.class);
    }
    
    public ConfigFrame getConfigFrame() {
        return injector.getInstance(ConfigFrame.class);
    }
    
    public EditTaskFrame getEditTaskFrame() {
        return injector.getInstance(EditTaskFrame.class);
    }
    
    public ListTaskFrame getListTaskFrame() {
        return injector.getInstance(ListTaskFrame.class);
    }
}
