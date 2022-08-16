package com.tasks.tasks.restController;

import com.tasks.tasks.TaskItem;
import com.tasks.tasks.db.DBWorker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.ArrayList;

@RestController
public class MainController {

    DBWorker dbWorker = new DBWorker("192.168.0.160:3306/tinyTasks", "3306", "root", "bdujlygw");

    @GetMapping("/list")
    public String getListTasks() {
        //JSONArray jsonArray = new JSONArray(dbWorker.getDataFromDB());



        return dbWorker.getDataFromDB().toString();
    }

    @PutMapping("/mod/{taskId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void modifyTask(@PathVariable int taskId) {
        System.out.println(taskId);
    }

    @DeleteMapping("/del/{taskId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void deleteTask(@PathVariable int taskId) {
        System.out.println(taskId);
    }

    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void createTask(@RequestBody TaskItem taskItem) {
        System.out.println(taskItem.taskTitle + taskItem.taskDescription);
    }

}
