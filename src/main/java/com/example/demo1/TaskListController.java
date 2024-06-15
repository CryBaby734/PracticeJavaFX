package com.example.demo1;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class TaskListController {

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private Button add_form;

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

        add_form.setOnAction(event -> OpenTaskForm());
    }

    protected void loadTasks() {
        ObservableList<Task> tasks = databaseHandler.getTasks(userId);
        taskTable.setItems(tasks);
    }

    private void OpenTaskForm() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/taskFormView.fxml"));
            Parent root = fxmlLoader.load();

            TaskController taskController = fxmlLoader.getController();
            taskController.setTaskListController(this);
            taskController.setUserId(userId);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
        loadTasks();
    }
}
