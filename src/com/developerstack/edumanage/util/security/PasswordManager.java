package com.developerstack.edumanage.util.security;

import org.mindrot.BCrypt;

public class PasswordManager {

    public String encrypt(String rowPassword){
        return BCrypt.hashpw(rowPassword,BCrypt.gensalt(10));//BCrypt.gensalt(10) -> encoding type
    }

    public boolean checkPassword(String rowPassword, String hashPassword){
        return BCrypt.checkpw(rowPassword, hashPassword);
    }
}
