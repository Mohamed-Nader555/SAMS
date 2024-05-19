package com.omar.sams.Prof;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Models.StudentCoursesDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;
import com.omar.sams.Utils.CustomProgress;

public class InsertGradesActivity extends AppCompatActivity {

    TextView termNameText, gradesGroupNoText, gradesStudentNameText;
    EditText midtermGradeET, finalGradeET, classWorkGradeET, totalGradesET;
    TextInputLayout midtermGradeInputLayout, finalGradeInputLayout, classWorkGradeLayout, totalGradesInputLayout;
    ImageView midtermGradeBtn, finalGradeBtn, classWorkGradeBtn, back_btn;
    Button saveGradesBtn;

    private CustomProgress mCustomProgress = CustomProgress.getInstance();

    UserDataModel userData;
    private DatabaseReference mUserRef;
    String userID;
    String courseID;
    int courseIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_grades);

        initViews();

    }

    private void initViews() {

        Intent i = getIntent();
        userID = i.getStringExtra("userId");
        courseID = i.getStringExtra("courseId");
        userData = new UserDataModel();
        mUserRef = FirebaseDatabase.getInstance().getReference("Users").child(userID);


        termNameText = findViewById(R.id.termNameText);
        gradesGroupNoText = findViewById(R.id.gradesGroupNoText);
        gradesStudentNameText = findViewById(R.id.gradesStudentNameText);

        midtermGradeET = findViewById(R.id.midtermGradeET);
        finalGradeET = findViewById(R.id.finalGradeET);
        classWorkGradeET = findViewById(R.id.classWorkGradeET);
        totalGradesET = findViewById(R.id.totalGradesET);

        midtermGradeInputLayout = findViewById(R.id.midtermGradeInputLayout);
        finalGradeInputLayout = findViewById(R.id.finalGradeInputLayout);
        classWorkGradeLayout = findViewById(R.id.classWorkGradeLayout);
        totalGradesInputLayout = findViewById(R.id.totalGradesInputLayout);

        midtermGradeBtn = findViewById(R.id.midtermGradeBtn);
        finalGradeBtn = findViewById(R.id.finalGradeBtn);
        classWorkGradeBtn = findViewById(R.id.classWorkGradeBtn);

        saveGradesBtn = findViewById(R.id.saveGradesBtn);

        getData();


        classWorkGradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableAndFocusEditText(classWorkGradeET);
            }
        });

        finalGradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableAndFocusEditText(finalGradeET);

            }
        });

        midtermGradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableAndFocusEditText(midtermGradeET);

            }
        });

        saveGradesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGradesData();
            }
        });

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        handelTextFields();

    }


    private void getData() {
        mCustomProgress.showProgress(this, "Loading...", false);

        mUserRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {


                    userData = snapshot.getValue(UserDataModel.class);
                    gradesStudentNameText.setText(userData.getFullName());
                    termNameText.setText(userData.getSemester());
                    gradesGroupNoText.setText(userData.getGroup());

                    midtermGradeET.setEnabled(false);
                    classWorkGradeET.setEnabled(false);
                    finalGradeET.setEnabled(false);
                    totalGradesET.setEnabled(false);

                    for (StudentCoursesDataModel studentCourse : userData.getStudentCourses()) {

                            Log.e("InsertGrades", "onDataChange: " + courseID );
                        if (studentCourse.getCourseId().equals(courseID)) {
                            Log.e("InsertGrades", "onDataChange: " + studentCourse.getCourseId() );
                            Log.e("InsertGrades", "onDataChange: grade : " + studentCourse.getMidtermGrade() );
                            midtermGradeET.setText(studentCourse.getMidtermGrade());
                            classWorkGradeET.setText(studentCourse.getClasswork());
                            finalGradeET.setText(studentCourse.getFinalGrade());
                            totalGradesET.setText(studentCourse.getTotal());
                            courseIndex = userData.getStudentCourses().indexOf(studentCourse);
                            Log.e("InsertGrades", "onDataChange: index : " + courseIndex );
                        }
                    }


                }

                mCustomProgress.hideProgress();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void handelTextFields() {

        finalGradeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (!input.isEmpty()) {
                    try {
                        int value = Integer.parseInt(input);
                        if (value < 0 || value > 40) {
                            finalGradeInputLayout.setError("Number must be between 0 and 40");
                        } else {
                            finalGradeInputLayout.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        finalGradeInputLayout.setError("Invalid number");
                    }
                } else {
                    finalGradeInputLayout.setError(null);
                }
                updateTotalGrade();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        midtermGradeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (!input.isEmpty()) {
                    try {
                        int value = Integer.parseInt(input);
                        if (value < 0 || value > 30) {
                            midtermGradeInputLayout.setError("Number must be between 0 and 30");
                        } else {
                            midtermGradeInputLayout.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        midtermGradeInputLayout.setError("Invalid number");
                    }
                } else {
                    midtermGradeInputLayout.setError(null);
                }
                updateTotalGrade();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        classWorkGradeET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = String.valueOf(s);
                if (!input.isEmpty()) {
                    try {
                        int value = Integer.parseInt(input);
                        if (value < 0 || value > 30) {
                            classWorkGradeLayout.setError("Number must be between 0 and 30");
                        } else {
                            classWorkGradeLayout.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        classWorkGradeLayout.setError("Invalid number");
                    }
                } else {
                    classWorkGradeLayout.setError(null);
                }
                updateTotalGrade();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void updateTotalGrade() {
        int midtermGrade = 0, finalGrade = 0, classWorkGrade = 0;

        if (!midtermGradeET.getText().toString().isEmpty()) {
            midtermGrade = Integer.parseInt(midtermGradeET.getText().toString());
        }
        if (!finalGradeET.getText().toString().isEmpty()) {
            finalGrade = Integer.parseInt(finalGradeET.getText().toString());
        }
        if (!classWorkGradeET.getText().toString().isEmpty()) {
            classWorkGrade = Integer.parseInt(classWorkGradeET.getText().toString());
        }

        int totalGrade = midtermGrade + finalGrade + classWorkGrade;
        totalGradesET.setText(String.valueOf(totalGrade));
    }



    private void saveGradesData() {

        mUserRef.child("studentCourses").child(String.valueOf(courseIndex))
                .child("midtermGrade").setValue(midtermGradeET.getText().toString());
        mUserRef.child("studentCourses").child(String.valueOf(courseIndex))
                .child("classwork").setValue(classWorkGradeET.getText().toString());
        mUserRef.child("studentCourses").child(String.valueOf(courseIndex))
                .child("finalGrade").setValue(finalGradeET.getText().toString());


        mUserRef.child("studentCourses").child(String.valueOf(courseIndex))
                .child("gradeAlpha").setValue(getGradeAlpha());

        mUserRef.child("studentCourses").child(String.valueOf(courseIndex))
                .child("total").setValue(totalGradesET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(InsertGradesActivity.this, "Grades Saved Successfully", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(InsertGradesActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getGradeAlpha() {
        int grade;
        try {
            grade = Integer.parseInt(totalGradesET.getText().toString());
        } catch (NumberFormatException e) {
            return "Invalid Grade";
        }

        String alpha;
        if (grade >= 95) {
            alpha = "A+";
        } else if (grade >= 90) {
            alpha = "A";
        } else if (grade >= 85) {
            alpha = "B+";
        } else if (grade >= 80) {
            alpha = "B";
        }else if (grade >= 78) {
            alpha = "C+";
        } else if (grade >= 65) {
            alpha = "C";
        }else if (grade >= 58) {
            alpha = "D+";
        } else if (grade >= 50) {
            alpha = "D";
        } else {
            alpha = "F";
        }

        return alpha;
    }


    private void enableAndFocusEditText(EditText editText) {
        editText.setEnabled(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

}