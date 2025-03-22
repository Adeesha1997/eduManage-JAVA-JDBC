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
import java.util.Optional;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtPassword;


    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().toLowerCase();
        String password = txtPassword.getText().trim();

        try {
            User selectedUser = login(email);
            if (null != selectedUser) {
                if (new PasswordManager().checkPassword(password, selectedUser.getPassword())) {
                    setUi("DashboardForm");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Wrong Password !").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, String.format("User Not Found (%s)", email)).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }

    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void forgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ForgotPasswordForm");
    }

    private User login(String email) throws ClassNotFoundException, SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        // Write a SQL Query
        String sql = "SELECT * FROM user WHERE email=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,email);

        ResultSet resultSet = statement.executeQuery();// SELECT Query need to use executeQuery();

        if (resultSet.next()) {
            User user = new User(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"),
                    resultSet.getString("password")

                /*    // Also Can Get Data Using ColumnIndex
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)*/
            );
            System.out.println(user);
            return user;
        }

        return null;

    }
}
