package com.tasks.tasks.restController;

import java.util.Optional;
import com.tasks.tasks.core.TaskRepository;
import com.tasks.tasks.core.TaskItem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v2")
public class TasksController {

    private final TaskRepository taskRepository;

    public TasksController(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/list")
    public Iterable<TaskItem> getAllTasks () {
        return this.taskRepository.findAll();
    }

    @GetMapping("/list/{id}")
    public Optional<TaskItem> getTaskById ( @PathVariable("id") Integer id) {
        return this.taskRepository.findById(id);
    }

    @DeleteMapping("/del/{id}")
    public void deleteTaskById (@PathVariable("id") Integer id) {

        try {
            this.taskRepository.deleteById(id);
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        throw new ResponseStatusException(HttpStatus.OK);

    }

    @PostMapping("/new")
    public void addNewTask(@RequestBody TaskItem testTaskItem) {
        //this.taskRepository.save(testTaskItem);
        TaskItem newTestTaskItem = this.taskRepository.save(testTaskItem);

        throw new ResponseStatusException(HttpStatus.CREATED);
    }

    @PutMapping("/mod/{id}")
    public TaskItem updateTask( @PathVariable("id") Integer id, @RequestBody TaskItem t) {


        Optional<TaskItem> taskToUpdateOptional = this.taskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            //return null;
        }
        TaskItem taskToUpdate = taskToUpdateOptional.get();

        if (t.getTaskTitle() != null) {
            taskToUpdate.setTaskTitle(t.getTaskTitle());
        }

        if (t.getTaskDescription() != null) {
            taskToUpdate.setTaskDescription(t.getTaskDescription());
        }

        TaskItem updatedTaskItem = this.taskRepository.save(taskToUpdate);


        return updatedTaskItem;
    }
}
