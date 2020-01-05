package com.example.crossingfield.lib;

import android.graphics.Bitmap;

import com.example.crossingfield.lib.StringConstants;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String gender;
    private Integer old;
    private String area;
    private Bitmap photo;

    public User(){}

    public User(String username, String gender, Integer old, String area, Bitmap photo){
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

    public void setPhoto(Bitmap photo){
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

    public Bitmap getPhoto(){
        return photo;
    }

}
