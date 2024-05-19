package com.omar.sams.Common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Auth.Auth.AuthActivity;
import com.omar.sams.Auth.Signup.SignupEmailActivity;
import com.omar.sams.Models.ProfessorDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;
import com.omar.sams.Student.StudentDashboardActivity;
import com.omar.sams.Utils.CustomProgress;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    //get the provider, so if it phoneNumber or gmail, hide the change password button
    //firebase.auth().currentUser.providerData[0].providerId

    final String TAG = "ProfileActivity";
    String currentUserID;
    FirebaseUser user;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;
    UserDataModel userData;
    ProfessorDataModel profDataModel;
    GoogleSignInClient mGoogleSignInClient;


    private CustomProgress mCustomProgress = CustomProgress.getInstance();

    private TextView studentNameProfileText, studentYearProfileText, groupTextView, semesterTextView;
    private Spinner semesterSpinner, groupSpinner;
    private EditText fullNameEditText, emailEditText;
    Button changePasswordBtn, logoutBtn;
    ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initViews();
        checkProvider();
        getData();

    }


    private void initViews() {


        userData = new UserDataModel();
        profDataModel = new ProfessorDataModel();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        currentUserID = user.getUid();
        mUsersRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID);


        fullNameEditText = findViewById(R.id.full_name_et);
        emailEditText = findViewById(R.id.email_et);
        fullNameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        studentNameProfileText = findViewById(R.id.studentNameProfileText);
        studentYearProfileText = findViewById(R.id.studentYearProfileText);
        logoutBtn = findViewById(R.id.logoutBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        semesterTextView = findViewById(R.id.semesterTextView);
        groupTextView = findViewById(R.id.groupTextView);
        back_btn = findViewById(R.id.back_btn);


        semesterSpinner = findViewById(R.id.semester_input_spinner);
        groupSpinner = findViewById(R.id.group_input_spinner);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        initGroupSpinner();
        initSemesterSpinner();


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }

    private void checkProvider() {
        boolean isEmailProvider = false;

        for (UserInfo userInfo : user.getProviderData()) {
            String providerId = userInfo.getProviderId();
            if (providerId.equals("password")) {
                isEmailProvider = true;
                break;
            }
        }

        if (isEmailProvider) {
            changePasswordBtn.setVisibility(View.VISIBLE);
        } else {
            changePasswordBtn.setVisibility(View.GONE);
        }
    }

    private void logout() {
        mGoogleSignInClient.signOut();
        mAuth.signOut();
        Intent userLogout = new Intent(ProfileActivity.this, AuthActivity.class);
        userLogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        userLogout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(userLogout);
        finish();
    }


    private void getData() {
        mCustomProgress.showProgress(this, "Loading...", false);

        mUsersRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    String fullName = "";
                    String email = "";

                    if(snapshot.child("profId").exists()){
                        profDataModel =  snapshot.getValue(ProfessorDataModel.class);
                        fullName = profDataModel.getName();
                        email = profDataModel.getEmail();
                        semesterSpinner.setVisibility(View.GONE);
                        groupSpinner.setVisibility(View.GONE);


                        studentNameProfileText.setText(fullName);
                        studentYearProfileText.setVisibility(View.GONE);
                        groupTextView.setVisibility(View.GONE);
                        semesterTextView.setVisibility(View.GONE);

                    }else{
                        userData = snapshot.getValue(UserDataModel.class);
                        fullName = userData.getFullName();
                        email = userData.getEmail();
                        semesterSpinner.setVisibility(View.VISIBLE);
                        groupSpinner.setVisibility(View.VISIBLE);

                        String group = userData.getGroup();
                        String semester = userData.getSemester();

                        switch (group){
                            case "1":
                                groupSpinner.setSelection(1);
                                break;
                            case "2":
                                groupSpinner.setSelection(2);
                                break;
                            case "3":
                                groupSpinner.setSelection(3);
                                break;
                            case "4":
                                groupSpinner.setSelection(4);
                                break;
                        }

                        switch (semester){
                            case "Third Term":
                                semesterSpinner.setSelection(1);
                                break;
                            case "Fourth Term":
                                semesterSpinner.setSelection(2);
                                break;
                        }

                        studentNameProfileText.setText(fullName);
                        studentYearProfileText.setText(semester + " | Group " + group);
                        semesterSpinner.setEnabled(false);
                        groupSpinner.setEnabled(false);
                    }

                    fullNameEditText.setText(fullName);
                    emailEditText.setText(email);

                }

                mCustomProgress.hideProgress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initGroupSpinner() {
        ArrayList<String> groups = new ArrayList<>();
        groups.add("Select Your Group ...");
        groups.add("1");
        groups.add("2");
        groups.add("3");
        groups.add("4");


        ArrayAdapter groupAdapter = new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupAdapter);
        groupSpinner.setSelection(0);
    }


    private void initSemesterSpinner() {
        ArrayList<String> semesters = new ArrayList<>();
        semesters.add("Select Current Semester ...");
        semesters.add("Third Term");
        semesters.add("Fourth Term");


        ArrayAdapter semesterAdapter = new ArrayAdapter(ProfileActivity.this, android.R.layout.simple_spinner_item, semesters);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);
        semesterSpinner.setSelection(0);
    }


}