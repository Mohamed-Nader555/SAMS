package com.omar.sams.Prof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Models.StudentCoursesDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.Prof.Adapters.InsertStudentAttendanceListAdapter;
import com.omar.sams.R;

import java.util.ArrayList;

public class InsertAttendanceActivity extends AppCompatActivity {

    TextView insertAttendaceGroupNumberText, insertAttendanceDateText;
    private String lectureDate, groupNumber, courseID;

    RecyclerView studentsRecyclerView;
    InsertStudentAttendanceListAdapter studentsAdapter;
    private LinearLayoutManager mLayoutManager;
    ImageView back_btn;
    private DatabaseReference mUserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_attendance);

        initViews();
    }

    private void initViews() {
        Intent i = getIntent();
        lectureDate = i.getStringExtra("lectureDate");
        groupNumber = i.getStringExtra("groupName");
        courseID = i.getStringExtra("courseId");

        insertAttendanceDateText = findViewById(R.id.insertAttendanceDateText);
        insertAttendaceGroupNumberText = findViewById(R.id.insertAttendaceGroupNumberText);

        insertAttendanceDateText.setText(lectureDate);
        insertAttendaceGroupNumberText.setText(groupNumber);
        mUserRef = FirebaseDatabase.getInstance().getReference("Users");

        studentsRecyclerView = findViewById(R.id.insertStudentAttendanceRV);
        studentsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(InsertAttendanceActivity.this, RecyclerView.VERTICAL, false);
        studentsRecyclerView.setLayoutManager(mLayoutManager);
        getStudentsList();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void getStudentsList() {

        ArrayList<UserDataModel> studentsList = new ArrayList<>();


        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentsList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnap : snapshot.getChildren()) {
                        if (!childSnap.child("profId").exists()) {
                            UserDataModel userData = childSnap.getValue(UserDataModel.class);
                            if (userData.getGroup().equals(groupNumber)) {
                                for (StudentCoursesDataModel course : userData.getStudentCourses()) {
                                    if (course.getCourseId().equals(courseID)) {
                                        studentsList.add(userData);
                                    }
                                }
                            }
                        }
                    }
                }

                studentsAdapter = new InsertStudentAttendanceListAdapter(InsertAttendanceActivity.this, studentsList, courseID, lectureDate);
                studentsRecyclerView.setAdapter(studentsAdapter);
                studentsAdapter .notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}