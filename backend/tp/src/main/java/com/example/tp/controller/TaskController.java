package com.example.tp.controller;

import com.example.tp.controller.dtos.TaskDto;
import com.example.tp.controller.dtos.assemblers.TaskDtoAssembler;
import com.example.tp.models.entity.Task;
import com.example.tp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks/")
public class TaskController {

    private final TaskService taskService;
    private final TaskDtoAssembler taskDtoAssembler;

    @Autowired
    public TaskController(TaskService taskService, TaskDtoAssembler taskDtoAssembler) {
        this.taskService = taskService;
        this.taskDtoAssembler = taskDtoAssembler;
    }

    @GetMapping
    public ResponseEntity<Collection<TaskDto>> getAll() {
        List<Task> list = taskService.findAll(Pageable.unpaged()).getContent();
        return ResponseEntity.ok(taskDtoAssembler.toDtos(list));
    }

    @GetMapping("{id}/")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(taskDtoAssembler.toDto(task.get()));
    }

    @PostMapping
    public ResponseEntity<TaskDto> post(@RequestBody TaskDto taskDto) {
        Task task = taskService.create(taskDtoAssembler.toEntity(taskDto));
        return ResponseEntity.ok(taskDtoAssembler.toDto(task));
    }

    @PutMapping("{id}/")
    public ResponseEntity<TaskDto> put(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        if (!taskService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskDto.setId(id);
        Task task = taskService.update(taskDtoAssembler.toEntity(taskDto));
        return ResponseEntity.ok(taskDtoAssembler.toDto(task));
    }

    @PatchMapping("{id}/")
public ResponseEntity<TaskDto> patch(@PathVariable Long id, @RequestBody TaskDto taskDto) {
    if (!taskService.existsById(id)) {
        return ResponseEntity.notFound().build();
    }

    Task existingTask = taskService.findById(id).get(); // sûr ici, on vient de vérifier existsById
    Task merged = taskDtoAssembler.toMergeEntity(taskDto, existingTask);
    Task saved = taskService.update(merged);

    return ResponseEntity.ok(taskDtoAssembler.toDto(saved));
}

    @DeleteMapping("{id}/")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (taskService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}