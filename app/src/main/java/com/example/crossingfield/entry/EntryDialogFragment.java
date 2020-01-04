package com.example.crossingfield.entry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.crossingfield.home.HomeActivity;
import com.example.crossingfield.lib.MySocket;

import java.util.List;

public class EntryDialogFragment extends DialogFragment {

    private  Context context;
    private String message;
    private String sendMessage;

    public EntryDialogFragment(Context context, String message, String sendMessage){
        this.context = context;
        this.message = message;
        this.sendMessage = sendMessage;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("登録内容")
                .setMessage("以下の内容でよろしいですか？\n\n" + message)
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        entry();

                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("いいえ", null)
                .create();
    }

    @Override
    public void onPause(){
        super.onPause();
        dismiss();
    }

    private void entry(){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

                MySocket socket = new MySocket(context);
                socket.setMessage(sendMessage);
                socket.sendMessage();

                return null;
            }
        }.execute();
    }
}