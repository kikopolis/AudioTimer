package com.kikopolis.config;

import java.awt.*;

public interface Configuration {
    String get(final ConfigKey key);
    Integer getInt(final ConfigKey key);
    void set(final ConfigKey key, final String value);
    void save();
    Image loadAppIcon();
}
