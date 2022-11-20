package com.kikopolis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class.getName());
    
    private FileUtil() {
    }
    
    public static boolean createFileIfNotExists(final String path) {
        boolean created = false;
        File file = new File(path);
        if (!file.exists()) {
            try {
                created = file.createNewFile();
            } catch (IOException e) {
                LOGGER.warn("Could not create file: {}", path);
            }
        }
        return created;
    }
    
    public static void copyImageToDirectory(final String imagePath, final String directoryPath) {
        File image = new File(imagePath);
        File directory = new File(directoryPath);
        if (image.exists() && directory.exists()) {
            try {
                Files.copy(image.toPath(), new File(directoryPath + File.separator + image.getName()).toPath());
            } catch (IOException e) {
                LOGGER.warn("Could not copy image to directory: {}", directoryPath);
            }
        }
    }
}
