package com.developerstack.edumanage.db;

import com.developerstack.edumanage.model.User;
import com.developerstack.edumanage.util.security.PasswordManager;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> userTable = new ArrayList<>();

    static {
        userTable.add(
                new User("Kaveesha","Madushani","k@gmail.com",
                        new PasswordManager().encrypt("1234"))
        );
    }
}
