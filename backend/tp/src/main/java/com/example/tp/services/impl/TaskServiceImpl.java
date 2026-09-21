package com.example.tp.services.impl;

import org.springframework.stereotype.Service;

import com.example.tp.models.entity.Task;
import com.example.tp.repository.TaskRepository;
import com.example.tp.services.TaskService;
import com.example.tp.services.commons.AbstractService;

@Service 
public class TaskServiceImpl extends AbstractService<Task, TaskRepository> implements TaskService {

    public TaskServiceImpl(TaskRepository repository) {
        super(repository);
    }

}
