package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.State;
import com.amplifyframework.datastore.generated.model.Task;

public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    State state = State.NEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Spinner spinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.stateSpinner,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // to display the back button in the action bar


        EditText title,body;
        title = findViewById(R.id.taskTitle);
        body = findViewById(R.id.taskDescription);

        Button addTask = findViewById(R.id.button_addTask_activity_addTask);

        // database using Room
        TaskDataBase db = TaskDataBase.getInstance(this);
        TaskDao taskDao = db.taskDao();


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskRoom taskRoom = new TaskRoom(title.getText().toString(),body.getText().toString(), State.NEW);
                Task task = Task.builder().title(title.getText().toString()).state(state).body(body.getText().toString()).build();

                Amplify.DataStore.save(task,
                success -> Log.i("Tutorial", "Saved task: " + success.item().getTitle()),
                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
                );

                taskDao.insert(taskRoom);

                Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });





    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if(text.toLowerCase().equals("new"))
            state = State.NEW;
        if(text.toLowerCase().equals("assigned"))
            state = State.ASSIGNED;
        if(text.toLowerCase().equals("in process"))
            state = State.IN_PROCESS;
        if(text.toLowerCase().equals("complete"))
            state = State.COMPLETE;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}