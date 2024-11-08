package com.example.todolistapp;

import java.io.Serializable;

public class Task implements Serializable {
    private  int id;
    private String name;
    private String deadline;
    private int duration;
    private String description;
    private String status;

    public Task(String name, String deadline, int duration, String description, String status) {
        this.name = name;
        this.deadline = deadline;
        this.duration = duration;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
