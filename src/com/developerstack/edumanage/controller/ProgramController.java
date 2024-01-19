package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Program;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.model.Teacher;
import com.developerstack.edumanage.view.tm.ProgramTM;
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

public class ProgramController {
    public AnchorPane context;
    public TextField txtProgramCode;
    public TextField txtName;
    public TextField txtCost;
    public TextField txtSearch;
    public Button btnSave;
    public ComboBox<String> cmbTeacher;
    public TableView<TechAddTM> tblTechnologies;
    public TableColumn colRemove;
    public TextField txtTechnology;
    public TableColumn colCode;
    public TableColumn colProgram;
    public TableColumn colCost;
    public TableColumn colTName;
    public TableColumn colTcode;
    public TableColumn colTeacher;
    public TableColumn colTechnology;
    public TableColumn colOption;
    public TableView<ProgramTM> tblProgram;
    String selectedTeacher = "";
    ArrayList<String> teacherList = new ArrayList<>();

    ObservableList<TechAddTM> tmList = FXCollections.observableArrayList();

    String searchText = "";

    public void initialize(){

        colTcode.setCellValueFactory(new PropertyValueFactory<>("codeT"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        colCode.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colTechnology.setCellValueFactory(new PropertyValueFactory<>("btnTech"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btnTeacher"));


        setProgramId();
        setTeacherDetails();

        cmbTeacher.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedTeacher = newValue.toString();
                });

    }

    private void setProgramId() {
        if(!Database.programTable.isEmpty()){
            Program program = Database.programTable.get(Database.programTable.size()-1);
            String lastId = program.getProgramCode();
            String split[] = lastId.split("-");
            String lastIdIntegerAsString = split[1];
            int lastIdStringAsInteger = Integer.parseInt(lastIdIntegerAsString);
            lastIdStringAsInteger++;
            String generatedId = "P-" + lastIdStringAsInteger;
            txtProgramCode.setText(generatedId);

        }else {
            txtProgramCode.setText("P-1");
        }
    }



    public void saveOnAction(ActionEvent actionEvent) {
        ObservableList<Program> obList = FXCollections.observableArrayList();
        String[] selectedTechnology = new String[tmList.size()];
        int pointer = 0;

        for(TechAddTM tm : tmList){
            selectedTechnology[pointer] = tm.getName();
            pointer++;
        }


        if(btnSave.getText().equals("Save Program")){
            Program program = new Program(
                    txtProgramCode.getText(),
                    txtName.getText(),
                    selectedTechnology,
                    Double.parseDouble(txtCost.getText()),
                    selectedTeacher.split(":")[0]
            );

                    Database.programTable.add(program);
            setProgramId();
            clear();
            setTableData(searchText);

            new Alert(Alert.AlertType.INFORMATION,"Program saved!").show();


        }else{
            Optional<Program> selectedProgram = Database.programTable.stream().filter(e -> e.getProgramCode().equals(txtProgramCode.getText())).findFirst();

            if(!selectedProgram.isPresent()){
                new Alert(Alert.AlertType.WARNING,"Not Found").show();
                return;
            }

            selectedProgram.get().setName(txtName.getText());
            selectedProgram.get().setCost(Double.parseDouble(txtCost.getText()));
            selectedProgram.get().setTeacher("");
            //selectedProgram.get().setTechnologies();

            setProgramId();
            clear();
            setTableData(searchText);
        }


    }

    private void setTableData(String searchText) {
        ObservableList<ProgramTM> obList = FXCollections.observableArrayList();
        for(Program program: Database.programTable){

            if(program.getName().contains(searchText)){
                Button btnTech = new Button("Show Tech");
                Button btnRemove = new Button("Delete");
                ProgramTM programTM = new ProgramTM(
                        program.getProgramCode(),
                        program.getName(),
                       program.getCost(),
                       program.getTeacher(),
                        btnTech,
                        btnRemove);

                btnRemove.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,"Are you sure?",
                            ButtonType.YES,ButtonType.NO);

                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if(buttonType.get().equals(ButtonType.YES)){
                        Database.programTable.remove(program);
                        new Alert(Alert.AlertType.INFORMATION,"Success!").show();
                        setTableData(searchText);
                    }
                });
                obList.add(programTM);
            }
        }

        tblProgram.setItems(obList);

    }

    private void setTeacherDetails(){
        for(Teacher teacher: Database.teacherTable){
            teacherList.add(teacher.getCode() + " : " +teacher.getName());
        }

        ObservableList<String> obTeacherList = FXCollections.observableArrayList(teacherList);
        cmbTeacher.setItems(obTeacherList);
    }

    private void clear(){
        txtName.clear();
        txtCost.clear();
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
        clear();
        setProgramId();
        btnSave.setText("Save Program");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void addTechnologyOnAction(ActionEvent actionEvent) {

        if(!isExists(txtTechnology.getText().trim())){

            Button btn = new Button("Remove");
            System.out.println(tmList.size() +1);
            TechAddTM tm = new TechAddTM(
                    tmList.size() +1,
                    txtTechnology.getText().trim(),
                    btn
            );

            tmList.add(tm);
            tblTechnologies.setItems(tmList);
            txtTechnology.clear();
        }else {
            txtTechnology.selectAll();
            new Alert(Alert.AlertType.WARNING,"Already Exists").show();
        }

    }

    private boolean isExists(String tech){
        for(TechAddTM tm: tmList){
            if(tm.getName().equals(tech)){
                return true;
            }
        }
        return false;
    }



}
