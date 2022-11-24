package com.kikopolis.repository;

import com.google.inject.Inject;
import com.kikopolis.episode.AudioEpisode;
import com.kikopolis.episode.EpisodeWriterAndReader;
import com.kikopolis.episode.RecurringAudioEpisode;
import com.kikopolis.util.DayOfWeek;

import java.util.List;
import java.util.UUID;

public class EpisodeRepository {
    // TODO split reader and writer
    private final EpisodeWriterAndReader episodeWriterAndReader;
    
    // TODO replace episode manager with this repository
    @Inject
    public EpisodeRepository(final EpisodeWriterAndReader episodeWriterAndReader) {
        this.episodeWriterAndReader = episodeWriterAndReader;
    }
    
    public List<AudioEpisode> all() {
        return episodeWriterAndReader.read();
    }
    
    public List<AudioEpisode> allByDayOfWeek(final DayOfWeek dayOfWeek) {
        List<AudioEpisode> episodes = all();
        episodes.removeIf(episode -> !(episode instanceof RecurringAudioEpisode recurringAudioEpisode) || recurringAudioEpisode.getDayOfWeek() != dayOfWeek);
        return episodes;
    }
    
    public AudioEpisode find(final UUID id) {
        return episodeWriterAndReader.read().stream().filter(episode -> episode.getId().equals(id)).findFirst().orElse(null);
    }
    
    public AudioEpisode findByName(final String name) {
        return episodeWriterAndReader.read().stream().filter(episode -> episode.getName().equals(name)).findFirst().orElse(null);
    }
    
    public void saveMany(final List<AudioEpisode> episodes) {
        episodeWriterAndReader.write(episodes);
    }
    
    public void save(final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.add(episode);
        saveMany(episodes);
    }
    
    public void update(final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.set(episodes.indexOf(episode), episode);
        saveMany(episodes);
    }
    
    public void updateById(final UUID id, final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.set(episodes.indexOf(find(id)), episode);
        saveMany(episodes);
    }
    
    public void updateByName(final String name, final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.set(episodes.indexOf(findByName(name)), episode);
        saveMany(episodes);
    }
    
    public void delete(final AudioEpisode episode) {
        List<AudioEpisode> episodes = all();
        episodes.remove(episode);
        saveMany(episodes);
    }
    
    public void deleteById(final UUID id) {
        List<AudioEpisode> episodes = all();
        episodes.remove(find(id));
        saveMany(episodes);
    }
    
    public void deleteByName(final String name) {
        List<AudioEpisode> episodes = all();
        episodes.remove(findByName(name));
        saveMany(episodes);
    }
    
    public void deleteAll() {
        episodeWriterAndReader.write(List.of());
    }
    
    public void deleteAllInvalid() {
        List<AudioEpisode> episodes = all();
        episodes.removeIf(AudioEpisode::isInvalid);
        saveMany(episodes);
    }
    
    public void deleteDispatched() {
        List<AudioEpisode> episodes = all();
        episodes.removeIf(AudioEpisode::isDispatched);
        saveMany(episodes);
    }
    
    public boolean exists(final AudioEpisode episode) {
        return episodeWriterAndReader.read().contains(episode);
    }
    
    public boolean existsById(final UUID id) {
        return episodeWriterAndReader.read().stream().anyMatch(episode -> episode.getId().equals(id));
    }
    
    public boolean existsByName(final String name) {
        return episodeWriterAndReader.read().stream().anyMatch(episode -> episode.getName().equals(name));
    }
    
    public boolean isEmpty() {
        return episodeWriterAndReader.read().isEmpty();
    }
    
    public long count() {
        return episodeWriterAndReader.read().size();
    }
    
    public boolean doesNameExist(final String name) {
        return episodeWriterAndReader.read().stream().anyMatch(episode -> episode.getName().equals(name));
    }
}
