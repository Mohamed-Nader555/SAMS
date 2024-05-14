package com.omar.sams.Auth.OTP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.Models.ProfessorDataModel;
import com.omar.sams.Models.StudentDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;

public class CompleteUserDataActivity extends AppCompatActivity {

    private final String TAG = "CompleteUserDataActivity";
    ProgressDialog mLoading;
    String phoneNumber;
    private TextInputLayout fullNameInputLayout, emailInputLayout, semesterInputLayout;
    private EditText fullNameEditText, emailEditText, semesterEditText;
    private Button continueButton;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

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


        fullNameInputLayout = findViewById(R.id.full_name_input_et);
        emailInputLayout = findViewById(R.id.email_input_et);

        fullNameEditText = findViewById(R.id.full_name_et);
        emailEditText = findViewById(R.id.email_et);


        continueButton = findViewById(R.id.continue_btn);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccountUserData();
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
        UsersRef = FirebaseDatabase.getInstance().getReference("Users");

        final UserDataModel dataModel = new UserDataModel(currentUserID,
                fullNameEditText.getText().toString(),
                emailEditText.getText().toString(),
                phoneNumber,
                "",
                "",
                semesterEditText.getText().toString(),
                semesterEditText.getText().toString(),
                false,
                new ProfessorDataModel(),
                new StudentDataModel()
        );

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
        } else if (semesterEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Semester", Toast.LENGTH_SHORT).show();
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


}