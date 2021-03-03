package com.example.tmcronin.model;

public class Users {
    private String name, phone, password;
//Code used to pass references through parentDbName instead of using quoted Users as we'll be using it in several pages. Some code acquired from Youtube tutorial https://www.youtube.com/watch?v=wuICtQpggg0&t=50s
    public Users()
    {

    }

    public Users(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;


    }
}


