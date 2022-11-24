package com.kikopolis.gui.panel;

import com.kikopolis.core.Events;
import com.kikopolis.episode.AudioEpisode;
import com.kikopolis.episode.RecurringAudioEpisode;
import com.kikopolis.episode.SingularAudioEpisode;
import com.kikopolis.event.episode.EpisodeListRefreshEvent;
import com.kikopolis.event.EventSubscriber;
import com.kikopolis.event.Priority;
import org.apache.log4j.Logger;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.swing.*;
import java.util.List;

public class EpisodeList extends JPanel implements EventSubscriber {
    private static final Logger LOGGER = Logger.getLogger(EpisodeList.class.getName());
    
    public EpisodeList() {
        Events.subscribe(this);
    }
    
    // TODO: figure out to refactor to service maybe?
    @Subscribe(threadMode = ThreadMode.MAIN, priority = Priority.LOWEST)
    public void refreshEpisodes(final EpisodeListRefreshEvent event) {
        removeAll();
        display(event.getEpisodes());
        revalidate();
        repaint();
    }
    
    public void display(final List<AudioEpisode> episodes) {
        LOGGER.info("Displaying %s episodes".formatted(episodes.size()));
        Object[][] data = new Object[episodes.size()][];
        for (AudioEpisode episode : episodes) {
            String episodeType = episode instanceof RecurringAudioEpisode ? "RepeatableEpisode" : "SingularEpisode";
            data[episodes.indexOf(episode)] = new Object[]{
                    episodeType,
                    episode.getName(),
                    episode.getSound(),
                    episode.getHour(),
                    episode.getMinute(),
                    episodeType.equals("RepeatableEpisode") ? ((RecurringAudioEpisode) episode).getDayOfWeek() : "N/A",
                    episodeType.equals("SingularEpisode") ? ((SingularAudioEpisode) episode).getDate() : "N/A"
            };
        }
        String[] columns = new String[]{"Type", "Name", "Sound", "Hour", "Minute", "Day of Week", "Date"};
        JTable table = new JTable(data, columns);
        add(new JScrollPane(table));
    }
}
