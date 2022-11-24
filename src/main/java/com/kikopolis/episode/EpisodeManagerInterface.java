package com.kikopolis.episode;

import java.util.List;

public interface EpisodeManagerInterface {
    void addEpisode(final AudioEpisode episode);
    void removeEpisode(final AudioEpisode episode);
    void checkAndDispatchEpisodes();
    List<AudioEpisode> getEpisodes();
    void setEpisodes(final List<AudioEpisode> episodes);
    void save();
}
