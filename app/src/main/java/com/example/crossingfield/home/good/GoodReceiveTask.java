package com.example.crossingfield.home.good;

import android.content.Context;
import android.os.AsyncTask;

import com.example.crossingfield.lib.MySocket;
import com.example.crossingfield.lib.User;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class GoodReceiveTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private String get_message;
    private CountDownLatch latch;

    private GoodReceiveCallBackTask task;

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

    }

    public GoodReceiveTask(Context context, CountDownLatch latch) {
        this.context = context;
        this.latch = latch;
    }

    @Override
    protected String doInBackground(Void... voids) {
        MySocket socket = new MySocket(context);
        get_message = socket.getMessage();

        return null;
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);

        task.CallBack(str);
        latch.countDown();
    }

    public void setOnCallBack(GoodReceiveCallBackTask task){
        this.task = task;
    }

    public static class GoodReceiveCallBackTask{
        public void CallBack(String result){

        }
    }
}
