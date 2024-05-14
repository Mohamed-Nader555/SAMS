package com.omar.sams.Auth.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.database.annotations.Nullable;
import com.omar.sams.Auth.Auth.AuthActivity;
import com.omar.sams.Auth.OTP.SendOtpActivity;
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.Models.ProfessorDataModel;
import com.omar.sams.Models.StudentDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;
import com.omar.sams.Utils.CustomProgress;

public class LoginOptionsActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private final CustomProgress mCustomProgress = CustomProgress.getInstance();
    String TAG = "LoginOptionsActivity";
    Button loginEmailBtn, loginPhoneBtn, loginGoogle;
    private ProgressDialog mLoading;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersRef;
    private GoogleSignInClient mGoogleSignInClient;
    private Boolean emailAddressChecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("1098999832367-idvru8bimtne8i399thdvoei7ruea427.apps.googleusercontent.com").requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        initViews();


    }


    private void initViews() {

        mAuth = FirebaseAuth.getInstance();
        mLoading = new ProgressDialog(this);

        loginEmailBtn = findViewById(R.id.login_email_btn);
        loginPhoneBtn = findViewById(R.id.login_phone_btn);
        loginGoogle = findViewById(R.id.login_google_btn);


        loginEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginOptionsActivity.this, LoginEmailActivity.class));
                finish();
            }
        });


        loginPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginOptionsActivity.this, SendOtpActivity.class));
                finish();
            }
        });

        loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }


    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            mCustomProgress.showProgress(this, "Loading...", false);
            //Toast.makeText(Login.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            Toast.makeText(LoginOptionsActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "handleSignInResult: " + e.getMessage());
            mCustomProgress.hideProgress();
            FirebaseGoogleAuth(null);
        }
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
                            Toast.makeText(LoginOptionsActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();
                            GoogleSaveData();
                            startActivity(new Intent(LoginOptionsActivity.this, HelloActivity.class));
                            finish();
                        } else {
                            SendEmailVerificationMessage();
                        }

                    } else {
                        Toast.makeText(LoginOptionsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onComplete: login" + task.getException().getMessage());
                        mAuth.signOut();
                    }
                }
            });
        } else {
            Toast.makeText(LoginOptionsActivity.this, "Account Login failed", Toast.LENGTH_SHORT).show();
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
                    personEmail,
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


    private void SendEmailVerificationMessage() {
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        firebaseUser.reload();
                        Toast.makeText(LoginOptionsActivity.this, "Check your email and verify your email.. ", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(LoginOptionsActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent x = new Intent(LoginOptionsActivity.this, AuthActivity.class);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(x);
        finish();
    }


}