package com.omar.sams.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.omar.sams.Auth.Auth.AuthActivity;
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.R;
import com.omar.sams.Utils.CheckInternetConnection;

public class SplashActivity extends AppCompatActivity {


    private CheckInternetConnection cd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        cd = new CheckInternetConnection(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        splashTimer();
    }

    private void splashTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!cd.isConnected()) {
                    Toast.makeText(SplashActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
                    sendUserToHome();
                } else {
                    checkUser();
                }
            }
        }, 2000);
    }


    void checkUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendUserToLogin();
        } else {
            sendUserToHome();
        }
    }

    void sendUserToLogin() {
        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
        finish();
    }

    void sendUserToHome() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        Intent intent = new Intent(SplashActivity.this, HelloActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}