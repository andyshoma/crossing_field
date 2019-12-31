package com.example.crossingfield.home.message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daasuu.bl.BubbleLayout;
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
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View messageView = layoutInflater.inflate(R.layout.layout_message, null);
                String message = edit.getText().toString();
                ((ImageView) messageView.findViewById(R.id.image_message)).setImageResource(R.drawable.monky);
                ((TextView) messageView.findViewById(R.id.text_message)).setText(message);

                linearLayout.addView(messageView);

                edit.getText().clear();
            }
        });
    }
}
