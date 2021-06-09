package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.amplifyframework.datastore.generated.model.State;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);


        ImageView image = findViewById(R.id.imageView);
        TextView linkTextView = findViewById(R.id.link_text_view_task_details_activity);
        TextView linkTitleTextView = findViewById(R.id.linkTitle_task_details_activity);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// to display the back button in the action bar

        TextView bodyView = findViewById(R.id.txt_view_body_task_details);
        TextView stateView = findViewById(R.id.txt_view_state_task_details);
        Intent mainIntent = getIntent();
        String title = mainIntent.getStringExtra("title");
        String body = mainIntent.getStringExtra("body");
        State state = (State) mainIntent.getSerializableExtra("state");


        String fileName = mainIntent.getStringExtra("fileName");


        if(fileName != null){

            File outFile=new File(getApplicationContext().getFilesDir() + fileName);
            Amplify.Storage.downloadFile(
                    fileName,
                    outFile,
                    result -> {
                        Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getTotalSpace());

                        String fileType = getFileType(result.getFile().getPath());
                        boolean isImage = isTheFileImage(fileType);
//                        Toast.makeText(this, "is image: ? "+ isImage,Toast.LENGTH_SHORT).show();

                        if(isImage){
                            Bitmap bitmap = convertFileToImage(result.getFile());
                            image.setImageBitmap(bitmap);
                            image.setVisibility(View.VISIBLE);

                            linkTextView.setVisibility(View.INVISIBLE);
                            linkTitleTextView.setVisibility(View.INVISIBLE);

                        }
                        else{
                            String linkToFile = "https://tasksbucket184655-dev.s3.amazonaws.com/public/"+fileName;
                            linkTextView.setText(linkToFile);
                            linkTextView.setVisibility(View.VISIBLE);
                            linkTitleTextView.setVisibility(View.VISIBLE);

                            image.setVisibility(View.INVISIBLE);
                        }


                    },
                    error -> Log.e("MyAmplifyApp",  "Download Failure", error)
            );
        }



//


          // Room DB
//        TaskDataBase db = TaskDataBase.getInstance(this);
//        TaskDao taskDao = db.taskDao();

//        Task taskRoom = taskDao.findTaskById(id);

        setTitle(title);

        bodyView.setText(body);
        stateView.setText(state.toString());


    }

    private boolean isTheFileImage(String fileType) {
        Pattern pattern = Pattern.compile("^image/",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(fileType);
        return matcher.find();
    }


    public  String getFileType(String filePath) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(filePath);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    private Bitmap convertFileToImage(File file) {
        String filePath = file.getPath();
        return  BitmapFactory.decodeFile(filePath);
    }
}



