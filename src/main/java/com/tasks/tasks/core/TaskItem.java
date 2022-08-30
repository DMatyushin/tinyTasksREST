package com.tasks.tasks.core;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tasks")
public class TaskItem {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="title")
    private String taskTitle;

    @Column(name="descr")
    private String taskDescription;

    @Column(name="date")
    private long createTime;

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle( String taskTitle ) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription( String taskDescription ) {
        this.taskDescription = taskDescription;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime( long createTime ) {

        this.createTime = createTime;
    }
}
