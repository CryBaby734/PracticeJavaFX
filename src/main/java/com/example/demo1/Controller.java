package com.example.demo1;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignInButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField pass_field;

    @FXML
    void initialize() {
        SignInButton.setOnAction(event -> {
            String login = login_field.getText().trim();
            String loginPass = pass_field.getText().trim();
            if(!login.isEmpty() && !loginPass.isEmpty()) {
                loginUser(login, loginPass);
           } else
                System.out.println("Login or Password is empty!");
        });

        SignUpButton.setOnAction(event -> {
            Stage currentStage = (Stage) SignUpButton.getScene().getWindow();
            currentStage.hide();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo1/SignUp.fxml"));

            try {
                Parent root = fxmlLoader.load();
                SignUpController signUpController = fxmlLoader.getController(); // Получаем контроллер
                //signUpController.initData(/);
                Stage signUpStage = new Stage();
                signUpStage.setScene(new Scene(root));
                signUpStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void loginUser(String login, String loginPass) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        boolean isAuthenticated = databaseHandler.authenticateUser(login, loginPass);

        if (isAuthenticated) {
            System.out.println("Successfully logged in!");
            try {
                // Загружаем FXML для списка задач
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo1/taskListView.fxml"));
                Parent root = loader.load();

                // Получаем контроллер списка задач и устанавливаем userId
                TaskListController taskListController = loader.getController();
                int userId = databaseHandler.getUserIdByUsername(login);
                taskListController.setUserId(userId);

                // Переходим на сцену со списком задач
                Stage stage = (Stage) SignInButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Login failed!");
        }
    }
}
