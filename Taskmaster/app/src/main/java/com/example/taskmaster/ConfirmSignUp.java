package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;

public class ConfirmSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);
        setTitle("Confirm Page");

        EditText codeET = findViewById(R.id.confirm_code_edit_text_confirm_activity);
        Button confirm = findViewById(R.id.confirm_button_confirm_activity);

        Intent signUpIntent = getIntent();
        String userName = signUpIntent.getStringExtra("userName");
        String code = codeET.getText().toString();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmCode(userName, code);
                Intent mainIntent = new Intent(ConfirmSignUp.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });


    }
    public void confirmCode(String userName,String code){
        Amplify.Auth.confirmSignUp(
                userName,
                code,
                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }
}