package com.omar.sams.Auth.Signup;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.Nullable;
import com.omar.sams.Auth.Auth.AuthActivity;
import com.omar.sams.Auth.OTP.CompleteUserDataActivity;
import com.omar.sams.Auth.OTP.SendOtpActivity;
import com.omar.sams.R;
import com.omar.sams.Utils.CustomProgress;

public class SignupOptionsActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private final CustomProgress mCustomProgress = CustomProgress.getInstance();
    String TAG = "SignupOptionsActivity";
    Button signupEmailBtn, signupPhoneBtn, signupGoogle;
    private ProgressDialog mLoading;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersRef;
    private GoogleSignInClient mGoogleSignInClient;
    private Boolean emailAddressChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_options);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("1098999832367-idvru8bimtne8i399thdvoei7ruea427.apps.googleusercontent.com").requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        initViews();


    }


    private void initViews() {


        mAuth = FirebaseAuth.getInstance();
        mLoading = new ProgressDialog(this);


        signupEmailBtn = findViewById(R.id.signup_email_btn);
        signupPhoneBtn = findViewById(R.id.signup_phone_btn);
        signupGoogle = findViewById(R.id.signup_google_btn);


        signupEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupOptionsActivity.this, SignupEmailActivity.class));
                finish();
            }
        });


        signupPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupOptionsActivity.this, SendOtpActivity.class));
                finish();
            }
        });

        signupGoogle.setOnClickListener(new View.OnClickListener() {
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
            Toast.makeText(SignupOptionsActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(SignupOptionsActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                            mLoading.dismiss();


                            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                            if (account != null) {
                                final String personGivenName = account.getGivenName();
                                final String personFamilyName = account.getFamilyName();
                                final String personEmail = account.getEmail();
                                String fullName = personGivenName + " " + personFamilyName;

                                Intent completeActivity = new Intent(SignupOptionsActivity.this, CompleteUserDataActivity.class);
                                completeActivity.putExtra("isGoogle", true);
                                completeActivity.putExtra("userName", fullName);
                                completeActivity.putExtra("userEmail", personEmail);
                                startActivity(completeActivity);
                                finish();
                            } else {
                                Toast.makeText(SignupOptionsActivity.this, "There is an error While Sign with Google.", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            SendEmailVerificationMessage();
                        }

                    } else {
                        Toast.makeText(SignupOptionsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onComplete: login" + task.getException().getMessage());
                        mAuth.signOut();
                    }
                }
            });
        } else {
            Toast.makeText(SignupOptionsActivity.this, "Account Login failed", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SignupOptionsActivity.this, "Check your email and verify your email.. ", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(SignupOptionsActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent x = new Intent(SignupOptionsActivity.this, AuthActivity.class);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(x);
        finish();
    }

}