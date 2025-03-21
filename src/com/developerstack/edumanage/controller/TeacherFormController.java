package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.model.Teacher;
import com.developerstack.edumanage.view.tm.StudentTm;
import com.developerstack.edumanage.view.tm.TeacherTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class TeacherFormController {
    public AnchorPane teacherContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSearch;
    public TextField txtAddress;
    public TableView<TeacherTm> tblTeacher;
    public Button btn;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colOption;

    String searchText = "";

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
        clear();
        setTeacherId();
        btn.setText("Save Teacher");
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (btn.getText().equalsIgnoreCase("Save Teacher")) {
            Teacher teacher = new Teacher(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText()
            );

            Database.teacherTable.add(teacher);
            setTeacherId();
            clear();
          setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION, "Teacher Saved!!").show();
        } else {
            for (Teacher tt : Database.teacherTable
            ) {
                if (tt.getCode().equals(txtId.getText())) {
                    // Update Code
                    tt.setAddress(txtAddress.getText());
                    tt.setName(txtName.getText());
                    tt.setContact(txtContact.getText());
                 setTableData(searchText);
                    clear();
                    setTeacherId();
                    btn.setText("Save Teacher");
                    new Alert(Alert.AlertType.INFORMATION, "Teacher Updated!!").show();
                    return;
                }
            }
            new Alert(Alert.AlertType.WARNING, "Teacher Not Found!!").show();
        }
    }

    private void setTeacherId() {
        if (!Database.teacherTable.isEmpty()) {
            Teacher lastTeacher = Database.teacherTable.get(
                    Database.teacherTable.size() - 1
            );

            String lastId = lastTeacher.getCode();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedTeacherId = "T-" + lastIntegerIdAsInt;
            txtId.setText(generatedTeacherId);

        } else {
            txtId.setText("T-1");
        }
    }


    private void clear() {
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) teacherContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }


    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setTeacherId();
        setTableData(searchText);

        tblTeacher.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (null != newValue) {
                        setData(newValue);
                    }

                }

        );

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

    }

    private void setData(TeacherTm tm) {
        txtId.setText(tm.getCode());
        txtName.setText(tm.getName());
        txtContact.setText(tm.getContact());
        txtAddress.setText(tm.getAddress());
        btn.setText("Update Teacher");
    }

    private void setTableData(String searchText) {

        ObservableList<TeacherTm> obList = FXCollections.observableArrayList();
        for (Teacher tt : Database.teacherTable
        ) {

            if (tt.getName().contains(searchText)) {
                Button btn = new Button("Delete");
                TeacherTm tm = new TeacherTm(
                        tt.getCode(),
                        tt.getName(),
                        tt.getContact(),
                        tt.getAddress(),
                        btn
                );

                //Delete Code
                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you Sure?",
                            ButtonType.YES, ButtonType.NO
                    );

                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.teacherTable.remove(tt);
                        new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                        setTableData(searchText);
                        setTeacherId();
                    }

                });
                obList.add(tm);
            }

        }

        tblTeacher.setItems(obList);
    }
}
