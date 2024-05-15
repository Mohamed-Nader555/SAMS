package com.omar.sams.Student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.StudentCoursesDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;
import com.omar.sams.Utils.CustomProgress;

import java.util.ArrayList;

public class CoursesInfoActivity extends AppCompatActivity {


    private TextView mStudentCourseNameText, mStudentProfNameText, mStudentCourseTimeLocationText,
            mCourseInfoMidTermGradeText, mCourseInfoFinalGradeText, mCourseInfoGradeText;
    private Button mStudentAttendanceBtn, mStudentMaterialBtn;

    private EditText mDescriptionTextField;

    private CustomProgress mCustomProgress = CustomProgress.getInstance();
    private FirebaseAuth mAuth;
    private String currentUserId;
    private DatabaseReference mUserRef;
    private DatabaseReference mCoursesRef;
    private UserDataModel userData;
    CourseDataModel courseData;

    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_info);

        initViews();

    }

    private void initViews() {

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses");

        userData = new UserDataModel();
        courseData = new CourseDataModel();

        Intent i = getIntent();
        courseID = i.getStringExtra("courseID");

        mStudentCourseNameText = findViewById(R.id.studentCourseNameText);
        mStudentProfNameText = findViewById(R.id.studentProfNameText);
        mStudentCourseTimeLocationText = findViewById(R.id.studentCourseTimeLocationText);
        mCourseInfoMidTermGradeText = findViewById(R.id.courseInfoMidTermGradeText);
        mCourseInfoFinalGradeText = findViewById(R.id.courseInfoFinalGradeText);
        mCourseInfoGradeText = findViewById(R.id.courseInfoGradeText);

        mStudentAttendanceBtn = findViewById(R.id.studentAttendanceBtn);
        mStudentMaterialBtn = findViewById(R.id.studentMaterialBtn);

        mDescriptionTextField = findViewById(R.id.descriptionTextField);
        mDescriptionTextField.setEnabled(false);


        mStudentAttendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCustomProgress.showProgress(this, "Loading...", false);

        getUserData();

        mCustomProgress.hideProgress();

    }

    private void getUserData() {

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userData = snapshot.getValue(UserDataModel.class);
                    ArrayList<StudentCoursesDataModel> studentCourses = userData.getStudentCourses();
                    for (StudentCoursesDataModel course: studentCourses) {
                        if(course.getCourseId().equals(courseID)){
                            mCourseInfoMidTermGradeText.setText(course.getMidtermGrade());
                            mCourseInfoFinalGradeText.setText(course.getFinalGrade());
                            mCourseInfoGradeText.setText(course.getGradeAlpha());
                        }
                    }
                    getCourseData(userData.getGroup());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getCourseData(String groupName) {

        mCoursesRef.child(courseID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 if (snapshot.exists()) {
                     CourseDataModel course = snapshot.getValue(CourseDataModel.class);
                     mStudentCourseNameText.setText(course.getName());
                     mStudentProfNameText.setText(course.getProfName());

                     int groupNumber = Integer.parseInt(groupName) - 1;

                     mStudentCourseTimeLocationText.setText(course.getGroups().get(groupNumber).getTime()
                             + " | " + course.getGroups().get(groupNumber).getRoom());
                     mDescriptionTextField.setText(course.getDescription());

                     materialButtonClick(course.getDriveLink());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void materialButtonClick(String driveLink) {
        mStudentMaterialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(driveLink));
                startActivity(intent);
            }
        });
    }

}