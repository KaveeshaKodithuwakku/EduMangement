package com.developerstack.edumanage.view.tm;


import javafx.scene.control.Button;

public class TechAddTM {
    private int codeT;
    private String name;
    private Button btnRemove;


    public TechAddTM() {
    }

    public TechAddTM(int codeT, String name, Button btnRemove) {
        this.codeT = codeT;
        this.name = name;
        this.btnRemove = btnRemove;
    }

    public int getCodeT() {
        return codeT;
    }

    public void setCodeT(int codeT) {
        this.codeT = codeT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(Button btnRemove) {
        this.btnRemove = btnRemove;
    }

    @Override
    public String toString() {
        return "TechAddTM{" +
                "codeT=" + codeT +
                ", name='" + name + '\'' +
                ", btnRemove=" + btnRemove +
                '}';
    }
}
