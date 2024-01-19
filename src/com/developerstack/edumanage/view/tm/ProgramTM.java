package com.developerstack.edumanage.view.tm;

import javafx.scene.control.Button;

import java.util.Arrays;

public class ProgramTM {
    private String programId;
    private String name;
    private double cost;
    private String teacherId;
    private Button btnTech;
    private Button btnTeacher;



    public ProgramTM() {
    }


    public ProgramTM(String programId, String name, double cost, String teacherId, Button btnTech, Button btnTeacher) {
        this.programId = programId;
        this.name = name;
        this.cost = cost;
        this.teacherId = teacherId;
        this.btnTech = btnTech;
        this.btnTeacher = btnTeacher;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Button getBtnTech() {
        return btnTech;
    }

    public void setBtnTech(Button btnTech) {
        this.btnTech = btnTech;
    }

    public Button getBtnTeacher() {
        return btnTeacher;
    }

    public void setBtnTeacher(Button btnTeacher) {
        this.btnTeacher = btnTeacher;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "ProgramTM{" +
                "programId='" + programId + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", teacherId='" + teacherId + '\'' +
                ", btnTech=" + btnTech +
                ", btnTeacher=" + btnTeacher +
                '}';
    }
}
