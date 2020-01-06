package com.example.crossingfield.home.search;

import android.content.Context;
import android.os.AsyncTask;

import com.example.crossingfield.lib.MySocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class SearchTask extends AsyncTask<Void, Void, String> {

    private CountDownLatch latch;
    private Context context;
    private String message;

    SearchCallBackTask task;

    public SearchTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        MySocket socket = new MySocket(context);
        socket.setMessage(message);
        String get_message = socket.trySend();

        return get_message;

    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        task.CallBack(string);
        System.out.println("end");

        latch.countDown();
    }

    public void setPram(CountDownLatch latch, String message){
        this.latch = latch;
        this.message = message;
    }

    public void setOnCallBack(SearchCallBackTask task){
        this.task = task;
    }

    public static class SearchCallBackTask{
        public void CallBack(String result){

        }
    }
}
