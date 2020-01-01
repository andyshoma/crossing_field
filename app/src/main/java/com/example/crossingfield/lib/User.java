package com.example.crossingfield.lib;

import com.example.crossingfield.lib.StringConstants;

public class User {

    private String username;
    private String gender;
    private Integer old;
    private String area;
    private int photo;

    public User(){}

    public User(String username, String gender, Integer old, String area, int photo){
        this.username = username;
        this.gender = gender;
        this.old = old;
        this.area = area;
        this.photo = photo;
    }


    public void setUsername(String username){
        this.username = username;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setOld(Integer old){
        this.old = old;
    }

    public void setArea(String area){
        this.area = area;
    }

    public void setPhoto(int photo){
        this.photo = photo;
    }


    public String getUsername(){
        return username;
    }

    public String getGender(){
        return gender;
    }

    public Integer getOld(){
        return old;
    }

    public String getArea(){
        return area;
    }

    public int getPhoto(){
        return photo;
    }

}
