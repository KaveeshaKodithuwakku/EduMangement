package com.developerstack.edumanage.controller;

import com.developerstack.edumanage.db.Database;
import com.developerstack.edumanage.model.Intake;
import com.developerstack.edumanage.model.Program;
import com.developerstack.edumanage.model.Student;
import com.developerstack.edumanage.view.tm.IntakeTM;
import com.developerstack.edumanage.view.tm.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.dnd.DropTarget;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class IntakeFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtSearch;
    public Button btnSave;
    public TableView<IntakeTM> tblIntake;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colStartDate;
    public TableColumn colProgram;
    public TableColumn colStatus;
    public TableColumn colOption;
    public TextField txtIntakeName;
    public DatePicker txtDate;
    public ComboBox<String> cmbProgram;

    ArrayList<String> proList = new ArrayList<>();
    String selectedProgram = "";
    String searchText = "";

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("intakeId"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colName.setCellValueFactory(new PropertyValueFactory<>("intakeName"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("intakeCompleteness"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

        setIntakeId();
        getProgramDetails();
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

        cmbProgram.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedProgram = newValue;
                });


        tblIntake.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        setData(newValue);
                    }
                });

    }

    private void setData(IntakeTM newValue) {
        txtId.setText(newValue.getIntakeId());
        txtIntakeName.setText(newValue.getIntakeName());
        txtDate.setValue(LocalDate.parse(newValue.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        btnSave.setText("Update Intake");
        cmbProgram.setValue(newValue.getProgramId());
    }

    private void setIntakeId() {
        if(!Database.intakeTable.isEmpty()){
            Intake lastIntake = Database.intakeTable.get(Database.intakeTable.size() - 1);
            String lastId = lastIntake.getIntakeId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsString);
            lastIntegerIdAsInt++;
            String generatedIntakeId = "S-"+lastIntegerIdAsInt;
            txtId.setText(generatedIntakeId);
        }else {
            txtId.setText("INT-1");
        }
    }

    public  void getProgramDetails(){
        for(Program program:Database.programTable){
            proList.add(program.getProgramCode());
        }

        ObservableList<String> obProgramList = FXCollections.observableArrayList(proList);
        cmbProgram.setItems(obProgramList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    public void newIntakeOnAction(ActionEvent actionEvent) {
        clear();
        setIntakeId();
        getProgramDetails();
        btnSave.setText("Save Intake");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void saveOnAction(ActionEvent actionEvent) {
      if(btnSave.getText().equalsIgnoreCase("Save Intake")){
          Intake intake = new Intake(
                  txtId.getText(),
                  Date.from(txtDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                  txtIntakeName.getText(),
                  selectedProgram,
                  false);

          Database.intakeTable.add(intake);
          setIntakeId();
          clear();
          setTableData(searchText);
      }else{
          Optional<Intake> selectedIntake = Database.intakeTable.stream().filter(e -> e.getIntakeId().equals(txtId.getText().trim())).findFirst();
          if(!selectedIntake.isPresent()){
              new Alert(Alert.AlertType.ERROR,"Not Found").show();
              return;
          }

          selectedIntake.get().setIntakeName(txtIntakeName.getText());
          selectedIntake.get().setStartDate(Date.from(txtDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
          selectedIntake.get().setProgramId(selectedProgram);
          selectedIntake.get().setIntakeCompleteness(false);

          setIntakeId();
          clear();
          setTableData(searchText);
      }
    }

    private void setTableData(String text) {
        ObservableList<IntakeTM> obList = FXCollections.observableArrayList();

        for(Intake intake: Database.intakeTable){
            if(intake.getIntakeName().contains(text)){
                Button btn = new Button("Delete");
                IntakeTM intakeTM = new IntakeTM(
                        intake.getIntakeId(),
                        new SimpleDateFormat("yyyy-MM-dd").format(intake.getStartDate()),
                        intake.getIntakeName(),
                        intake.getProgramId(),
                        intake.isIntakeCompleteness(),
                        btn
                );

                btn.setOnAction(e -> {
               Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);
               Optional<ButtonType> buttonType = alert.showAndWait();

                    if(buttonType.get().equals(ButtonType.YES)){
                        Database.intakeTable.remove(intake);
                        new Alert(Alert.AlertType.INFORMATION,"Success").show();
                        setTableData(text);
                    }
                });

                obList.add(intakeTM);
            }
        }
        tblIntake.setItems(obList);
    }

    private void clear() {
        txtDate.setValue(null);
        txtIntakeName.clear();
    }
}
