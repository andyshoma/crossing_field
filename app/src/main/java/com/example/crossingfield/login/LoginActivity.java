package com.example.crossingfield.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crossingfield.home.HomeActivity;
import com.example.crossingfield.lib.MySocket;
import com.example.crossingfield.R;
import com.example.crossingfield.lib.User;

import java.util.concurrent.CountDownLatch;

import static com.example.crossingfield.lib.MathConstants.*;
import static com.example.crossingfield.lib.StringConstants.*;

public class LoginActivity extends AppCompatActivity {

    public Context context;
    public EditText userText;
    public EditText passText;
    private String send_message;

    public CountDownLatch latch;
    private User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = getApplicationContext();

        userText = findViewById(R.id.user_text);
        passText = findViewById(R.id.pass_text);
        Button login_button = findViewById(R.id.login);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login(){
        String username = userText.getText().toString();
        String password = passText.getText().toString();
        send_message = LOGIN + "," + username + ',' + password + ',';

        latch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginTask task = new LoginTask(context, send_message, latch);
                task.setOnCallBack(new LoginTask.LoginCallBackTask(){
                    @Override
                    public void CallBack(User user){
                        me = user;
                        System.out.println(me.getUsername());
                    }
                });
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                task.execute();
                System.out.println("uis");

                try{
                    latch.await();
                    Thread.sleep(1000);
                    System.out.println("hello");
                    if (me != null){
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.putExtra("my_user", me);
                        startActivity(intent);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
