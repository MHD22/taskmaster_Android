package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        EditText userNameET,emailET,passET;
        userNameET = findViewById(R.id.user_name_edit_text_sign_up);
        emailET = findViewById(R.id.email_edit_text_sign_up);
        passET = findViewById(R.id.pass_edit_text_sign_up);

        Button signUpButton = findViewById(R.id.sign_up_button);

        String userName,email,pass;
        userName = userNameET.getText().toString();
        email = emailET.getText().toString();
        pass = passET.getText().toString();


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(userName,email,pass);
                Intent confirmIntent = new Intent(SignUp.this, ConfirmSignUp.class);
                confirmIntent.putExtra("userName",userName);
                startActivity(confirmIntent);
            }
        });

    }

    public void signUp(String userName, String email, String pass){

        // sign up

        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();


        Amplify.Auth.signUp(userName, pass, options,
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );

    }
}