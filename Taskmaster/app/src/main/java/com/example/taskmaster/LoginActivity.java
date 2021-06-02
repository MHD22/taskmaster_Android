package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);

            Intent homeIntent = new Intent(this,MainActivity.class);
            startActivity(homeIntent);
//            Amplify.Auth.fetchAuthSession(
//                    result ->{
//                        if(result.isSignedIn()){
//                            Log.i("AmplifyQuickstart", result.toString());
//                            Intent homeIntent = new Intent(this,MainActivity.class);
//                            startActivity(homeIntent);
//                        }
//                    },
//                    error -> Log.e("AmplifyQuickstart", error.toString())
//            );
//
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login_button_login_activity);

        setTitle("Login");




        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");



        }
        catch (AmplifyException e) {
            Log.e("Tutorial", "Could not initialize Amplify", e);
        }
        finally {

            Amplify.Auth.fetchAuthSession(
                    result ->{
                        if(result.isSignedIn()){
                            Log.i("AmplifyQuickstart", result.toString());
                            Intent homeIntent = new Intent(this,MainActivity.class);
                            startActivity(homeIntent);
                        }
                    },
                    error -> Log.e("AmplifyQuickstart", error.toString())
            );

            Amplify.Auth.signInWithWebUI(
                    this,
                    result -> Log.i("AuthQuickStart ", result.toString()),
                    error -> Log.e("AuthQuickStart", error.toString())

            );

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Amplify.Auth.fetchAuthSession(
                            result -> {
                                if (result.isSignedIn()) {
                                    Log.i("AmplifyQuickstart", result.toString());
                                    Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(homeIntent);
                                }
                                else{
                                    Amplify.Auth.signInWithWebUI(
                                            LoginActivity.this,
                                            result2 -> Log.i("AuthQuickStart ", result2.toString()),
                                            error2 -> Log.e("AuthQuickStart", error2.toString())

                                    );
                                }
                            },
                            error -> Log.e("AmplifyQuickstart", error.toString())
                    );
                }
            });

        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        Amplify.Auth.fetchAuthSession(
                result ->{
                    if(result.isSignedIn()){
                        Log.i("AmplifyQuickstart", result.toString());
                        Intent homeIntent = new Intent(this,MainActivity.class);
                        startActivity(homeIntent);
                    }
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

    }
}