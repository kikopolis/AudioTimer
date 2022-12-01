package com.kikopolis.audio;

import com.google.inject.Inject;
import org.slf4j.Logger;

import java.io.File;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class Players {
    private static final Logger LOGGER = getLogger(Players.class);
    private final Set<Player> playerSet;
    
    @Inject
    public Players(final Set<Player> playerSet) {
        if (playerSet == null || playerSet.isEmpty()) {
            LOGGER.warn("No players found");
            throw new IllegalStateException("No players found");
        }
        this.playerSet = playerSet;
    }
    
    public Set<Player> all() {
        return playerSet;
    }
    
    public boolean hasSupportingPlayer(final File file) {
        return playerSet.stream().anyMatch(player -> player.canPlay(file));
    }
    
    public Player getSupportingPlayer(final File file) {
        for (Player player : playerSet) {
            if (player.canPlay(file)) {
                return player;
            }
        }
        return null;
    }
}
