package com.example.anagrammer;

public class Users {
    String name1;
    String email1;
    String password1;
    String reTypePass1;

    public Users(){

    }

    public Users(String name1, String email1, String password1, String reTypePass1) {
        this.name1 = name1;
        this.email1 = email1;
        this.password1 = password1;
        this.reTypePass1 = reTypePass1;
    }

    public String getName() {
        return name1;
    }

    public String getEmail() {
        return email1;
    }

    public String getPassword() {
        return password1;
    }

    public String getReTypePass() {
        return reTypePass1;
    }
}
