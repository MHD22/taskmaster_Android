package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);


        Intent mainIntent = getIntent();
        String title = mainIntent.getStringExtra("title");

        TextView titleView = findViewById(R.id.task_details_title_text_view);

        titleView.setText(title);


    }
}