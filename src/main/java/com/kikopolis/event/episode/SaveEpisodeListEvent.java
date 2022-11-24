package com.kikopolis.event.episode;

import com.kikopolis.episode.AudioEpisode;
import com.kikopolis.event.Event;

import java.util.ArrayList;
import java.util.List;

public class SaveEpisodeListEvent implements Event {
    private List<AudioEpisode> episodes;
    
    public SaveEpisodeListEvent() {
        episodes = new ArrayList<>();
    }
    
    public SaveEpisodeListEvent(List<AudioEpisode> episodes) {
        this.episodes = episodes;
    }
    
    public List<AudioEpisode> getEpisodes() {
        return episodes;
    }
    
    public void setEpisodes(List<AudioEpisode> episodes) {
        this.episodes = episodes;
    }
}
