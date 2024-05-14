package com.omar.sams.Auth.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.Models.ProfessorDataModel;
import com.omar.sams.Models.StudentDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;

public class LoginEmailActivity extends AppCompatActivity {


    private final String TAG = "LoginActivity_TAG";
    Button loginBtn;
    private TextInputLayout emailInputLayout, passwordInputLayout;
    private EditText emailEditText, passwordEditText;
    private ProgressDialog mLoading;
    private Boolean emailAddressChecker;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        initViews();


    }


    private void initViews() {

        mAuth = FirebaseAuth.getInstance();
        mLoading = new ProgressDialog(this);


        emailEditText = findViewById(R.id.email_et);
        passwordEditText = findViewById(R.id.password_et);
        emailInputLayout = findViewById(R.id.email_input_et);
        passwordInputLayout = findViewById(R.id.password_input_et);

        loginBtn = findViewById(R.id.login_email_btn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) loginToTheAccount();
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
                    passwordInputLayout.setError("Short Password! Make it Shorter!");
                } else {
                    passwordInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Toast.makeText(Login.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        firebaseUser.reload();
                        emailAddressChecker = firebaseUser.isEmailVerified();
                        if (emailAddressChecker) {
                            Toast.makeText(LoginEmailActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                            GoogleSaveData();
                            startActivity(new Intent(LoginEmailActivity.this, HelloActivity.class));
                            finish();
                        } else {
                            SendEmailVerificationMessage();
                        }

                    } else {
                        Toast.makeText(LoginEmailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onComplete: login" + task.getException().getMessage());
                        mAuth.signOut();
                    }
                }
            });
        } else {
            Toast.makeText(LoginEmailActivity.this, "Account Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void GoogleSaveData() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            final String personGivenName = account.getGivenName();
            final String personFamilyName = account.getFamilyName();
            final String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            String fullNameEditText = personGivenName + " " + personFamilyName;
            final String currentUserID = mAuth.getCurrentUser().getUid();
            mUsersRef = FirebaseDatabase.getInstance().getReference("Users");

            final UserDataModel dataModel = new UserDataModel(currentUserID,
                    fullNameEditText,
                    emailEditText.getText().toString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    false,
                    new ProfessorDataModel(),
                    new StudentDataModel()
            );

            mUsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (!snapshot.exists()) {
                        mUsersRef.child(currentUserID).setValue(dataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mLoading.dismiss();
                                    Log.e(TAG, "onComplete: Done Saving Data for google account !");
                                } else
                                    Log.e(TAG, "onComplete: Error on Saving Data " + task.getException().toString());

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }


    private void loginToTheAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mLoading.setTitle("Logging In");
        mLoading.setMessage("Please Wait ...");
        mLoading.setCanceledOnTouchOutside(false);
        mLoading.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    VerifyEmailAddress();
                } else {
                    String messsage = task.getException().getMessage();
                    Toast.makeText(LoginEmailActivity.this, "Error Occurred: " + messsage, Toast.LENGTH_LONG).show();
                    mLoading.dismiss();
                }
            }
        });
    }


    boolean validate() {
        if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void VerifyEmailAddress() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        firebaseUser.reload();
        emailAddressChecker = firebaseUser.isEmailVerified();

        if (emailAddressChecker) {

            Toast.makeText(LoginEmailActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            mLoading.dismiss();
            startActivity(new Intent(LoginEmailActivity.this, HelloActivity.class));
            finish();

        } else {
            Toast.makeText(LoginEmailActivity.this, "Please Verfiy Your Account First ..", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            mLoading.dismiss();
        }


    }


    private void SendEmailVerificationMessage() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.reload();
                        Toast.makeText(LoginEmailActivity.this, "Check your email and verify your email.. ", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(LoginEmailActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent x = new Intent(LoginEmailActivity.this, LoginOptionsActivity.class);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(x);
        finish();
    }

}