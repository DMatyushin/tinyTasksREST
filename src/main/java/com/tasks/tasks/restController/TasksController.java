package com.tasks.tasks.restController;

import java.util.Optional;
import com.tasks.tasks.core.TaskRepository;
import com.tasks.tasks.core.TestTaskItem;
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
    public Iterable<TestTaskItem> getAllTasks () {
        return this.taskRepository.findAll();
    }

    @GetMapping("/list/{id}")
    public Optional<TestTaskItem> getTaskById (@PathVariable("id") Integer id) {
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
    public void addNewTask(@RequestBody TestTaskItem testTaskItem) {
        this.taskRepository.save(testTaskItem);
        throw new ResponseStatusException(HttpStatus.OK);
    }
}
