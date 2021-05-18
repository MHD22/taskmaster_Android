package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button task1 = MainActivity.this.findViewById(R.id.button_task1);
        Button task2 = MainActivity.this.findViewById(R.id.button_task2);
        Button task3 = MainActivity.this.findViewById(R.id.button_task3);
        Button settings = MainActivity.this.findViewById(R.id.button_settings);
        TextView title  = findViewById(R.id.home_page_title);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName","User");
        title.setText(userName+"' Tasks");

        



        task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String title = task1.getText().toString();
               clickTask(title);
            }
        });


        task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = task2.getText().toString();
                clickTask(title);
            }
        });


        task3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = task3.getText().toString();
                clickTask(title);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });


    }

    private void clickTask(String title) {
        Intent taskDetailsIntent = new Intent(MainActivity.this, TaskDetailActivity.class);
        taskDetailsIntent.putExtra("title",title);
        startActivity(taskDetailsIntent);
    }
}