package com.developerstack.edumanage.model;

import java.util.Date;

public class Student {

    private String id;
    private String fullName;
    private Date dob;
    private String address;

    public Student() {
    }

    public Student(String id, String fullName, Date dob, String address) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                '}';
    }
}
