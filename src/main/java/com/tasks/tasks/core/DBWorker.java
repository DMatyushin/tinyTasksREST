package com.tasks.tasks.core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBWorker {
    String dbURL;
    String dbPort;
    private final String dbUsername;
    private final String dbPassword;
    Connection conn;


    public DBWorker(String dbURL, String dbPort, String dbUsername, String dbPassword) {
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


    public JSONArray getDataFromDB() {
        JSONArray jsonArray = new JSONArray();

        try {
            connectToDB();

            try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM tasks");

                while(resultSet.next()) {

                    JSONObject jsonRow = new JSONObject();

                    jsonRow.put("id", (resultSet.getString(1)));
                    jsonRow.put("title", (resultSet.getString(2)));
                    jsonRow.put("description", (resultSet.getString(3)));
                    jsonRow.put("time", (resultSet.getString(4)));

                    jsonArray.put(jsonRow);

                }

            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public void addTaskItem(TaskItem taskItem) {
        try {
            connectToDB();
            try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)){
                Statement statement = conn.createStatement();

                statement.executeUpdate("insert tasks(title, descr, date) " +
                        "values ('" + taskItem.taskTitle + "', '" + taskItem.taskDescription + "', " + taskItem.createTime + ");");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
                System.out.printf("Task id <%s> was delete.\n", taskId);
            }
            else {
                System.out.println("Task not found");
            }

        }
        catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public void modifyTaskItem(ModifiedTask modifiedTask) {
        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            String sqlQuerry;
            Statement statement = conn.createStatement();
            sqlQuerry = String.format("UPDATE tasks " +
                    "SET title = '%s', " +
                    "descr = '%s' " +
                    "WHERE ID = %d", modifiedTask.newTitle, modifiedTask.newDescription, modifiedTask.taskId);

            statement.executeUpdate(sqlQuerry);
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