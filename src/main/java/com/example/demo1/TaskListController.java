package com.example.demo1;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class TaskListController {

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, String> descriptionColumn;

    @FXML
    private TableColumn<Task, Date> dueDateColumn;

    @FXML
    private TableColumn<Task, String> priorityColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;

    @FXML
    private Button addTaskButton;

    @FXML
    private Button editTaskButton;

    @FXML
    private Button deleteTaskButton;

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private int userId = 1;

    @FXML
    void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadTasks();

        addTaskButton.setOnAction(event -> {
            Task newTask = new Task(userId, "New Task", "Description", Date.valueOf("2023-12-31"), "High", "Pending");
            databaseHandler.addTask(newTask);
            loadTasks();
        });

        editTaskButton.setOnAction(event -> {
            Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                selectedTask.setTitle("Updated task!");
                databaseHandler.updateTask(selectedTask);
                loadTasks();
            }
        });

        deleteTaskButton.setOnAction(event -> {
            Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                databaseHandler.deleteTask(selectedTask.getId());
                loadTasks();
            }
        });
    }

    private void loadTasks() {
        ObservableList<Task> tasks = databaseHandler.getTasks(userId);
        taskTable.setItems(tasks);
    }

    public void setUserId(int userId) {
        this.userId = userId;
        loadTasks();
    }
}
