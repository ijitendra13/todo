package com.app.todoapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

//@Entity is an annotation in JPA used to mark a class as a database table.
//✔ What it does
//Tells Hibernate/JPA: “This class represents a table in the database.”
//Each object of the class becomes a row in that table.
@Document(collection = "todo")
@Data
public class Task {
    @Id
    private String id;
    private String title;
    @JsonProperty("isCompleted")
    private boolean completed;


}

