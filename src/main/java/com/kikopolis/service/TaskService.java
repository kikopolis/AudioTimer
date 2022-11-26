package com.kikopolis.service;

import com.google.inject.Inject;
import com.kikopolis.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    
    @Inject
    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
