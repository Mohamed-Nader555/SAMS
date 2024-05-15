package com.omar.sams.Common;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sams.R;

public class ProfileActivity extends AppCompatActivity {

    //get the provider, so if it phoneNumber or gmail, hide the change password button
    //firebase.auth().currentUser.providerData[0].providerId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}