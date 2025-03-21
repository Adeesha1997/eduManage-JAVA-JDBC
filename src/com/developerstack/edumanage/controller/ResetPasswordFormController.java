package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.User;
import com.developerstack.edumanage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ResetPasswordFormController {

    public AnchorPane context;
    public TextField txtPassword;
    String selectedEmail;

    public void setUserData(String email) {

        selectedEmail = email;
    }

    public void changePasswordOnAction(ActionEvent actionEvent) throws IOException {
        Optional<User> selectedUser = Database.userTable.stream().filter(e -> e.getEmail().equals(selectedEmail)).findFirst();
        if (selectedUser.isPresent()) {
            selectedUser.get().setPassword(new PasswordManager().encryptPassword(txtPassword.getText()));
            setUi("LoginForm");
        } else {
            new Alert(Alert.AlertType.ERROR, "Email Not Found!").show();
        }

    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

}
