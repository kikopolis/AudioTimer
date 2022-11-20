package com.kikopolis.config;

import java.awt.*;

public interface Configuration {
    String get(final ConfigParam key);
    Integer getInt(final ConfigParam key);
    void set(final ConfigParam key, final String value);
    void save();
    Image loadAppIcon();
}
