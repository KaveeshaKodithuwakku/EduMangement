package com.developerstack.edumanage.db;

import com.developerstack.edumanage.model.*;
import com.developerstack.edumanage.util.security.PasswordManager;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable = new ArrayList<>();
    public static  ArrayList<Student> studentTable = new ArrayList<>();
    public static ArrayList<Teacher> teacherTable = new ArrayList<>();
    public static ArrayList<Program> programTable = new ArrayList<>();
    public static ArrayList<Intake> intakeTable = new ArrayList<>();
    public static ArrayList<Registration> registrationTable = new ArrayList<>();

    static {
        userTable.add(
                new User("Kaveesha","Madushani","k@gmail.com",
                        new PasswordManager().encrypt("1234"))
        );
    }
}
