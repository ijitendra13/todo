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
//        ResponseEntity<?> tasks=taskService.getAllTasksOfUser();
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
import com.app.todoapp.model.User;
import com.app.todoapp.repository.TaskMongoRepository;
import com.app.todoapp.services.TaskService;
import com.app.todoapp.services.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class TaskController {
    private final TaskMongoRepository taskMongoRepository;
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllTasksOfUser() {
        List<Task> tasks=taskService.getAllTasks();

        if(tasks!=null && !tasks.isEmpty()){
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllTasksOfUser(@PathVariable String userName) {
        User user=userService.findByUserName(userName);
//        List<Task> tasks=taskService.getAllTasksOfUser();
        List<Task>tasks=user.getTasks();
        if(tasks!=null && !tasks.isEmpty()){
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getTasksById(@PathVariable String id) {
        Optional<Task> task=taskService.getTask(id);
        if(task.isPresent()){
            return new ResponseEntity<>(task.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<Task> createTask(@RequestBody Task task,@PathVariable String userName) {
        try{
            taskService.saveTask(task,userName);
            return new ResponseEntity<>(task,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}/{userName}")
    public ResponseEntity<?> deleteTask(@PathVariable String id,@PathVariable String userName) {
        boolean deleted = taskService.deleteTask(id,userName);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        return ResponseEntity.noContent().build();   // 204 — successful delete, nobody
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        Task updated = taskService.updateTaskFields(id, updates);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }
}
