package com.example.tp.controller.dtos.assemblers;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.example.tp.controller.dtos.TaskDto;
import com.example.tp.models.entity.Task;

@Component
public class TaskDtoAssembler {

    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .done(task.getDone())
                .build();
    }

    public Collection<TaskDto> toDtos(Collection<Task> tasks) {
        return tasks.stream().map(this::toDto).toList();
    }

    public Task toEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDone(taskDto.getDone());

        return task;
    }

    public Task toMergeEntity(TaskDto taskDto, Task existingTask) {
        if (taskDto.getTitle() != null) {
            existingTask.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            existingTask.setDescription(taskDto.getDescription());
        }
        if (taskDto.getDone() != null) {
            existingTask.setDone(taskDto.getDone());
        }
        return existingTask;
    }
}