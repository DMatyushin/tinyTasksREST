package com.tasks.tasks.restController;

import com.tasks.tasks.TaskItem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/list")
    public String getListTasks() {
        return "task list";
    }

    @PutMapping("/mod/{taskId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void modifyTask(@PathVariable int taskId) {
        System.out.println(taskId);
    }

    @PostMapping("/del/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void deleteTask(@RequestParam int taskId) {
        System.out.println(taskId);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void createTask(@RequestBody TaskItem taskItem) {
        System.out.println(taskItem.taskTitle + taskItem.taskDescription);
    }

}
