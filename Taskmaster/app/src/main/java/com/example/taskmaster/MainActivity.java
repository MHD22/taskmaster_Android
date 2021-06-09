package com.example.taskmaster;

import androidx.annotation.NonNull;
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
import android.widget.Toast;


import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.login_button_main_activity);
        Button logout = findViewById(R.id.lougout_button_main_activity);



            Amplify.Auth.fetchAuthSession(
                    result ->{
                        if(! result.isSignedIn()){
                            Intent loginIntent = new Intent(this,LoginActivity.class);
                            startActivity(loginIntent);
                        }
                        else{
                            String userNameTitle =Amplify.Auth.getCurrentUser().getUsername();
                            setTitle(userNameTitle+"'s Tasks");
                        }
                    },
                    error -> Log.e("AmplifyQuickstart", error.toString())
            );

//            Amplify.Auth.fetchUserAttributes(
//                    attributes -> Log.i("AuthDemo", "User attributes = " + attributes.toString()),
//                    error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
//            );


        // Fire Base ..
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM:: ", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast

                        Log.d("FCM:: ", token);

                    }
                });





        // dataBase usage..
        TaskDataBase db = TaskDataBase.getInstance(this);
        TaskDao taskDao = db.taskDao();

        List<TaskRoom> tasksDB = taskDao.getAll();


//        RecyclerView
        RecyclerView recyclerView = findViewById(R.id.RV_main);
        TaskAdapter taskAdapter = new TaskAdapter(tasksDB, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.canScrollVertically();
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);


        Button settings = MainActivity.this.findViewById(R.id.button_settings);
        Button addTask = MainActivity.this.findViewById(R.id.button_add_task_main);

        //-----------------------------
        // for setting button.. and get name from settings.
//        TextView title  = findViewById(R.id.home_page_title);
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String userName = sharedPreferences.getString("userName","User");
//        title.setText(userName+"' Tasks");

        //-----------------------------


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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(signUpIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );

                setTitle("Task Master");
            }
        });






    }


    @Override
    protected void onResume() {
        super.onResume();

        Amplify.Auth.fetchAuthSession(
                result ->{
                    if(! result.isSignedIn()){

                        Intent loginIntent = new Intent(this,LoginActivity.class);
                        startActivity(loginIntent);
                    }
                    else{
                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

    }


}




// dynamo DB:
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

//        List<Task> tasks2 = new ArrayList();

//        // query tasks..
//        Amplify.DataStore.query(Task.class,
//                tasks -> {
//                    while (tasks.hasNext()) {
//                        Task task = tasks.next();
//                        tasks2.add(task);
//                        Log.i("Tutorial", "==== Task ====");
//                        Log.i("Tutorial", "Title: " + task.getTitle());
//
//                        if (task.getBody() != null) {
//                            Log.i("Tutorial", "Body: " + task.getBody().toString());
//                        }
//
//                        if (task.getState() != null) {
//                            Log.i("Tutorial", "State: " + task.getState());
//                        }
//                    }
////                    triggerRecyclerView(tasks2);
//                },
//                failure -> Log.e("Tutorial", "Could not query DataStore", failure)
//        );


