package com.example.crossingfield.home.search;

import android.content.Context;
import android.os.AsyncTask;

import com.example.crossingfield.home.good.GoodListAdapter;
import com.example.crossingfield.lib.MySocket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;

import static com.example.crossingfield.lib.MathConstants.*;

public class GoodSendTask extends AsyncTask<Void, Void, Void> {

    private CountDownLatch latch;
    private Context context;
    private String send_message;

    public GoodSendTask(Context context, String myName, String yourName){
        this.context = context;
        this.send_message = String.valueOf(GOOD) + ',' + myName + ',' + yourName + ',';
    }

    @Override
    protected Void doInBackground(Void... voids) {
        MySocket socket = new MySocket(context);
        socket.setMessage(send_message);
        socket.send();

        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        super.onPostExecute(voids);
    }
}
