package com.example.crossingfield.home.search;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.crossingfield.lib.MyHTTP;

import java.util.concurrent.CountDownLatch;

public class GoodImageTask extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private CountDownLatch latch;
    private String name;

    private GoodImageCallBackTask task;

    public GoodImageTask(Context context, CountDownLatch latch){
        this.context = context;
        this.latch = latch;
    }

    @Override
    protected Bitmap doInBackground(Void...voids) {

        Bitmap image;
        String url = "http://192.168.2.200:8100/send";
        MyHTTP http = new MyHTTP(url);
        image = http.download(name);

        return image;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
        super.onPostExecute(image);

        task.CallBack(image);

        latch.countDown();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setOnCallBack(GoodImageCallBackTask task){
        this.task = task;
    }

    public static class GoodImageCallBackTask{
        public void CallBack(Bitmap result){

        }
    }

}
