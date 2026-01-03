package com.app.todoapp.services;

import com.app.todoapp.model.Task;
import com.app.todoapp.repository.TaskMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TaskService {
    @Autowired
    private final TaskMongoRepository taskRepository;

    public TaskService(TaskMongoRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return  taskRepository.findAll();

    }

    public Optional<Task> getTask(String id){
        return taskRepository.findById(id);
    }

    @Transactional
    public boolean deleteTask(String id) {
        if (!taskRepository.existsById(id)) return false;
        taskRepository.deleteById(id);

        return true;
    }

    public void addTask(Task tasks) {
        tasks.setCompleted(false);
        taskRepository.save(tasks);
    }
}
