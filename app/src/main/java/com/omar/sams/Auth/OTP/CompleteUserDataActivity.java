package com.omar.sams.Auth.OTP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Auth.Signup.SignupEmailActivity;
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.Models.AttendanceDataModel;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.ProfessorDataModel;
import com.omar.sams.Models.StudentCoursesDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;

import java.util.ArrayList;
import java.util.Date;

public class CompleteUserDataActivity extends AppCompatActivity {

    private final String TAG = "CompleteUserDataActivity";
    ProgressDialog mLoading;
    String phoneNumber;
    private TextInputLayout fullNameInputLayout, emailInputLayout, semesterInputLayout;
    private EditText fullNameEditText, emailEditText;
    private Spinner semesterSpinner, groupSpinner;
    private Button continueButton;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private DatabaseReference mCoursesRef;
    String groupSelected;
    String semesterSelected;
    ArrayList<StudentCoursesDataModel> coursesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_data);

        initViews();

    }

    private void initViews() {

        phoneNumber = getIntent().getStringExtra("mobile_number");

        mAuth = FirebaseAuth.getInstance();
        mLoading = new ProgressDialog(this);
        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses");

        Intent i = getIntent();
        boolean isGoogle = i.getBooleanExtra("isGoogle",false);
        String userName = i.getStringExtra("userName");
        String userEmail = i.getStringExtra("userEmail");

        fullNameInputLayout = findViewById(R.id.full_name_input_et);
        emailInputLayout = findViewById(R.id.email_input_et);

        fullNameEditText = findViewById(R.id.full_name_et);
        emailEditText = findViewById(R.id.email_et);

        if(isGoogle){
            fullNameEditText.setText(userName);
            emailEditText.setText(userEmail);
        }

        semesterSpinner = findViewById(R.id.semester_input_spinner);
        groupSpinner = findViewById(R.id.group_input_spinner);


        initGroupSpinner();
        initSemesterSpinner();

        continueButton = findViewById(R.id.continue_btn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    semesterSelected = semesterSpinner.getSelectedItem().toString();
                    groupSelected = groupSpinner.getSelectedItem().toString();
                    coursesList = initStudentCourses(semesterSelected, groupSelected);

                saveAccountUserData();
                }
            }
        });

        handelTextFields();

    }


    private void handelTextFields() {
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (!input.contains("@") || !input.contains("."))
                    emailInputLayout.setError("Invalid Email!");
                else emailInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    private void saveAccountUserData() {

        String currentUserID = mAuth.getCurrentUser().getUid();
        String currentPhoneNumber = mAuth.getCurrentUser().getPhoneNumber();
        UsersRef = FirebaseDatabase.getInstance().getReference("Users");

        final UserDataModel dataModel = new UserDataModel(currentUserID, fullNameEditText.getText().toString(), emailEditText.getText().toString(), currentPhoneNumber, "", "", semesterSelected, groupSelected, coursesList);


        UsersRef.child(currentUserID).setValue(dataModel).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CompleteUserDataActivity.this, "Data Saved Successfully", Toast.LENGTH_LONG).show();
                    Intent x = new Intent(CompleteUserDataActivity.this, HelloActivity.class);
                    startActivity(x);
                    finish();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(CompleteUserDataActivity.this, "Error Occurred :" + message, Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    boolean validate() {
        if (fullNameEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            emailEditText.setError("Password Don't Match");
            return false;
        } else if (semesterSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(CompleteUserDataActivity.this, "Please Select your Semester!", Toast.LENGTH_LONG).show();
            return false;
        } else if (groupSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(CompleteUserDataActivity.this, "Please Select your Group!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent x = new Intent(CompleteUserDataActivity.this, VerifyOtpActivity.class);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(x);
        finish();
    }


    private void initSemesterSpinner() {
        ArrayList<String> semesters = new ArrayList<>();
        semesters.add("Select Current Semester ...");
        semesters.add("Third Term");
        semesters.add("Fourth Term");


        ArrayAdapter semesterAdapter = new ArrayAdapter(CompleteUserDataActivity.this, android.R.layout.simple_spinner_item, semesters);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);
        semesterSpinner.setSelection(0);
    }


    private void initGroupSpinner() {
        ArrayList<String> groups = new ArrayList<>();
        groups.add("Select Your Group ...");
        groups.add("1");
        groups.add("2");
        groups.add("3");
        groups.add("4");


        ArrayAdapter groupAdapter = new ArrayAdapter(CompleteUserDataActivity.this, android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupAdapter);
        groupSpinner.setSelection(0);
    }


    private ArrayList<StudentCoursesDataModel> initStudentCourses(String userSemester, String userGroup) {

        ArrayList<StudentCoursesDataModel> studentCoursesList = new ArrayList<>();

        mCoursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnap : snapshot.getChildren()) {
                        CourseDataModel course = childSnap.getValue(CourseDataModel.class);
                        if (userSemester.equals(course.getSemester())) {

                            StudentCoursesDataModel studentCoursesDataModel = new StudentCoursesDataModel();
                            studentCoursesDataModel.setCourseId(course.getCourseId());
                            studentCoursesDataModel.setClasswork("0");
                            studentCoursesDataModel.setMidtermGrade("0");
                            studentCoursesDataModel.setFinalGrade("0");
                            studentCoursesDataModel.setGradeAlpha("-");
                            studentCoursesDataModel.setTotal("0");


                            int groupNumber = Integer.parseInt(userGroup) - 1;
                            ArrayList<AttendanceDataModel> attendanceDataModel = new ArrayList<>();
                            for (Date date : course.getGroups().get(groupNumber).getLectureDates()) {
                                attendanceDataModel.add(new AttendanceDataModel(date, false));
                            }
                            studentCoursesDataModel.setAttendance(attendanceDataModel);
                            studentCoursesList.add(studentCoursesDataModel);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return studentCoursesList;
    }


}