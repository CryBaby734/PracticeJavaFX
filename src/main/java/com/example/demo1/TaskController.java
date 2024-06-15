package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TaskController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private TextField priorityField;

    @FXML
    private Button saveButton;

    private TaskListController taskListController;
    private int userId;

    @FXML
    void initialize() {
        saveButton.setOnAction(event -> saveTask());
    }

    public void setTaskListController(TaskListController taskListController) {
        this.taskListController = taskListController;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private void saveTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        java.time.LocalDate dueDate = dueDatePicker.getValue();
        String priority = priorityField.getText();

        if(title.isEmpty() || description.isEmpty() ||dueDate == null || priority.isEmpty()) {
            System.out.println("All fields are empty");
            return;
        }

        Task task = new Task(userId, title, description, java.sql.Date.valueOf(dueDate), priority, "Pending");
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.addTask(task);

        taskListController.loadTasks();

        Stage stage = (Stage) saveButton.getScene().getWindow();

//        titleField.clear();
//        descriptionArea.clear();
//        dueDatePicker.setValue(null);
//        priorityField.clear();
    }
}
