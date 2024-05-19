package com.omar.sams.Prof;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.Prof.Adapters.StudentNamesListAdapter;
import com.omar.sams.R;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {

    RecyclerView studentsRecyclerView;
    StudentNamesListAdapter studentsAdapter;
    private LinearLayoutManager mLayoutManager;
    ImageView back_btn;

    TextView noRegisteredTextView;


    String groupNumber;
    String courseID;

    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        initViews();

    }

    private void initViews() {

        Intent i = getIntent();
        groupNumber = i.getStringExtra("groupNumber");
        courseID = i.getStringExtra("courseId");

        mUserRef = FirebaseDatabase.getInstance().getReference("Users");
        noRegisteredTextView = findViewById(R.id.noRegisteredTextView);

        studentsRecyclerView = findViewById(R.id.studentGradesRV);
        studentsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(GradesActivity.this, RecyclerView.VERTICAL, false);
        studentsRecyclerView.setLayoutManager(mLayoutManager);
        getStudentsList();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void getStudentsList() {

        ArrayList<UserDataModel> studentsList = new ArrayList<>();

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentsList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnap : snapshot.getChildren()) {
                        if (!childSnap.child("profId").exists()) {
                            UserDataModel userData = childSnap.getValue(UserDataModel.class);
                            int intUserGroupNumber = Integer.parseInt(userData.getGroup()) - 1;
                            String userGroupNumber = String.valueOf(intUserGroupNumber);
                            if (userGroupNumber.equals(groupNumber)) {
                                Log.e("GradesActivity", "Inside IF: " + userData.toString());
                                studentsList.add(userData);
                            }
                        }
                    }
                }

                studentsAdapter = new StudentNamesListAdapter(GradesActivity.this, studentsList, courseID);
                studentsRecyclerView.setAdapter(studentsAdapter);
                studentsAdapter.notifyDataSetChanged();

                if (studentsList.size() == 0){
                    noRegisteredTextView.setVisibility(View.VISIBLE);
                }else{
                    noRegisteredTextView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}