package com.developerstack.edumanage.view.tm;

public class RegistrationTM {
    private String regId;
    private String regDate;
    private String student;
    private String paymentStatus;

    public RegistrationTM() {
    }

    public RegistrationTM(String regId, String regDate, String student, String paymentStatus) {
        this.regId = regId;
        this.regDate = regDate;
        this.student = student;
        this.paymentStatus = paymentStatus;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "RegistrationTM{" +
                "regId='" + regId + '\'' +
                ", regDate='" + regDate + '\'' +
                ", student='" + student + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
