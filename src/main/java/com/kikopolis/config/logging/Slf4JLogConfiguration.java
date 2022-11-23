package com.kikopolis.config.logging;

import com.google.inject.Inject;
import com.kikopolis.config.ConfigKey;
import com.kikopolis.config.Configuration;
import com.kikopolis.util.DirectoryUtil;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.RollingFileAppender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Slf4JLogConfiguration implements LogConfiguration {
    private static final Logger LOGGER = Logger.getLogger(Slf4JLogConfiguration.class.getName());
    private static final String LOG_DIR_PATH_LINUX = "%svar%slog%skikopolis".formatted(File.separator, File.separator, File.separator);
    private static final String LOG_DIR_PATH_WINDOWS = "C:%sProgramData%sKikopolis".formatted(File.separator, File.separator);
    private static final String LOG_FILE_NAME = "audio_timer.log";
    private static final Priority LOGGER_LEVEL = Level.DEBUG;
    private final Configuration configuration;
    
    @Inject
    public Slf4JLogConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }
    
    public void configure() {
        // Remove the fallback logger before appending a new console logger, otherwise we get duplicate logs
        Logger.getRootLogger().removeAllAppenders();
        Logger.getRootLogger().setLevel(Level.DEBUG);
        configureConsoleAppender();
        configureFileAppender();
    }
    
    private void configureConsoleAppender() {
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setLayout(getLayout());
        consoleAppender.setThreshold(LOGGER_LEVEL);
        consoleAppender.activateOptions();
        Logger.getRootLogger().addAppender(consoleAppender);
    }
    
    private void configureFileAppender() {
        File logFile;
        logFile = verifyOrCreateLogFile();
        if (!canLogToFile(logFile)) {
            return;
        }
        FileAppender fileAppender = new RollingFileAppender();
        fileAppender.setFile(logFile.getPath());
        fileAppender.setLayout(getLayout());
        fileAppender.setThreshold(LOGGER_LEVEL);
        fileAppender.setImmediateFlush(true);
        fileAppender.setAppend(true);
        fileAppender.activateOptions();
        Logger.getRootLogger().addAppender(fileAppender);
    }
    
    private File verifyOrCreateLogFile() {
        File configLogFile = new File(configuration.get(ConfigKey.LOG_FILE_PATH_KEY));
        if (configLogFile.exists()) {
            return configLogFile;
        }
        File logFile;
        File osLogFile = getOsSpecificLogFile();
        File fallbackLogFile = getFallbackLogFile();
        if (createLogFile(osLogFile)) {
            logFile = osLogFile;
        } else if (createLogFile(fallbackLogFile)) {
            logFile = fallbackLogFile;
        } else {
            LOGGER.error("Unable to create log files. Logging to console only.");
            return null;
        }
        if (logFile.getPath().equals(fallbackLogFile.getPath())) {
            LOGGER.warn("Unable to create log file in OS specific location. Logging to \"%s\"".formatted(logFile.getPath()));
        }
        configuration.set(ConfigKey.LOG_FILE_PATH_KEY, logFile.getPath());
        return logFile;
    }
    
    private boolean createLogFile(final File logFile) {
        if (!logFile.getParentFile().exists()) {
            createDir(logFile.getParentFile());
        }
        if (!logFile.exists()) {
            createFile(logFile);
        }
        return canLogToFile(logFile);
    }
    
    private boolean canLogToFile(final File logFile) {
        return logFile != null && logFile.exists() && logFile.canRead() && logFile.canWrite();
    }
    
    private void createDir(final File dir) {
        LOGGER.debug("Creating logging directory - \"%s\"".formatted(dir.getPath()));
        try {
            Files.createDirectories(dir.toPath());
        } catch (IOException e) {
            LOGGER.error("Could not create logging directory - \"%s\"".formatted(dir.getPath()));
        }
    }
    
    private void createFile(final File file) {
        LOGGER.debug("Creating log file - \"%s\"".formatted(file.getPath()));
        try {
            Files.createFile(file.toPath());
        } catch (IOException e) {
            LOGGER.error("Could not create log file - \"%s\"".formatted(file.getPath()));
        }
    }
    
    private String getOsLoggingDirectory() {
        String logDirPath;
        if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_MAC) {
            logDirPath = LOG_DIR_PATH_LINUX;
        } else if (SystemUtils.IS_OS_WINDOWS) {
            logDirPath = LOG_DIR_PATH_WINDOWS;
        } else {
            throw new IllegalStateException("Unsupported OS");
        }
        return logDirPath;
    }
    
    private PatternLayout getLayout() {
        return new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
    }
    
    private static File getFallbackLogFile() {
        return new File("%s%slog%s%s".formatted(DirectoryUtil.DATA_DIR, File.separator, File.separator, LOG_FILE_NAME));
    }
    
    private File getOsSpecificLogFile() {
        return new File(getOsLoggingDirectory() + File.separator + LOG_FILE_NAME);
    }
}
