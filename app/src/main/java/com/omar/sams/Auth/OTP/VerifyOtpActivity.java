package com.omar.sams.Auth.OTP;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.omar.sams.Hello.HelloActivity;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;

import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends AppCompatActivity {


    EditText mCode1, mCode2, mCode3, mCode4, mCode5, mCode6;
    TextView mResendCode, mMobileText;
    Button mVerifyBtn;
    String verificationId;
    ProgressBar mProgressBar;
    DatabaseReference mUsersRef;
    FirebaseAuth mAuth;
    String TAG = "VerifyOtpActivity";
    String phoneNumber;
    UserDataModel userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        initViews();

    }


    private void initViews() {
        phoneNumber = getIntent().getStringExtra("mobile_number");
        verificationId = getIntent().getStringExtra("verificationId");

        mCode1 = findViewById(R.id.code_1);
        mCode2 = findViewById(R.id.code_2);
        mCode3 = findViewById(R.id.code_3);
        mCode4 = findViewById(R.id.code_4);
        mCode5 = findViewById(R.id.code_5);
        mCode6 = findViewById(R.id.code_6);

        mAuth = FirebaseAuth.getInstance();
        mProgressBar = findViewById(R.id.verify_progress_bar);
        mMobileText = findViewById(R.id.phone_number_text);
        mMobileText.setText(String.format("+2 %s", phoneNumber));
        mResendCode = findViewById(R.id.resend_code_again_tv);
        mVerifyBtn = findViewById(R.id.verify_otp_btn);


        setOTPInputs();


        mVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode();
            }
        });


        mResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSendCode();
            }
        });


    }

    private void reSendCode() {


        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(VerifyOtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String newVerificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                verificationId = newVerificationID;
                Toast.makeText(VerifyOtpActivity.this, "Code Sent Again", Toast.LENGTH_SHORT).show();
            }
        };


        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber("+2" + getIntent().getStringExtra("mobile_number")).setTimeout(60L, TimeUnit.SECONDS).setActivity(VerifyOtpActivity.this).setCallbacks(mCallbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    private void verifyCode() {

        if (mCode1.getText().toString().trim().isEmpty() || mCode2.getText().toString().trim().isEmpty() || mCode3.getText().toString().trim().isEmpty() || mCode4.getText().toString().trim().isEmpty() || mCode5.getText().toString().trim().isEmpty() || mCode6.getText().toString().trim().isEmpty()) {
            Toast.makeText(VerifyOtpActivity.this, "Please Enter Valid Code", Toast.LENGTH_SHORT).show();
            return;
        }

        String code = mCode1.getText().toString() + mCode2.getText().toString() + mCode3.getText().toString() + mCode4.getText().toString() + mCode5.getText().toString() + mCode6.getText().toString();

        if (verificationId != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            mVerifyBtn.setVisibility(View.GONE);
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(phoneAuthCredential);
        }
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
                    Intent intent = new Intent(VerifyOtpActivity.this, CompleteUserDataActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Log.e("Sign in", "signInWithCredential:failure", task.getException());
                    Toast.makeText(VerifyOtpActivity.this, "The Verification Code is Invalid", Toast.LENGTH_SHORT).show();
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                    }
                }
            }
        });
    }


    public void setOTPInputs() {
        mCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) mCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) mCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) mCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) mCode5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) mCode6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}