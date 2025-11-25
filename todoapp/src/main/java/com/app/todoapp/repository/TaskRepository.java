package com.app.todoapp.repository;

import com.app.todoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}

//@Entity is an annotation in JPA used to mark a class as a database table.
//✔ What it does
//Tells Hibernate/JPA: “This class represents a table in the database.”
//Each object of the class becomes a row in that table.

//A Spring Data JPA repository for the Task entity.
//Spring automatically generates all database operations (no SQL needed).
//Type Parameters -> Task → Entity class  , Long → Primary key type
//
//What You Get Automatically (CRUD)
//save() – insert/update
//findById() – get one record
//findAll() – get all records
//deleteById() – delete by ID
//delete() – delete entity
//count() – total rows