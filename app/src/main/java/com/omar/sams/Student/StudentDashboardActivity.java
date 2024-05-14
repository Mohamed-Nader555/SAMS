package com.omar.sams.Student;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sams.R;

public class StudentDashboardActivity extends AppCompatActivity {


    ImageView eContentBtn, clubsBtn, contactBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);


        initView();

    }

    private void initView() {

        eContentBtn = findViewById(R.id.studentEcontentBtn);
        clubsBtn = findViewById(R.id.studentClubsBtn);
        contactBtn = findViewById(R.id.studentContactBtn);


    }



}