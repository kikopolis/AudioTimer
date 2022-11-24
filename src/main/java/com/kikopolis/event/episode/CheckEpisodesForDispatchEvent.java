package com.kikopolis.event.episode;

import com.kikopolis.episode.AudioEpisode;
import com.kikopolis.event.Event;

import java.util.ArrayList;
import java.util.List;

public class CheckEpisodesForDispatchEvent implements Event {
    private List<AudioEpisode> episodes;
    
    public CheckEpisodesForDispatchEvent() {
        this.episodes = new ArrayList<>();
    }
    
    public CheckEpisodesForDispatchEvent(List<AudioEpisode> episodes) {
        this.episodes = episodes;
    }
    
    public List<AudioEpisode> getEpisodes() {
        return episodes;
    }
    
    public void setEpisodes(List<AudioEpisode> episodes) {
        this.episodes = episodes;
    }
}
