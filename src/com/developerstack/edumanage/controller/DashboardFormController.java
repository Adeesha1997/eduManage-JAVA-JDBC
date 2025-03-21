package com.developerstack.edumanage.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane context;

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void initialize() {
        setData();
    }

    private void setData() {
     /*   Date date = new Date(); // Get From util Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String txtDate = dateFormat.format(date);
       lblDate.setText(txtDate);*/

        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        /* lblTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));*/

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        e -> {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                            lblTime.setText(LocalTime.now().format(dateFormatter));
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void openStudentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("StudentForm");
    }

    public void openProgramFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProgramForm");
    }

    public void openIntakeFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("IntakeForm");
    }

    public void openTeacherFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("TeacherForm");
    }

    public void openRegistrationFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("RegistrationForm");
    }
}
