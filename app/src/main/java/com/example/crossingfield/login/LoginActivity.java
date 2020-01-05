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
import static com.example.crossingfield.lib.MathConstants.*;
import static com.example.crossingfield.lib.StringConstants.*;

public class LoginActivity extends AppCompatActivity {

    public Context context;
    public EditText userText;
    public EditText passText;

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
                //login();
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private String login(){
        String username = userText.getText().toString();
        String password = passText.getText().toString();
        final String sendMessage = LOGIN + "," + username + ',' + password;
        System.out.println(sendMessage);

        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {

                MySocket socket = new MySocket(context);
                socket.setMessage(sendMessage);
                String str = socket.trySend();

                return str;
            }

            @Override
            protected void onPostExecute(String str) {
                super.onPostExecute(str);

                if (str.equals("\n") == false && str == null){
                    // usernameとpasswordが正しい場合

                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                }else{
                    // usernameもしくはpasswordが間違っていた場合

                    Toast.makeText(context, "正しくありません", Toast.LENGTH_LONG);
                    System.out.println("間違い");
                }
            }
        }.execute();

        return "jda";
    }
}
