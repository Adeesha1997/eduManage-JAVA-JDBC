package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.view.tm.StudentTm;
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

public class StudentFormController {

    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public DatePicker txtDob;
    public TextField txtAddress;
    public TableView<StudentTm> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colOption;
    public Button btn;
    public TextField txtSearch;

    String searchText = "";

    public void saveOnAction(ActionEvent actionEvent) {

        if (btn.getText().equalsIgnoreCase("Save Student")) {
            Student student = new Student(
                    txtId.getText(),
                    txtName.getText(),
                    Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText()
            );

            Database.studentTable.add(student);
            setStudentId();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION, "Student Saved!!").show();
        } else {


           /* Optional<Student> selectedStudent = Database.studentTable.stream().filter(e -> e.getStudentId().equals(txtId.getText())).findFirst();
            if (!selectedStudent.isPresent()){
                new Alert(Alert.AlertType.WARNING,"Student Not Found!!").show();
                return;
            }else{
             // Update Code
               selectedStudent.setAddress(txtAddress.getText());
                    selectedStudent.setFullName(txtName.getText());
                    selectedStudent.setDateOfBirth(Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    setTableData();
                    clear();
                    setStudentId();
                    new Alert(Alert.AlertType.INFORMATION, "Student Updated!!").show();
            }*/

            for (Student st : Database.studentTable
            ) {
                if (st.getStudentId().equals(txtId.getText())) {
                    // Update Code
                    st.setAddress(txtAddress.getText());
                    st.setFullName(txtName.getText());
                    st.setDateOfBirth(Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    setTableData(searchText);
                    clear();
                    setStudentId();
                    btn.setText("Save Student");
                    new Alert(Alert.AlertType.INFORMATION, "Student Updated!!").show();
                    return;
                }
            }

            new Alert(Alert.AlertType.WARNING, "Student Not Found!!").show();

        }


    }

    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        setStudentId();
        setTableData(searchText);

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

    private void setData(StudentTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtDob.setValue(LocalDate.parse(tm.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txtAddress.setText(tm.getAddress());
        btn.setText("Update Student");
    }

    private void setTableData(String searchText) {

        ObservableList<StudentTm> obList = FXCollections.observableArrayList();
        for (Student st : Database.studentTable
        ) {

            if (st.getFullName().contains(searchText)) {
                Button btn = new Button("Delete");
                StudentTm tm = new StudentTm(
                        st.getStudentId(),
                        st.getFullName(),
                        new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),
                        st.getAddress(),
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
                        Database.studentTable.remove(st);
                        new Alert(Alert.AlertType.INFORMATION, "Deleted!").show();
                        setTableData(searchText);
                        setStudentId();
                    }

                });
                obList.add(tm);
            }

        }

        tblStudent.setItems(obList);
    }

    private void setStudentId() {
        if (!Database.studentTable.isEmpty()) {
            Student lastStudent = Database.studentTable.get(
                    Database.studentTable.size() - 1
            );

            String lastId = lastStudent.getStudentId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "S-" + lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);

        } else {
            txtId.setText("S-1");
        }
    }

    private void clear() {
        txtDob.setValue(null);
        txtName.clear();
        txtAddress.clear();
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clear();
        setStudentId();
        btn.setText("Save Student");
    }


    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }
}

