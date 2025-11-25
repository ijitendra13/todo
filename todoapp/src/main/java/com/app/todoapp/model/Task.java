package com.app.todoapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

//@Entity is an annotation in JPA used to mark a class as a database table.
//✔ What it does
//Tells Hibernate/JPA: “This class represents a table in the database.”
//Each object of the class becomes a row in that table.
@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private boolean isCompleted;

}
