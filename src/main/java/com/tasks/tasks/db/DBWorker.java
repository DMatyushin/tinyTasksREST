package com.tasks.tasks.db;

import com.tasks.tasks.TaskItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBWorker {
    String dbURL;
    String dbPort;
    private final String dbUsername;
    private final String dbPassword;
    Connection conn;


    DBWorker(String dbURL, String dbPort, String dbUsername, String dbPassword) {
        this.dbURL = "jdbc:mysql://" + dbURL;
        this.dbPort = dbPort;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;

    }

    void connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<ArrayList<String>> getDataFromDB() {
        ArrayList<ArrayList<String>> tasksList = new ArrayList<>();


        try {
            connectToDB();

            try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");

                while(resultSet.next()) {

                    ArrayList<String> taskBody = new ArrayList<>();

                    taskBody.add(resultSet.getString(1));
                    taskBody.add(resultSet.getString(2));
                    taskBody.add(resultSet.getString(3));
                    taskBody.add(resultSet.getString(4));

                    tasksList.add(taskBody);
                }

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return tasksList;
    }

    public void addTaskItem(TaskItem taskItem) {

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)){
            Statement statement = conn.createStatement();

            statement.executeUpdate("insert tasks(title, descr, date) " +
                    "values ('" + taskItem.taskTitle + "', '" + taskItem.taskDescription + "', " + taskItem.createTime + ");");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeTaskItem(int taskId) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            Statement statement = conn.createStatement();
            if (checkDBEntry(taskId)) {
                statement.executeUpdate("DELETE FROM tasks WHERE id =" + taskId);
                System.out.printf("Task ID %s was delete.", taskId);
            }
            else {
                System.out.printf("Task with ID %s didn't exist.\n", taskId);
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void modifyTaskItem(int taskId, String modField, String fieldUpdate) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            String sqlQuerry;
            Statement statement = conn.createStatement();

            if (modField.equals("T")) {
                sqlQuerry = String.format("UPDATE tasks SET title = '%s' WHERE ID = %d", fieldUpdate, taskId);
                statement.executeUpdate(sqlQuerry);


                System.out.printf("Task ID < %s > title updated. New title < %s >.\n", taskId, fieldUpdate);
            }
            else if (modField.equals("D")) {
                sqlQuerry = String.format("update tasks set descr = '%s' where id = %d", fieldUpdate, taskId);
                statement.executeUpdate(sqlQuerry);
                System.out.printf("Task id < %s > description updated. New description < %s >", taskId, fieldUpdate);
            }
            else {
                System.out.println("Incorrect field type.");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    boolean checkDBEntry (int checkId) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT id FROM tasks WHERE id = %d", checkId));

            if (resultSet.next()) {
                return true;
            }


        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

}