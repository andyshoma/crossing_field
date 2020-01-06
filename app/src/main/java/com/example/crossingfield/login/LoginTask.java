package com.example.crossingfield.login;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.crossingfield.home.HomeActivity;
import com.example.crossingfield.lib.MySocket;
import com.example.crossingfield.lib.User;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.concurrent.CountDownLatch;

public class LoginTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private String send_message;
    private CountDownLatch latch;

    private LoginCallBackTask task;

    public class Person{
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("age")
        @Expose
        public Integer age;
        @SerializedName("pass")
        @Expose
        public String password;
        @SerializedName("area")
        @Expose
        public String area;
        @SerializedName("name")
        @Expose
        public String user_name;
        @SerializedName("uri")
        @Expose
        public String uri;
    }

    public LoginTask(Context context, String send_message, CountDownLatch latch) {
        this.context = context;
        this.send_message = send_message;
        this.latch = latch;
    }

    @Override
    protected String doInBackground(Void... voids) {

        MySocket socket = new MySocket(context);
        socket.setMessage(send_message);
        String str = socket.trySend();

        return str;
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);

        latch.countDown();

        if (!str.equals("\n") && !str.isEmpty()){
            // usernameとpasswordが正しい場合
            User me = jsonToUser(str);
            task.CallBack(me);
        }else{
            // usernameもしくはpasswordが間違っていた場合
            task.CallBack(null);
            Toast.makeText(context, "正しくありません", Toast.LENGTH_LONG);
        }
    }

    public User jsonToUser(String json){
            Gson gson = new Gson();
            Person person = gson.fromJson(json, Person.class);
            User user = setUser(person);

            return user;
    }

    public User setUser(Person person){
        User user = new User();
        user.setGender(person.gender);
        user.setOld(person.age);
        user.setArea(person.area);
        user.setUsername(person.user_name);

        return user;
    }

    public void setOnCallBack(LoginCallBackTask task){
        this.task = task;
    }

    public static class LoginCallBackTask{
        public void CallBack(User result){

        }
    }
}
