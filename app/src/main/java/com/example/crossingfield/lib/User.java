package com.example.crossingfield.lib;

import android.graphics.Bitmap;
import android.os.Parcelable;

import com.example.crossingfield.lib.StringConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("name")
    @Expose
    private String username = "";
    @SerializedName("gender")
    @Expose
    private String gender = "";
    @SerializedName("age")
    @Expose
    private Integer old = 0;
    @SerializedName("area")
    @Expose
    private String area = "";
    private Bitmap photo;

    public class Person{
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("age")
        @Expose
        public Integer age;
        @SerializedName("area")
        @Expose
        public String area;
        @SerializedName("name")
        @Expose
        public String user_name;

    }

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

    public JSONObject toJSON(){
        String str = "{" +
                "\"name\":\"" + username +
                "\", \"gender\":\"" + gender +
                "\", \"age\":" + old +
                ", \"area\":\"" + area +
                "\"}";
        JSONObject json;
        try{
            json = new JSONObject(str);
            return json;
        }catch (JSONException e){
            return null;
        }
    }

    @Override
    public String toString(){
        return "{" +
                "name:'" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + old + '\'' +
                ", area='" + area + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

}
