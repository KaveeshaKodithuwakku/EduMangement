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

public class DashBoardFormController {
    public AnchorPane context;
    public Label lblDate;
    public Label lblTime;
    
    public void initialize(){
        setData();

    }

    private void setData() {

        // -------------- use this way in industry ----------------
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //lblTime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));

        //---------- To count time ----------------------
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                         e -> {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                            lblTime.setText(LocalTime.now().format(dateTimeFormatter));
                         }
                         ),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

//        Date date = new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String textDate = simpleDateFormat.format(date);
//        lblDate.setText(textDate);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
