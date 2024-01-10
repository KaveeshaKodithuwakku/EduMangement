package com.developerstack.edumanage.model;

import java.util.Arrays;

public class Program {
    private String programCode;
    private String name;
    private String[] technologies;
    private double cost;
    private String teacher;

    public Program() {
    }

    public Program(String programCode, String name, String[] technologies, double cost, String teacher) {
        this.programCode = programCode;
        this.name = name;
        this.technologies = technologies;
        this.cost = cost;
        this.teacher = teacher;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String[] technologies) {
        this.technologies = technologies;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programCode='" + programCode + '\'' +
                ", name='" + name + '\'' +
                ", technologies=" + Arrays.toString(technologies) +
                ", cost=" + cost +
                ", teacher='" + teacher + '\'' +
                '}';
    }
}
