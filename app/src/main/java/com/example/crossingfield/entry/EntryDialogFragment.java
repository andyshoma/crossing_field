package com.example.crossingfield.entry;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.crossingfield.R;
import com.example.crossingfield.home.HomeActivity;
import com.example.crossingfield.lib.MyHTTP;
import com.example.crossingfield.lib.MySocket;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.WeakHashMap;

public class EntryDialogFragment extends DialogFragment {

    private  Context context;
    private String message;
    private String sendMessage;
    private Bitmap bmp;
    private String image;
    private ImageView imageView;
    private String name;

    public EntryDialogFragment(Context context, String message, String sendMessage, Bitmap bmp, String image ,String name){
        this.context = context;
        this.message = message;
        this.sendMessage = sendMessage;
        this.image = image;
        this.bmp = bmp;
        this.name = name;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {

        //View alertView = getLayoutInflater().inflate(R.layout.dialog_entry, null);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getContext());
        textView.setText("以下の内容でよろしいですか？\n\n" + message);
        linearLayout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        imageView = new ImageView(getContext());
        imageView.setImageBitmap(bmp);
        linearLayout.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new AlertDialog.Builder(getActivity())
                .setTitle("登録内容")
                .setView(linearLayout)
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        entry();

                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.putExtra("name", name);
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
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {

                MySocket socket = new MySocket(context);
                socket.setMessage(sendMessage);
                String get_message = socket.trySend();

                return get_message;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);

                if (message.equals("done")) {
                    up();
                }
            }
        }.execute();
    }

    private void up(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids){

                // 送信先のURL
                String url = "http://192.168.2.200:8100/save_prof";

                MyHTTP http = new MyHTTP(url);
                http.upload(image);
                System.out.println("doing");

                return null;
            }
        }.execute();
    }
}