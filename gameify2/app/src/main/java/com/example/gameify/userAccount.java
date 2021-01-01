package com.example.gameify;

import java.io.FileInputStream;
import java.util.ArrayList;

public class userAccount {

    public static ArrayList<userAccount> userAccountArrayList = new ArrayList<>();
    private String name;
    private String surname;
    private String age;
    private String email;
    private String username;
    private String password;
    private String re_password;



    public userAccount(FileInputStream outFile) {
    }

    public userAccount(String name, String surname, String age, String email, String username, String password, String re_password) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
        this.re_password = re_password;
        System.out.println("Account Created");
    }

    public static ArrayList<userAccount> getUserAccountArrayList() {
        return userAccountArrayList;
    }

    public static void setUserAccountArrayList(ArrayList<userAccount> userAccountArrayList) {
        userAccount.userAccountArrayList = userAccountArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRe_password() {
        return re_password;
    }

    public void setRe_password(String re_password) {
        this.re_password = re_password;
    }
}
