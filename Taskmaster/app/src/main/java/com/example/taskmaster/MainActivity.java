package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras felis massa, elementum a nibh sed, sodales posuere nunc. Vivamus eget ante malesuada, fermentum tellus eget, dignissim enim. Duis felis enim, facilisis in tortor eget, pellentesque tristique dolor. Nullam hendrerit ex at sagittis tincidunt. Cras in sodales mauris. Quisque lobortis nisl quis rhoncus accumsan. ";
        RecyclerView recyclerView = findViewById(R.id.RV_main);
        List tasks = new ArrayList<>();
        Task t1 = new Task("Clean the room", body, State.NEW );
        Task t2 = new Task("Study 2 hours", body, State.NEW );
        Task t3 = new Task("Eat your meal", body, State.NEW );
        Task t4 = new Task("Take a shower", body, State.NEW );
        Task t5 = new Task("Feed yor cat", body, State.NEW );
        Task t6 = new Task("Sleep 6 hours", body, State.NEW );
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        tasks.add(t4);
        tasks.add(t5);
        tasks.add(t6);
        TaskAdapter taskAdapter = new TaskAdapter(tasks, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);






        Button settings = MainActivity.this.findViewById(R.id.button_settings);
        TextView title  = findViewById(R.id.home_page_title);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName","User");
        title.setText(userName+"' Tasks");

        




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