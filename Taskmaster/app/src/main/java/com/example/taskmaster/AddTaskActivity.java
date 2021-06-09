package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Locale;


public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    State state = State.NEW;
    String fileName = null;

    Uri fileUri = null;

    double altitude =0;
    double longitude = 0;


    private FusedLocationProviderClient fusedLocationClient;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);




        Button pickFileButton = findViewById(R.id.pick_file_button_add_task_activity);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
             requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1212);
        }
        else {

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    altitude = location.getAltitude();
                                    longitude = location.getLongitude();

                                    Toast.makeText(AddTaskActivity.this,location.getAltitude()+"",Toast.LENGTH_SHORT).show();


                                    Geocoder geocoder = new Geocoder(AddTaskActivity.this,Locale.getDefault());
                                    try {
                                        List<Address> addresses = geocoder.getFromLocation(altitude,longitude,1);
                                        if(addresses != null && addresses.size()>0){
//                                            Toast.makeText(AddTaskActivity.this, "country Nmae: "+ addresses.get(0).getAddressLine(0).split(",")[1], Toast.LENGTH_SHORT).show();

                                        }

//                                      countryName = getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry();

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

            }







        // receive image from another app:
        // Get the intent that started this activity
        Intent intent = getIntent();
        Uri data = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        fileUri = data;
//        (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

//        Toast.makeText(this,"file loaded "+ "Helloooo 1 "+data.getPath() ,Toast.LENGTH_SHORT).show();

        // Figure out what to do based on the intent type

        if (data != null && intent.getType() != null && intent.getType().indexOf("image/") != -1) {
            pickFileButton.setEnabled(false);

//            uploadFile(data);
            Toast.makeText(this, "file loaded " + "Helloooo 1 ", Toast.LENGTH_SHORT).show();

        }

        Spinner spinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stateSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // to display the back button in the action bar


        EditText title, body;
        title = findViewById(R.id.taskTitle);
        body = findViewById(R.id.taskDescription);

        Button addTask = findViewById(R.id.button_addTask_activity_addTask);

        // database using Room
        TaskDataBase db = TaskDataBase.getInstance(this);
        TaskDao taskDao = db.taskDao();


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadFile(fileUri);

                TaskRoom taskRoom = new TaskRoom(title.getText().toString(), body.getText().toString(), state, fileName, altitude, longitude);
//                Task task = Task.builder().title(title.getText().toString()).state(state).body(body.getText().toString()).build();

//                Amplify.DataStore.save(task,
//                success -> Log.i("Tutorial", "Saved task: " + success.item().getTitle()),
//                error -> Log.e("Tutorial", "Could not save item to DataStore", error)
//                );

                Toast.makeText(AddTaskActivity.this, "fileName: " + fileName, Toast.LENGTH_SHORT).show();
                taskDao.insert(taskRoom);


                Intent mainIntent = new Intent(AddTaskActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });


        // attaching a file :
        pickFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTaskActivity.this, "Altitude: "+altitude, Toast.LENGTH_SHORT).show();
                getFileFormMobile();
                Toast.makeText(AddTaskActivity.this, "fileName: " + fileName, Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (text.toLowerCase().equals("new"))
            state = State.NEW;
        if (text.toLowerCase().equals("assigned"))
            state = State.ASSIGNED;
        if (text.toLowerCase().equals("in process"))
            state = State.IN_PROCESS;
        if (text.toLowerCase().equals("complete"))
            state = State.COMPLETE;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1199) {

            // get file name ..
            Uri returnUri = data.getData();
            fileUri = returnUri;
//            uploadFile(returnUri);

        }
    }


    public void uploadFile(Uri returnUri) {
        if (returnUri == null)
            return;

        Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String file_name = returnCursor.getString(nameIndex);
        this.fileName = file_name;

        File file = new File(getApplicationContext().getFilesDir(), file_name);
        try {
            InputStream inputStream = getContentResolver().openInputStream(returnUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                FileUtils.copy(inputStream, new FileOutputStream(file));
                Toast.makeText(this, "file loaded " + file.getName(), Toast.LENGTH_SHORT).show();
                uploadFile(file, file_name);
            } else {
                Toast.makeText(this, "file Error ", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadFile(File file, String fileName) {
        Amplify.Storage.uploadFile(
                fileName,
                file,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }

    public void getFileFormMobile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 1199);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1212) {

            if (grantResults.length > 0 ){

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        altitude = location.getAltitude();
                                        longitude = location.getLongitude();
                                    }
                                }
                            });
                }


            }
        }
    }
}