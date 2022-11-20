package com.kikopolis.episode;

import java.util.List;

public interface EpisodeWriterAndReader {
    void write(final List<AudioEpisode> episode);
    List<AudioEpisode> read();
}
