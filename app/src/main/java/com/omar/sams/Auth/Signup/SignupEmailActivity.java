package com.omar.sams.Auth.Signup;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Auth.Login.LoginEmailActivity;
import com.omar.sams.Models.AttendanceDataModel;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.StudentCoursesDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;
import com.omar.sams.Utils.AESCrypt;

import java.util.ArrayList;
import java.util.Date;

public class SignupEmailActivity extends AppCompatActivity {


    private final String TAG = "SignupActivity_TAG";
    ProgressDialog mLoading;
    private TextInputLayout fullNameInputLayout, emailInputLayout, passwordInputLayout;
    private EditText fullNameEditText, emailEditText, passwordEditText, repeatPasswordEditText;
    private Spinner semesterSpinner, groupSpinner;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private String currentUserID;
    private DatabaseReference mCoursesRef;
    String groupSelected;
    String semesterSelected;
    ArrayList<StudentCoursesDataModel> coursesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);


        initViews();


    }


    private void initViews() {

        mAuth = FirebaseAuth.getInstance();
        mLoading = new ProgressDialog(this);
        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses");

        fullNameInputLayout = findViewById(R.id.current_password_input_et);
        emailInputLayout = findViewById(R.id.new_password_input_et);
        passwordInputLayout = findViewById(R.id.password_input_et);

        fullNameEditText = findViewById(R.id.full_name_et);
        emailEditText = findViewById(R.id.email_et);
        passwordEditText = findViewById(R.id.password_et);
        repeatPasswordEditText = findViewById(R.id.repeat_password_et);

        semesterSpinner = findViewById(R.id.semester_input_spinner);
        groupSpinner = findViewById(R.id.group_input_spinner);


        initGroupSpinner();
        initSemesterSpinner();


        signupButton = findViewById(R.id.signup_btn);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    semesterSelected = semesterSpinner.getSelectedItem().toString();
                    groupSelected = groupSpinner.getSelectedItem().toString();
                    coursesList = initStudentCourses(semesterSelected, groupSelected);
                    createNewAccount();
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

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (input.length() < 8) {
                    passwordInputLayout.setError("Short Password! Make it Longer!");
                } else if (input.length() > 10) {
                    passwordInputLayout.setError("Long Password! Make it Shorter!");
                } else {
                    passwordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        repeatPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (input.length() < 8) {
                    repeatPasswordEditText.setError("Short Password! Make it Longer!");
                } else if (input.length() > 10) {
                    repeatPasswordEditText.setError("Long Password! Make it Shorter!");
                } else {
                    repeatPasswordEditText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void saveAccountUserData() {
        String password;
        try {
            password = AESCrypt.encrypt(passwordEditText.getText().toString());
        } catch (Exception e) {
            password = passwordEditText.getText().toString();
        }


        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference("Users");



        final UserDataModel dataModel = new UserDataModel(currentUserID, fullNameEditText.getText().toString(), emailEditText.getText().toString(), "", password, "", semesterSelected, groupSelected, coursesList);

        UsersRef.child(currentUserID).setValue(dataModel).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignupEmailActivity.this, "Data Saved Successfully", Toast.LENGTH_LONG).show();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(SignupEmailActivity.this, "Error Occurred :" + message, Toast.LENGTH_LONG).show();
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
        } else if (passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            repeatPasswordEditText.setError("Password Don't Match");
            return false;
        } else if (repeatPasswordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Repeat Your Password", Toast.LENGTH_SHORT).show();
            repeatPasswordEditText.setError("Please Repeat You Password");
            return false;
        } else if (!passwordEditText.getText().toString().equals(repeatPasswordEditText.getText().toString())) {
            repeatPasswordEditText.setError("Password Don't Match");
            return false;
        } else if (semesterSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(SignupEmailActivity.this, "Please Select your Semester!", Toast.LENGTH_LONG).show();
            return false;
        } else if (groupSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(SignupEmailActivity.this, "Please Select your Group!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


    private void createNewAccount() {

        mLoading.setTitle("Creating New Account");
        mLoading.setMessage("Please Wait, while we are creating your new Account");
        mLoading.show();
        mLoading.setCanceledOnTouchOutside(true);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    SendEmailVerificationMessage();
                    saveAccountUserData();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(SignupEmailActivity.this, "Error Occurred: " + message, Toast.LENGTH_LONG).show();
                    mLoading.dismiss();
                }


            }
        });
    }

    private void SendEmailVerificationMessage() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.reload();
                        Toast.makeText(SignupEmailActivity.this, "Check your email and verify your email.. ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupEmailActivity.this, LoginEmailActivity.class));
                        mAuth.signOut();
                        finish();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(SignupEmailActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent x = new Intent(SignupEmailActivity.this, SignupOptionsActivity.class);
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


        ArrayAdapter semesterAdapter = new ArrayAdapter(SignupEmailActivity.this, android.R.layout.simple_spinner_item, semesters);
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


        ArrayAdapter groupAdapter = new ArrayAdapter(SignupEmailActivity.this, android.R.layout.simple_spinner_item, groups);
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