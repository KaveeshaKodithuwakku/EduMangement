package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.view.tm.StudentTM;
import javafx.beans.property.Property;
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
    public TableView<StudentTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colDob;
    public TableColumn colOption;
    public Button btnSave;
    public TextField txtSearch;
    String searchText = "";

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("optionBtn"));

        setStudentId();
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

        tblStudent.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(null != newValue){
                        setData(newValue);
                    }
                });
    }

    private void setData(StudentTM tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtDob.setValue(LocalDate.parse(tm.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txtAddress.setText(tm.getAddress());
        btnSave.setText("Update Student");
    }

    private void setTableData(String searchText) {
        ObservableList<StudentTM> obList = FXCollections.observableArrayList();
        for(Student student: Database.studentTable){

            if(student.getFullName().contains(searchText)){
                Button btn = new Button("Delete");
                StudentTM studentTM = new StudentTM(
                        student.getId(),
                        student.getFullName(),
                        new SimpleDateFormat("yyyy-MM-dd").format(student.getDob()),
                        student.getAddress(),
                        btn);

                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,"Are you sure?",
                            ButtonType.YES,ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if(buttonType.get().equals(ButtonType.YES)){
                        Database.studentTable.remove(student);
                        new Alert(Alert.AlertType.INFORMATION,"Success!").show();
                        setTableData(searchText);
                    }
                });
                obList.add(studentTM);
            }
        }

        tblStudent.setItems(obList);

    }

    private void setStudentId() {
        if(!Database.studentTable.isEmpty()){
            Student lastStudent = Database.studentTable.get(Database.studentTable.size() - 1);
            String lastId = lastStudent.getId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "S-"+lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);
        }else {
            txtId.setText("S-1");
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {

        if(btnSave.getText().equalsIgnoreCase("Save Student")){
            Student student= new Student(
                    txtId.getText(),
                    txtName.getText(),
                    Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText());

            Database.studentTable.add(student);
            setStudentId();
            clear();
            setTableData(searchText);

            new Alert(Alert.AlertType.INFORMATION,"Student saved!").show();
        }else{
            Optional<Student> selectedStudent = Database.studentTable.stream().filter(e -> e.getId().equals(txtId.getText())).findFirst();

            if(!selectedStudent.isPresent()){
                new Alert(Alert.AlertType.WARNING,"Not Found").show();
                return;
            }

            selectedStudent.get().setAddress(txtAddress.getText());
            selectedStudent.get().setDob(Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            selectedStudent.get().setFullName(txtName.getText());

            setStudentId();
            clear();
            setTableData(searchText);

//            ///--------------- using for each ----------------------
//            for(Student st : Database.studentTable){
//                if(st.getId().equals(txtId.getText().trim())){
//                    st.setFullName(txtName.getText());
//                    st.setDob(Date.from(txtDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//                    st.setAddress(txtAddress.getText());
//
//                    setStudentId();
//                    clear();
//                    setTableData(searchText);
//                }
//            }
//
//            new Alert(Alert.AlertType.WARNING,"Not Found").show();
        }
    }

    private void clear(){
        txtName.clear();
        txtAddress.clear();
        txtDob.setValue(null);

    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clear();
        setStudentId();
        btnSave.setText("Save Student");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
