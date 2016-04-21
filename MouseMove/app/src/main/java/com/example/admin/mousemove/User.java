package com.example.admin.mousemove;

/**
 * Created by Admin on 4/17/2016.
 */
public class User {
    private int id;
    private String name, fullname, passcode;

    public User(int id, String name, String fullname, String passcode) {
        this.id = id;
        this.name = name;
        this.fullname = fullname;
        this.passcode = passcode;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public int getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPasscode() {
        return passcode;
    }

    @Override
    public String toString() {
        return id+"\t"+name+"\t"+fullname+"\t"+passcode;
    }
}
