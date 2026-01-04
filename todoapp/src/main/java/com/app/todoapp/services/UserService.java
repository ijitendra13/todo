package com.app.todoapp.services;

import com.app.todoapp.model.User;
import com.app.todoapp.repository.UserMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {
    @Autowired
    private final UserMongoRepository userMongoRepository;

    public UserService(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    public List<User> getAll() {
        return  userMongoRepository.findAll();

    }

    public Optional<User> getUser(String id){
        return userMongoRepository.findById(id);
    }

    @Transactional
    public boolean deleteUser(String id) {
        if (!userMongoRepository.existsById(id)) return false;
        userMongoRepository.deleteById(id);

        return true;
    }

    public void saveUserData(User user) {
        try {
            userMongoRepository.save(user);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
     }

     public User findByUserName(String user){
       return userMongoRepository.findByUserName(user);
     }
}
