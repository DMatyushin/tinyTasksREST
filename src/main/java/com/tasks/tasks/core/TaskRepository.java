package com.tasks.tasks.core;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskItem, Integer> {

}
