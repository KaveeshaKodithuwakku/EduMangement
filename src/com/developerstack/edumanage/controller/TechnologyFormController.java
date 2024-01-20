package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.view.tm.StudentTM;
import com.developerstack.edumanage.view.tm.TechAddTM;
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
import java.util.ArrayList;
import java.util.Optional;

public class TechnologyFormController {
    public AnchorPane context;
    public TableView<TechAddTM> tblTechnology;
    public TableColumn colId;
    public TableColumn colTechnology;
    ObservableList<TechAddTM> techList = FXCollections.observableArrayList();

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("codeT"));
        colTechnology.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
    public void setData(ObservableList<TechAddTM> list){
        techList = list;
        setTableData(techList);
    }

    private void setTableData(ObservableList<TechAddTM> list) {
        tblTechnology.setItems(list);
    }

    public void backToProgramOnAction(ActionEvent actionEvent) throws IOException {
        setUi("ProgramForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
