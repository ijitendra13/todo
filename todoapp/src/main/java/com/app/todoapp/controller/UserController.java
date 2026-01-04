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
import com.app.todoapp.model.User;
import com.app.todoapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getAll(@PathVariable String id){
        return userService.getUser(id);
    }

    @PostMapping()
    public void createUser(@RequestBody User user){
         userService.saveUserData(user);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
       User userdb=userService.findByUserName(user.getUserName());
       if(userdb!=null){
           userdb.setUserName(user.getUserName());
           userdb.setPassword(user.getPassword());
           userService.saveUserData(user);
       }
       
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       
    }




}
