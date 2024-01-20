package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Program;
import com.developerstack.edumanage.model.Registration;
import com.developerstack.edumanage.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class RegistrationFormController {
    public AnchorPane context;
    public TextField txtId;
    public Button btnRegistration;
    public ComboBox<String> cmbProgram;
    public TextField txtStudent;
    public ToggleGroup PaidStatus;
    public RadioButton rBtnPending;
    public RadioButton rBtnPaid;
    ArrayList<String> proList = new ArrayList<>();
    ArrayList<String> studentList = new ArrayList<>();

    String selectedProgram = "";

    public void initialize(){
        getProgramDetails();
        getStudentDetails();
        setRegistrationId();

        cmbProgram.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                     selectedProgram = newValue;
                });
    }

    private void setRegistrationId() {
        if(!Database.registrationTable.isEmpty()){
            Registration lastRegistration = Database.registrationTable.get(Database.registrationTable.size() - 1);
            String lastId = lastRegistration.getRegId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsString);
            lastIntegerIdAsInt++;
            String generatedRegId = "Reg-"+lastIntegerIdAsInt;
            txtId.setText(generatedRegId);
        }else {
            txtId.setText("Reg-1");
        }
    }

    public void getStudentDetails(){
        for(Student student: Database.studentTable){
            studentList.add(student.getId() + ":" + student.getFullName());
        }

        TextFields.bindAutoCompletion(txtStudent,studentList);
    }

    public  void getProgramDetails(){
        for(Program program: Database.programTable){
            proList.add(program.getProgramCode());
        }

        ObservableList<String> obProgramList = FXCollections.observableArrayList(proList);
        cmbProgram.setItems(obProgramList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    public void registrationOnAction(ActionEvent actionEvent) {

        boolean selectedPaymentStatus = false;
        LocalDate currentDate = LocalDate.now();

        if(rBtnPaid.isSelected()){
            selectedPaymentStatus = true;
        }

        String splitData[] = txtStudent.getText().split(":");
        String studentId = splitData[1];

        Registration registration = new Registration(
                txtId.getText(),
                Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                studentId,
                selectedProgram,
                selectedPaymentStatus);

        Database.registrationTable.add(registration);
        setRegistrationId();
        clear();

        System.out.println(Database.registrationTable.get(0));

        new Alert(Alert.AlertType.INFORMATION,"Registration  success!").show();
    }

    private void clear() {
        txtStudent.clear();
        if(rBtnPaid.isSelected()){
            rBtnPaid.setSelected(false);
        }else {
            rBtnPending.setSelected(false);
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
