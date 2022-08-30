package com.tasks.tasks.restController;

import com.tasks.tasks.core.TaskItem;
import com.tasks.tasks.core.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

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

        if (this.taskRepository.findById(id).isPresent()) {
            return this.taskRepository.findById(id);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/del/{id}")
    public void deleteTaskById (@PathVariable("id") Integer id) {

        if (this.taskRepository.findById(id).isPresent()) {
            this.taskRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.OK);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/new")
    public void addNewTask(@RequestBody TaskItem testTaskItem) {

        testTaskItem.setCreateTime(Instant.now().getEpochSecond());
        TaskItem newTestTaskItem = this.taskRepository.save(testTaskItem);

        if (this.taskRepository.findById(testTaskItem.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CREATED);
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/mod/{id}")
    public TaskItem updateTask( @PathVariable("id") Integer id, @RequestBody TaskItem t) {


        Optional<TaskItem> taskToUpdateOptional = this.taskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
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
