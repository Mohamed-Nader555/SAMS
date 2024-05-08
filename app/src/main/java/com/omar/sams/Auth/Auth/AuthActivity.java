package com.omar.sams.Auth.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sams.Auth.Login.LoginOptionsActivity;
import com.omar.sams.Auth.Signup.SignupOptionsActivity;
import com.omar.sams.R;

public class AuthActivity extends AppCompatActivity {

    Button authLoginBtn, authSignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initViews();


    }

    private void initViews() {

        authLoginBtn = findViewById(R.id.auth_login_btn);
        authSignupBtn = findViewById(R.id.auth_signup_btn);


        authLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthActivity.this, LoginOptionsActivity.class));
                finish();
            }
        });


        authSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthActivity.this, SignupOptionsActivity.class));
                finish();
            }
        });


    }


}