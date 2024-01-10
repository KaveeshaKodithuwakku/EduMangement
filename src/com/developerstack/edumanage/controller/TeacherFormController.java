package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.model.Teacher;
import com.developerstack.edumanage.view.tm.TeacherTM;
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
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class TeacherFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSearch;
    public Button btnSave;
    public TableView<TeacherTM> tblTeacher;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact  ;
    public TableColumn colOption1;
    public TextField txtContact;
    String searchText = "";

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colOption1.setCellValueFactory(new PropertyValueFactory<>("optionBtn"));

        setTeacherId();
        setTableData(searchText);


        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

        tblTeacher.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(null != newValue){
                        setData(newValue);
                    }
                });
    }

    private void setData(TeacherTM tm) {
        txtId.setText(tm.getTeacherId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContact.setText(tm.getContact());
        btnSave.setText("Update Teacher");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    public void newTeacherOnAction(ActionEvent actionEvent) {
        setTeacherId();
        clear();
        btnSave.setText("Save Teacher");
    }

    public void saveOnAction(ActionEvent actionEvent) {

        if(btnSave.getText().equalsIgnoreCase("Save Teacher")){
            Teacher teacher = new Teacher(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText());

            Database.teacherTable.add(teacher);
            setTeacherId();
            clear();
            setTableData(searchText);

            new Alert(Alert.AlertType.INFORMATION,"Teacher saved!").show();
        }else{
            Optional<Teacher> selectedTeacher = Database.teacherTable.stream().filter(e -> e.getCode().equals(txtId.getText())).findFirst();

            if(!selectedTeacher.isPresent()){
                new Alert(Alert.AlertType.WARNING,"Not Found").show();
                return;
            }

            selectedTeacher.get().setAddress(txtAddress.getText());
            selectedTeacher.get().setName(txtName.getText());
            selectedTeacher.get().setContact(txtContact.getText());

            setTeacherId();
            clear();
            setTableData(searchText);
        }

    }

    private void setTableData(String searchText) {

        ObservableList<TeacherTM> obList = FXCollections.observableArrayList();

        for(Teacher th: Database.teacherTable){

            if(th.getName().contains(searchText)) {
                Button btn = new Button("Delete");
                TeacherTM teacherTM = new TeacherTM(
                        th.getCode(),
                        th.getName(),
                        th.getAddress(),
                        th.getContact(),
                        btn
                );

                btn.setOnAction(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.teacherTable.remove(th);
                        new Alert(Alert.AlertType.INFORMATION, "Success!").show();
                        setTableData(searchText);
                    }
                });
                obList.add(teacherTM);
            }
        }
        tblTeacher.setItems(obList);
    }

    private void clear() {
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    private void setTeacherId() {

        if(Database.teacherTable.isEmpty()){
            txtId.setText("T-1");
        }else{
           Teacher teacher = Database.teacherTable.get(Database.teacherTable.size() -1);
           String lastId = teacher.getCode();
           String split[] = lastId.split("-");
           String lastIntegerNumberAsString = split[1];
           int lastIntegerIdAsInt = Integer.parseInt(lastIntegerNumberAsString);
           lastIntegerIdAsInt++;
           String generatedTeacherId = "T-"+ lastIntegerIdAsInt;
           txtId.setText(generatedTeacherId);
        }
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
