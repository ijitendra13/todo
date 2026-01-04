package com.app.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TodoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
}

//MongoDatabaseFactory is a Spring interface that provides access to the MongoDB database.
//It creates and manages connections to MongoDB and gives Spring the MongoDatabase object whenever repositories or transactions need it.
//
//Spring uses it internally for things like repositories, templates (MongoTemplate), and transactions.


//PlatformTransactionManager is a Spring interface that manages transactions (begin, commit, rollback) in a generic way.
//MongoTransactionManager is its MongoDB implementation, letting multiple Mongo operations run as a single transaction — so if one fails, everything rolls back.
//example
//@Transactional
//public void saveUserAndTask(User user, Task task) {
//    userRepo.save(user);
//    taskRepo.save(task);
//}
