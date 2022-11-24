package com.kikopolis.gui.component.event;

import com.kikopolis.event.AudioEvent;

import javax.swing.*;

public class List extends JList<AudioEvent> {
    public List(java.util.List<AudioEvent> events) {
        AudioEvent[] eventArray = new AudioEvent[events.size()];
        events.toArray(eventArray);
        setListData(eventArray);
        setVisibleRowCount(20);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellRenderer(new AudioEventListCellRenderer());
    }
    
    public void setData(java.util.List<AudioEvent> events) {
        AudioEvent[] eventArray = new AudioEvent[events.size()];
        events.toArray(eventArray);
        setListData(eventArray);
    }
}
