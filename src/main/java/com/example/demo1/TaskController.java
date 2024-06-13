package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

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

    @FXML
    void initialize() {
        saveButton.setOnAction(event -> saveTask());
    }

    private void saveTask() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        LocalDate dueDate = dueDatePicker.getValue();
        String priority = priorityField.getText();

        int userId = 1;

        Task task = new Task(userId,title, description, Date.valueOf(dueDate), priority, "Pending");
        DatabaseHandler dbhandler = new DatabaseHandler();
        dbhandler.addTask(task);

        titleField.clear();
        descriptionArea.clear();
        dueDatePicker.setValue(null);
        priorityField.clear();
    }
}
