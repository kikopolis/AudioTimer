package com.kikopolis.gui.panel;

import com.kikopolis.episode.AudioEpisode;
import com.kikopolis.episode.RecurringAudioEpisode;
import com.kikopolis.episode.SingularAudioEpisode;

import javax.swing.*;
import java.util.List;

public class EpisodeList extends JPanel {
    public void display(final List<AudioEpisode> episodes) {
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
