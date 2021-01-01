package com.example.gameify;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private int age;
    private String name,surname,username,email;
    private ArrayList<JSONObject> gamedata = new ArrayList<>(50);
   /* public User(int age, String name, String surname, String username, String email) {
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
    }*/

    public User(int age, String name, String surname, String username, String email, ArrayList<JSONObject> gamedata) {
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.gamedata = gamedata;
    }

    public ArrayList<JSONObject>  getGamedata() {
        return gamedata;
    }

    public void setGamedata(ArrayList<JSONObject>  gamedata) {
        this.gamedata = gamedata;
    }
    public void addGame(JSONObject game){
        this.gamedata.add(game);
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
