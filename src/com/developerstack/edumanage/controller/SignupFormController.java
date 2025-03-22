package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.db.DbConnection;
import com.developerstack.edumanage.model.User;
import com.developerstack.edumanage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;

public class SignupFormController {
    public AnchorPane context;
    public TextField txtFirstName;
    public TextField txtEmail;
    public TextField txtLastName;
    public TextField txtPassword;

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText().toLowerCase();
        String password = new PasswordManager().encryptPassword(txtPassword.getText().trim());


        User createdUser = new User(firstName, lastName, email, password);
        try {
            boolean isSaved = signup(createdUser);
            //Also Can Pass Anonymous Object Directly to The Function
            //boolean isSaved1 = signup(new User(firstName, lastName, email, password));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Welcome !!!!").show();
                setUi("LoginForm");
            } else {
                new Alert(Alert.AlertType.WARNING, "Please Try Again !!!!").show();
            }
        } catch (SQLException | ClassNotFoundException e1) {
            new Alert(Alert.AlertType.ERROR, e1.toString()).show();
        }


    }

    public void alreadyHaveAndAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
        ;
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }


    //====Manage DB==========
    private boolean signup(User user) throws ClassNotFoundException, SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        // Write a SQL Query
        /*String sql = "INSERT INTO user VALUES ('" + user.getEmail() + "','" + user.getFirstName() + "','" + user.getLastName() + "','" + user.getPassword() + "')";*/
        String sql= "INSERT INTO user VALUES (?,?,?,?)";

       /* //Create Statement
        Statement statement = connection.createStatement();*/

        //Create Prepared Statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,user.getEmail());
        statement.setString(2,user.getFirstName());
        statement.setString(3,user.getLastName());
        statement.setString(4,user.getPassword());

        //Set Sql into the statement & Execute
        return statement.executeUpdate() > 0; // INSERT , UPDATE, DELETE Query need to use executeUpdate()

       /* if (rowCount > 0) {
            return true;
        }
        return false;*/

    }
}
