package com.example.crossingfield;

//import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crossingfield.entry.EntryActivity;
import com.example.crossingfield.lib.MySocket;
import com.example.crossingfield.login.LoginActivity;
import static com.example.crossingfield.lib.MathConstants.*;

public class MainActivity extends Activity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Button entryButton = findViewById(R.id.entry);
        Button loginButton = findViewById(R.id.login);

        // エントリーボタンを押した時
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "hello", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, EntryActivity.class);
                startActivity(intent);
            }
        });

        // ログインボタンを押した時
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                MySocket socket = new MySocket(context);
                socket.setMessage(String.valueOf(INIT));
                socket.sendMessage();

                return null;
            }
        }.execute();
    }
}
