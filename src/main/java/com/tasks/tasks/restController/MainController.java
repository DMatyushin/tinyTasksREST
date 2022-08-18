package com.tasks.tasks.restController;

import com.tasks.tasks.core.ModifiedTask;
import com.tasks.tasks.core.TaskItem;
import com.tasks.tasks.core.DBWorker;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MainController {

    DBWorker dbWorker = new DBWorker("192.168.0.160:3306/tinyTasks", "3306", "root", "bdujlygw");

    @GetMapping("/list")
    public String getListTasks() {
        return dbWorker.getDataFromDB().toString();
    }

    @PostMapping("/mod")
    //@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void modifyTask( @RequestBody ModifiedTask modifiedTask ) {
        if ( modifiedTask.checkObject() ) {
            try {
                dbWorker.modifyTaskItem(modifiedTask);
            } catch(Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object is not valid");
        }

        throw new ResponseStatusException(HttpStatus.OK);
    }

    @DeleteMapping("/del/{taskId}")
    //@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void deleteTask( @PathVariable int taskId ) {

        try {
            dbWorker.removeTaskItem(taskId);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        throw new ResponseStatusException(HttpStatus.OK);

    }

    @PostMapping("/create")
    //@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Data accepted")
    public void createTask( @RequestBody TaskItem taskItem ) {

        if ( taskItem.taskTitle == null ) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Object is empty"
            );
        } else {
            try {
                dbWorker.addTaskItem(taskItem);

            } catch(Exception e) {
                System.out.println(e);
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Data is not valid or DB is off.");

            }
            throw new ResponseStatusException(HttpStatus.OK);
        }


    }

}
