package com.tasks.tasks.core;

public class ModifiedTask {
    public String newTitle;
    public String newDescription;
    public int taskId;

    public boolean checkObject() {

        if ( newTitle != null || newDescription != null || taskId != 0 ) {
            System.out.println(taskId);
            return true;
        } else {
            return false;
        }

    }
}
