package com.kikopolis.config;

import java.util.Map;

public interface ConfigWriterAndReader {
    void write(final Map<String, String> configData);
    Map<String, String> read();
}
