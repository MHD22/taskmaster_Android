package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextView bodyView = findViewById(R.id.txt_view_body_task_details);
        TextView stateView = findViewById(R.id.txt_view_state_task_details);
        Intent mainIntent = getIntent();
        int id = mainIntent.getIntExtra("id",0);





        TaskDataBase db = TaskDataBase.getInstance(this);
        TaskDao taskDao = db.taskDao();

        Task task = taskDao.findTaskById(id);
        setTitle(task.title);

        bodyView.setText(task.body);
        stateView.setText(task.state.toString());


    }
}


