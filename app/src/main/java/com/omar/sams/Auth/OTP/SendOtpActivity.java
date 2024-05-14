package com.omar.sams.Auth.OTP;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Auth.Auth.AuthActivity;
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;

import java.util.concurrent.TimeUnit;

public class SendOtpActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    Button mSendCodeBtn;
    FirebaseAuth mAuth;
    DatabaseReference mUsersRef;
    UserDataModel userData;
    private EditText mInputMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        initViews();

    }

    private void initViews() {
        mAuth = FirebaseAuth.getInstance();

        mInputMobile = findViewById(R.id.phone_number_et);
        mSendCodeBtn = findViewById(R.id.send_code_phone_btn);
        mProgressBar = findViewById(R.id.send_progress_bar);

        sendCode();
    }

    private void sendCode() {

        mSendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SendOtpActivity.this, "Please Enter Your Phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                mSendCodeBtn.setVisibility(View.GONE);


                PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        mProgressBar.setVisibility(View.GONE);
                        mSendCodeBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(SendOtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        mProgressBar.setVisibility(View.GONE);
                        mSendCodeBtn.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(SendOtpActivity.this, VerifyOtpActivity.class);
                        intent.putExtra("mobile_number", mInputMobile.getText().toString());
                        intent.putExtra("verificationId", verificationId);
                        startActivity(intent);
                    }
                };


                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber("+2" + mInputMobile.getText().toString()).setTimeout(60L, TimeUnit.SECONDS).setActivity(SendOtpActivity.this).setCallbacks(mCallbacks).build();
                PhoneAuthProvider.verifyPhoneNumber(options);


            }
        });
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mProgressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Log.d("Sign in", "signInWithCredential:success");


                    FirebaseUser user = task.getResult().getUser();
                    final String currentUserID = user.getUid();
                    mUsersRef = FirebaseDatabase.getInstance().getReference("Users");

                    mUsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists())
                                userData = snapshot.getValue(UserDataModel.class);

                            if (userData.getFullName().isEmpty() || userData.getEmail().isEmpty()) {
                                Intent intent = new Intent(SendOtpActivity.this, CompleteUserDataActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else if (!userData.getFullName().isEmpty() && !userData.getEmail().isEmpty()) {
                                Intent intent = new Intent(SendOtpActivity.this, HelloActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Log.e("Sign in", "signInWithCredential:failure", task.getException());
                    Toast.makeText(SendOtpActivity.this, "The Verification Code is Invalid", Toast.LENGTH_SHORT).show();
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                    }
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent x = new Intent(SendOtpActivity.this, AuthActivity.class);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        x.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(x);
        finish();
    }

}