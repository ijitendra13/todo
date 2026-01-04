package com.app.todoapp.services;

import com.app.todoapp.model.Task;
import com.app.todoapp.model.User;
import com.app.todoapp.repository.TaskMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class TaskService {
    @Autowired
    private final TaskMongoRepository taskRepository;
    @Autowired
    private UserService userService;
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
    public boolean deleteTask(String id, String userName) {
        if (!taskRepository.existsById(id)) return false;
        User user=userService.findByUserName(userName);
        user.getTasks().removeIf(x->x.getId().equals(id));
        userService.saveUserData(user);
        taskRepository.deleteById(id);

        return true;
    }
    @Transactional
    public void saveTask(Task tasks, String userName) {
        try{
            User user=userService.findByUserName(userName);
            tasks.setCompleted(false);
            Task saved = taskRepository.save(tasks);
            user.getTasks().add(saved);
            userService.saveUserData(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry",e);
        }

    }

    public Task updateTaskFields(String id, Map<String, Object> updates) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
                );

        // Only allow specific fields (avoid accidental overwrites)
        if (updates.containsKey("isCompleted")) {
            task.setCompleted(
                    Boolean.parseBoolean(updates.get("isCompleted").toString())
            );
        }
        if (updates.containsKey("title")) {
            task.setTitle(updates.get("title").toString());
        }

        return taskRepository.save(task);
    }

}
