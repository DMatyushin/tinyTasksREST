package com.tasks.tasks.core;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class TestTaskItem {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="title")
    private String taskTitle;

    @Column(name="descr")
    private String taskDescription;

    @Column(name="date")
    private int createTime;

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

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime( int createTime ) {
        this.createTime = createTime;
    }
}
