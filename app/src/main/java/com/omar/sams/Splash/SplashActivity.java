package com.omar.sams.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Auth.Auth.AuthActivity;
import com.omar.sams.Auth.Login.LoginEmailActivity;
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;
import com.omar.sams.Utils.CheckInternetConnection;

public class SplashActivity extends AppCompatActivity {


    private CheckInternetConnection cd;
    private FirebaseAuth mAuth;

//    private DatabaseReference mUserRef;
    private String currentUserEmail;
//    private UserDataModel userData;

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
                    Intent helloPageIntent = new Intent(SplashActivity.this, HelloActivity.class);
                    helloPageIntent.putExtra("isConnected", false);
                    startActivity(helloPageIntent);
                    finish();
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
            checkUserType();
        }
    }

    void sendUserToLogin() {
        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
        finish();
    }

    void checkUserType() {

        currentUserEmail = mAuth.getCurrentUser().getEmail();

        if (currentUserEmail.contains("@sams.edu.eg")) {
            Intent helloPageIntent = new Intent(SplashActivity.this, HelloActivity.class);
            helloPageIntent.putExtra("isAdmin", true);
            helloPageIntent.putExtra("isConnected", true);
            startActivity(helloPageIntent);
            finish();
        } else {
            Intent helloPageIntent = new Intent(SplashActivity.this, HelloActivity.class);
            helloPageIntent.putExtra("isAdmin", false);
            helloPageIntent.putExtra("isConnected", true);
            startActivity(helloPageIntent);
            finish();
        }


//        currentUserID = mAuth.getCurrentUser().getUid();
//        mUserRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
//        mUserRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    userData = snapshot.getValue(UserDataModel.class);
//                    sendUserToHome(userData);
//                 }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }



}