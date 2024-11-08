package com.example.todolistapp;

import java.io.Serializable;

public class Task implements Serializable {
    private String name;
    private String deadline;
    private int duration;
    private String description;

    public Task(String name, String deadline, int duration, String description) {
        this.name = name;
        this.deadline = deadline;
        this.duration = duration;
        this.description = description;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
