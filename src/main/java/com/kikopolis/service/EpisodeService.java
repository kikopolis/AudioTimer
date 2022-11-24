package com.kikopolis.service;

import com.google.inject.Inject;
import com.kikopolis.core.Events;
import com.kikopolis.event.episode.CheckEpisodesForDispatchEvent;
import com.kikopolis.event.episode.EpisodeListRefreshEvent;
import com.kikopolis.event.EventSubscriber;
import com.kikopolis.event.Priority;
import com.kikopolis.event.episode.SaveEpisodeListEvent;
import com.kikopolis.repository.EpisodeRepository;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpisodeService implements EventSubscriber {
    private static final Logger LOGGER = LoggerFactory.getLogger(EpisodeService.class);
    private final EpisodeRepository episodeRepository;
    
    @Inject
    public EpisodeService(final EpisodeRepository episodeRepository) {
        Events.subscribe(this);
        this.episodeRepository = episodeRepository;
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.HIGHEST)
    public void onRefreshEvent(final EpisodeListRefreshEvent event) {
        if (event != null) {
            LOGGER.info("Episode list refresh event received");
            event.setEpisodes(episodeRepository.all());
        }
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.LOWEST)
    public void onCheckEpisodesEvent(final CheckEpisodesForDispatchEvent event) {
        LOGGER.info("Checking episodes for dispatch");
        episodeRepository.all().forEach(episode -> {
            if (episode.isReadyForDispatch()) {
                LOGGER.info("Dispatching episode %s - \"%s\"".formatted(episode.getName(), episode.getId()));
                // TODO: dispatch episode
            }
        });
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.LOWEST)
    public void onSaveListEvent(final SaveEpisodeListEvent event) {
        LOGGER.info("Saving episode list");
        episodeRepository.saveMany(event.getEpisodes());
    }
}
