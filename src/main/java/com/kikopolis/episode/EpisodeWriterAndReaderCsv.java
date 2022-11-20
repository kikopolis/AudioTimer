package com.kikopolis.episode;

import com.kikopolis.util.DirectoryUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class EpisodeWriterAndReaderCsv implements EpisodeWriterAndReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(EpisodeWriterAndReaderCsv.class);
    private final File episodeFile;
    
    @Inject
    public EpisodeWriterAndReaderCsv() {
        episodeFile = new File(DirectoryUtil.DATA_DIR + File.separator + "episodes.csv");
    }
    
    @Override
    public void write(final List<AudioEpisode> episodes) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(episodeFile), CSVFormat.DEFAULT)) {
            for (AudioEpisode episode : episodes) {
                csvPrinter.printRecord(episode.toCsv());
            }
        } catch (IOException e) {
            LOGGER.error("Error writing episodes to file", e);
        }
    }
    
    @Override
    public List<AudioEpisode> read() {
        List<AudioEpisode> episodes = new ArrayList<>();
        try (CSVParser csvParser = CSVParser.parse(new FileReader(episodeFile), CSVFormat.DEFAULT)) {
            for (CSVRecord line : csvParser) {
                AudioEpisode episode = createEpisodeFromCsvRecord(line);
                episodes.add(episode);
            }
        } catch (IOException e) {
            LOGGER.error("Unable to read episode data from disk, episodes list has been reset to empty", e);
        }
        return episodes;
    }
    
    private static AudioEpisode createEpisodeFromCsvRecord(final CSVRecord line) {
        AudioEpisode episode = null;
        try {
            episode = AudioEpisode.createFromCsv(line.toList().toArray(new String[0]));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error reading episode from file, %s".formatted(line.toString()), e);
        }
        return episode;
    }
}
