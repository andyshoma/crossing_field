package com.example.crossingfield.home.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crossingfield.R;

public class MessageActivity extends AppCompatActivity {

    private EditText edit;
    private Context context;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        context = getApplicationContext();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        setTitle(name);

        edit = findViewById(R.id.send_box);
        linearLayout = findViewById(R.id.linear1);

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edit.getText().toString();
                TextView textView = new TextView(context);
                textView.setText(message);
                textView.setTextSize(30);
                textView.setGravity(Gravity.RIGHT|Gravity.BOTTOM);
                linearLayout.addView(textView);

                edit.getText().clear();
            }
        });
    }
}
