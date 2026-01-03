//package com.app.todoapp.controller;
//
//import com.app.todoapp.model.Task;
//import com.app.todoapp.services.TaskService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/tasks")
//@CrossOrigin(origins = "http://localhost:4200") // Allow requests from your Angular app's origin
//public class TaskController {
//
//    private final TaskService taskService;
//
//    public TaskController(TaskService taskService){
//        this.taskService=taskService;
//    }
//
//    @GetMapping
//    public ResponseEntity<?> getTasks(){
//        ResponseEntity<?> tasks=taskService.getAllTasks();
//        System.out.println(tasks);
//        return tasks;
//    }
//
//    @PostMapping
//    public Task addTasks(@RequestBody Task tasks){
//       taskService.addTask(tasks);
//        return tasks;
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteTasks(@PathVariable("id") Long id){
//      return  taskService.deleteTask(id);
//    }
//}
//
package com.app.todoapp.controller;
import com.app.todoapp.model.Task;
import com.app.todoapp.repository.TaskMongoRepository;
import com.app.todoapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
    @Autowired
    private TaskMongoRepository taskMongoRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        List<Task> tasks=taskService.getAllTasks();
        if(tasks!=null && !tasks.isEmpty()){
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTasksById(@PathVariable String id) {
        Optional<Task> task=taskService.getTask(id);
        if(task.isPresent()){
            return new ResponseEntity<>(task.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try{
            taskService.addTask(task);
            return new ResponseEntity<>(task,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        boolean deleted = taskService.deleteTask(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.noContent().build();   // 204 — successful delete, no body
    }

    @PatchMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        var taskOpt = taskMongoRepository.findById(id);
        if (taskOpt.isPresent()) {
            var taskEntity = taskOpt.get();
            if (updates.containsKey("isCompleted")) {
                taskEntity.setCompleted((Boolean) updates.get("isCompleted"));
            }
            return taskMongoRepository.save(taskEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }
    }
}
