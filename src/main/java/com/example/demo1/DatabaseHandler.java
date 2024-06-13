package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
public class DatabaseHandler{

       public boolean authenticateUser(String username, String password){
           String query = "SELECT COUNT(1) FROM users WHERE username = ? AND password = ?";

           try(Connection connection = Configs.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)){
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);

             try(ResultSet resultSet = preparedStatement.executeQuery()) {
                 if (resultSet.next()) {
                     return resultSet.getInt(1) == 1;
                 }
             }
              } catch (SQLException e){
             e.printStackTrace();
             }
             return false;
       }

       public void addTask(Task task) {
           String query = "INSERT INTO tasks (user_id, title, description, due_date, priority, status) VALUES (?, ?, ?, ?, ?, ?)";
           try(Connection connection = Configs.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)){
               preparedStatement.setInt(1,task.getUserId());
               preparedStatement.setString(2,task.getTitle());
               preparedStatement.setString(3,task.getDescription());
               preparedStatement.setDate(4,task.getDueDate());
               preparedStatement.setString(5,task.getPriority());
               preparedStatement.setString(6,task.getStatus());
               preparedStatement.executeUpdate();
           }catch (SQLException e){
               e.printStackTrace();
           }
       }
       public void updateTask(Task task) {
           String query = "UPDATE tasks SET title = ?, description = ?, due_date = ?, priority = ?, status = ? WHERE task_id = ?";
          try(Connection connection = Configs.getConnection();
          PreparedStatement preparedStatement = connection.prepareStatement(query)){
              preparedStatement.setString(1,task.getTitle());
              preparedStatement.setString(2,task.getDescription());
              preparedStatement.setDate(3,task.getDueDate());
              preparedStatement.setString(4,task.getPriority());
              preparedStatement.setString(5,task.getStatus());
              preparedStatement.setInt(6,task.getId());
              preparedStatement.executeUpdate();
           }catch (SQLException e){
              e.printStackTrace();
          }
       }
       public void deleteTask(int taskId) {
           String query = "DELETE FROM tasks WHERE task_id = ?";
           try(Connection connection = Configs.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setInt(1,taskId);
               preparedStatement.executeUpdate();
           }catch (SQLException e){
               e.printStackTrace();
           }
       }

       public ObservableList<Task> getTasks(int userId) {
           ObservableList<Task> taskList = FXCollections.observableArrayList();
           String query = "SELECT * FROM tasks WHERE user_id = ?";

           try(Connection connection = Configs.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setInt(1,userId);
               ResultSet resultSet = preparedStatement.executeQuery();
               while (resultSet.next()) {
                   int id = resultSet.getInt("task_id");
                   String title = resultSet.getString("title");
                   String description = resultSet.getString("description");
                   java.sql.Date dueDate = resultSet.getDate("due_date");
                   String priority = resultSet.getString("priority");
                   String status = resultSet.getString("status");

                   Task task = new Task(id, userId, title, description, dueDate, priority, status);
                   taskList.add(task);
               }
           }catch (SQLException e){
               e.printStackTrace();
           }
           return taskList;
       }

    public void signUpUser(User user) {
       if(user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getPassword().isEmpty()) {
           return;
       }

        try (Connection con = Configs.getConnection()) {
            String sql = "INSERT INTO users (username, lastname, password, contact_info) VALUES(?, ?, ? ,?)";
            try (PreparedStatement prSt = con.prepareStatement(sql)) {
                prSt.setString(1, user.getFirstname());
                prSt.setString(2, user.getLastname());
                prSt.setString(3, user.getPassword());
                prSt.setString(4, user.getContactInfo());
                prSt.executeUpdate();
                System.out.println("User added successfully");
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    public ResultSet getUser(User user){
        ResultSet resSet = null;

        try(Connection con = Configs.getConnection()){
            String select="SELECT * FROM users WHERE "+Const.USER_NAME + "=? AND "+Const.USER_PASSWORD+"=?";
            try(PreparedStatement preparedStatement = con.prepareStatement(select)) {
                preparedStatement.setString(1, user.getFirstname());
                preparedStatement.setString(2, user.getPassword());

                resSet = preparedStatement.executeQuery();
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return resSet;
    }
    public int getUserIdByUsername(String username) {
        String query = "SELECT id_user FROM users WHERE username = ?";
        try (Connection connection = Configs.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


}
