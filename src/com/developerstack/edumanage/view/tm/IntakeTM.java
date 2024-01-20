package com.developerstack.edumanage.view.tm;

import javafx.scene.control.Button;

public class IntakeTM {
    private String intakeId;
    private String startDate;
    private String intakeName;
    private String programId;
    private boolean intakeCompleteness;
    private Button btnRemove;

    public IntakeTM() {
    }

    public IntakeTM(String intakeId, String startDate, String intakeName, String programId, boolean intakeCompleteness, Button btnRemove) {
        this.intakeId = intakeId;
        this.startDate = startDate;
        this.intakeName = intakeName;
        this.programId = programId;
        this.intakeCompleteness = intakeCompleteness;
        this.btnRemove = btnRemove;
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }



    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public boolean isIntakeCompleteness() {
        return intakeCompleteness;
    }

    public void setIntakeCompleteness(boolean intakeCompleteness) {
        this.intakeCompleteness = intakeCompleteness;
    }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(Button btnRemove) {
        this.btnRemove = btnRemove;
    }

    @Override
    public String toString() {
        return "IntakeTM{" +
                "intakeId='" + intakeId + '\'' +
                ", startDate=" + getStartDate() +
                ", intakeName='" + intakeName + '\'' +
                ", programId='" + programId + '\'' +
                ", intakeCompleteness=" + intakeCompleteness +
                ", btnRemove=" + btnRemove +
                '}';
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
