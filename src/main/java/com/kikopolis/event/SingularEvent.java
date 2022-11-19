package com.kikopolis.event;

import java.util.Date;

public interface SingularEvent extends Event {
    Date getDate();
    void setDate(Date date);
}
