package com.example.contentprovider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MessageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        TextView senderTextView = findViewById(R.id.senderTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView messageBodyTextView = findViewById(R.id.messageBodyTextView);

        Intent intent = getIntent();
        String sender = intent.getStringExtra("sender");
        String date = intent.getStringExtra("date");
        String messageBody = intent.getStringExtra("messageBody");

        senderTextView.setText(sender);
        dateTextView.setText(date);
        messageBodyTextView.setText(messageBody);
    }
}