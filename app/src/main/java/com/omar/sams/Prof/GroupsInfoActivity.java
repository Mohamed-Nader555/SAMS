package com.omar.sams.Prof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.GroupDataModel;
import com.omar.sams.R;
import com.omar.sams.Utils.CustomProgress;

public class GroupsInfoActivity extends AppCompatActivity {


    TextView groupCourseNameText, groupNumberText, groupTimePlaceText;
    private Button gradesBtn, saveNotesBtn;
    ImageView back_btn;
    EditText notesTextFiled;

    private CustomProgress mCustomProgress = CustomProgress.getInstance();
    private FirebaseAuth mAuth;
    private String currentUserId;
    private DatabaseReference mCoursesRef;
    CourseDataModel courseData;
    GroupDataModel groupData;
    String groupName;
    int groupNumber;
    String courseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_info);

        initViews();

    }

    private void initViews() {


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses");

        courseData = new CourseDataModel();
        groupData = new GroupDataModel();

        Intent i = getIntent();
        groupName = i.getStringExtra("groupName");
        String groupNameToInt = String.valueOf(groupName.charAt(groupName.length() - 1));
        groupNumber = Integer.parseInt(groupNameToInt) - 1;

        courseID = i.getStringExtra("courseID");

        groupCourseNameText = findViewById(R.id.groupCourseNameText);
        groupNumberText = findViewById(R.id.groupNumberText);
        groupTimePlaceText = findViewById(R.id.groupTimePlaceText);
        gradesBtn = findViewById(R.id.gradesBtn);
        saveNotesBtn = findViewById(R.id.saveNotesBtn);
        notesTextFiled = findViewById(R.id.notesTextFiled);
        back_btn = findViewById(R.id.back_btn);


        gradesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentsListIntent = new Intent(GroupsInfoActivity.this, GradesActivity.class);
                studentsListIntent.putExtra("groupNumber", String.valueOf(groupNumber));
                studentsListIntent.putExtra("courseId",courseID);
                startActivity(studentsListIntent);
            }
        });

        saveNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCustomProgress.showProgress(this, "Loading...", false);

        getCourseData(courseID);

        mCustomProgress.hideProgress();

    }


    private void getCourseData(String courseId) {

        mCoursesRef.child(courseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    CourseDataModel course = snapshot.getValue(CourseDataModel.class);
                    groupCourseNameText.setText(course.getName());
                    groupData = course.getGroups().get(groupNumber);
                    groupNumberText.setText(groupName);
                    groupTimePlaceText.setText(groupData.getTime() + " | " + groupData.getRoom());
                    notesTextFiled.setText(course.getNotes());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void saveNotes() {
        mCoursesRef.child(courseID).child("notes").setValue(notesTextFiled.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(GroupsInfoActivity.this, "Notes Saved Successfully", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(GroupsInfoActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}