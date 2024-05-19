package com.omar.sams.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Models.AttendanceDataModel;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.StudentCoursesDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.Prof.Adapters.InsertStudentAttendanceListAdapter;
import com.omar.sams.Prof.InsertAttendanceActivity;
import com.omar.sams.R;
import com.omar.sams.Student.Adapters.StudentAttendanceAdapter;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    TextView attendanceProfName, attendanceCourseName;
    private String courseID;
    String currentUserID;
    FirebaseUser user;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;

    RecyclerView studentsRecyclerView;
    StudentAttendanceAdapter studentsAdapter;
    private LinearLayoutManager mLayoutManager;
    ImageView back_btn;
    private DatabaseReference mCoursesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        initViews();

    }

    private void initViews() {
        Intent i = getIntent();
        courseID = i.getStringExtra("courseId");

        attendanceProfName = findViewById(R.id.attendanceProfName);
        attendanceCourseName = findViewById(R.id.attendanceCourseName);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserID = user.getUid();
        mUsersRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);
        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses");

        studentsRecyclerView = findViewById(R.id.studentAttendanceRV);
        studentsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(AttendanceActivity.this, RecyclerView.VERTICAL, false);
        studentsRecyclerView.setLayoutManager(mLayoutManager);

        getCourseData();
        getStudentAttendanceList();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void getCourseData() {

        mCoursesRef.child(courseID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CourseDataModel course = snapshot.getValue(CourseDataModel.class);
                    attendanceCourseName.setText(course.getName());
                    attendanceProfName.setText(course.getProfName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getStudentAttendanceList() {

        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<AttendanceDataModel> studentsAttendanceList = new ArrayList<>();
                studentsAttendanceList.clear();
                if (snapshot.exists()) {
                    UserDataModel student = snapshot.getValue(UserDataModel.class);
                    for (StudentCoursesDataModel course : student.getStudentCourses()) {
                        if(course.getCourseId().equals(courseID)){
                            studentsAttendanceList = course.getAttendance();
                        }
                    }
                }

                studentsAdapter = new StudentAttendanceAdapter(AttendanceActivity.this, studentsAttendanceList);
                studentsRecyclerView.setAdapter(studentsAdapter);
                studentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}