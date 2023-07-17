package com.lisnenko.tasktracker.rest;

import com.lisnenko.tasktracker.entity.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskRestController {

    private List<Task> theTasks;

    @PostConstruct
    public void loadData() {

        theTasks = new ArrayList<>();

        theTasks.add(new Task("Main", "go to magazine"));
        theTasks.add(new Task("DB", "create DB"));
        theTasks.add(new Task("Shop", "buy milks"));

    }
    @GetMapping("/tasks")
    public List<Task> getTasks() {

        return theTasks;
    }

    @GetMapping("/tasks/{taskId}")
    public Task getTask(@PathVariable int taskId) {

        if ( (taskId >= theTasks.size()) || (taskId < 0)) {
            throw new TaskNotFoundException("Task id not found - " + taskId);
        }

        return theTasks.get(taskId);
    }


}
