package com.kikopolis.event.episode;

import com.kikopolis.episode.AudioEpisode;
import com.kikopolis.event.Event;

import java.util.ArrayList;
import java.util.List;

public class EpisodeListRefreshEvent implements Event {
    private List<AudioEpisode> episodes;
    
    public EpisodeListRefreshEvent() {
        this.episodes = new ArrayList<>();
    }
    
    public List<AudioEpisode> getEpisodes() {
        return episodes;
    }
    
    public void setEpisodes(List<AudioEpisode> episodes) {
        this.episodes = episodes;
    }
}
