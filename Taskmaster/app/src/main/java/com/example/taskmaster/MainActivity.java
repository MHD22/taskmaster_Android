package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.State;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Configure Amplify and DataStore
        try {
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }

//                create new tasks ..
//        Task task = Task.builder()
//                .title("Clean up")
//                .state(State.NEW)
//                .body("clean your room and rearrange your desk")
//                .build();
//
//        Amplify.DataStore.save(task,
//                success -> Log.i("Tutorial", "Saved task: " + success.item().getTitle()),
//                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
//        );

        List<Task> tasks2 = new ArrayList();

//        // query tasks..
        Amplify.DataStore.query(Task.class,
                tasks -> {
                    while (tasks.hasNext()) {
                        Task task = tasks.next();
                        tasks2.add(task);
                        Log.i("Tutorial", "==== Task ====");
                        Log.i("Tutorial", "Title: " + task.getTitle());

                        if (task.getBody() != null) {
                            Log.i("Tutorial", "Body: " + task.getBody().toString());
                        }

                        if (task.getState() != null) {
                            Log.i("Tutorial", "State: " + task.getState());
                        }
                    }
//                    triggerRecyclerView(tasks2);
                },
                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
        );






        // dataBase usage..
        TaskDataBase db = TaskDataBase.getInstance(this);
        TaskDao taskDao = db.taskDao();

        List<TaskRoom> tasksDB = taskDao.getAll();


//        RecyclerView
        RecyclerView recyclerView = findViewById(R.id.RV_main);
        TaskAdapter taskAdapter = new TaskAdapter(tasks2, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);


        Button settings = MainActivity.this.findViewById(R.id.button_settings);
        Button addTask = MainActivity.this.findViewById(R.id.button_add_task_main);
        TextView title  = findViewById(R.id.home_page_title);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPreferences.getString("userName","User");
        title.setText(userName+"' Tasks");



        //listeners:

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });

    }

//    private void triggerRecyclerView(List<Task> tasks2) {
//        RecyclerView recyclerView = findViewById(R.id.RV_main);
//        TaskAdapter taskAdapter = new TaskAdapter(tasks2, this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.canScrollVertically();
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(taskAdapter);
//    }
}