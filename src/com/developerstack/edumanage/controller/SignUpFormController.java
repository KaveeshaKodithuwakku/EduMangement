package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.User;
import com.developerstack.edumanage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class SignUpFormController {
    public PasswordField txtPassword    ;
    public TextField txtFirstName;
    public TextField txtEmail;
    public TextField txtLastName;
    public AnchorPane context;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtPassword.getText();
        String email = txtEmail.getText().toLowerCase();
        String password = new PasswordManager().encrypt(txtPassword.getText());

        Database.userTable.add(new User(firstName,lastName,email,password));

        new Alert(Alert.AlertType.INFORMATION,"Welcome!").show();

        setUi("LoginForm");
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
