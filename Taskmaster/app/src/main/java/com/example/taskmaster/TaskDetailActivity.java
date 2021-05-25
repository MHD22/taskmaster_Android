package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.State;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// to display the back button in the action bar

        TextView bodyView = findViewById(R.id.txt_view_body_task_details);
        TextView stateView = findViewById(R.id.txt_view_state_task_details);
        Intent mainIntent = getIntent();
        String title = mainIntent.getStringExtra("title");
        String body = mainIntent.getStringExtra("body");
        State state = (State)mainIntent.getSerializableExtra("state");




          // Room DB
//        TaskDataBase db = TaskDataBase.getInstance(this);
//        TaskDao taskDao = db.taskDao();

//        Task taskRoom = taskDao.findTaskById(id);

        // dynamo DB
        setTitle(title);

        bodyView.setText(body);
        stateView.setText(state.toString());


    }
}


