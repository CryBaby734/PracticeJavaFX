package com.example.demo1;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SignUpController {

    @FXML
    private TextField FirstName;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField contact;

    @FXML
    private TextField lastname;

    @FXML
    private PasswordField pass_sign;

    @FXML
    private PasswordField rpass_sign;

    @FXML
    private CheckBox signFemale;

    @FXML
    private CheckBox signMale;

    @FXML
     void initialize() {
         DatabaseHandler dbHandler = new DatabaseHandler();
         SignUpButton.setOnAction(event -> {

            signUpNewUser();

         });
     }

    private void signUpNewUser() {
         DatabaseHandler dbHandler = new DatabaseHandler();

        String firstName = FirstName.getText();
        String lastName = lastname.getText();
        String pass = pass_sign.getText();
        String rpass = rpass_sign.getText();
        String contactNo = contact.getText();
        String gender= "";


        if(signFemale.isSelected())
            gender = "Female";
        else
            gender = "Male";

        User user = new User(firstName,lastName,pass,contactNo);

        dbHandler.signUpUser(user);
        System.out.println("Sign Up Success");
    }
}