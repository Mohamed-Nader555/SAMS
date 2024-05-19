package com.omar.sams.Prof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.GroupDataModel;
import com.omar.sams.Prof.Adapters.LectureDatesListAdapter;
import com.omar.sams.Prof.Adapters.StudentNamesListAdapter;
import com.omar.sams.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChooseGroupActivity extends AppCompatActivity {


    private DatabaseReference mCoursesRef;

    Spinner groupSpinner;

    RecyclerView lecturesRecyclerView;
    LectureDatesListAdapter lecturesAdapter;
    private LinearLayoutManager mLayoutManager;
    private DatabaseReference mUserRef;
    ImageView back_btn;

    String courseID;
    ArrayList<Date> lectureDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group_activity);

        initViews();


    }

    private void initViews() {

        Intent i = getIntent();
        courseID = i.getStringExtra("courseId");

        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses").child(courseID);

        groupSpinner = findViewById(R.id.spinnerDropDownList);
        lecturesRecyclerView = findViewById(R.id.lectureSelectionRV);
        lecturesRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ChooseGroupActivity.this, RecyclerView.VERTICAL, false);
        lecturesRecyclerView.setLayoutManager(mLayoutManager);
        lectureDates = new ArrayList<>();

        initGroupSpinner();


        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (groupSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(ChooseGroupActivity.this, "Please Select Group!", Toast.LENGTH_LONG).show();
                }else{
                    String groupName = groupSpinner.getSelectedItem().toString();
                    getLecturesList(groupName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected, if needed
            }
        });


        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void getLecturesList(String groupName) {


        mCoursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lectureDates.clear();
                if (snapshot.exists()) {
                    CourseDataModel course = snapshot.getValue(CourseDataModel.class);
                    if (snapshot.child("groups").exists()) {
                        for (DataSnapshot childSnap : snapshot.child("groups").getChildren()) {
                            GroupDataModel group = childSnap.getValue(GroupDataModel.class);
                            if (group.getName().equals(groupName)) {
                                lectureDates = group.getLectureDates();
                            }
                        }
                    }

                    lecturesAdapter = new LectureDatesListAdapter(ChooseGroupActivity.this, lectureDates, courseID, extractNumber(groupName));
                    lecturesRecyclerView.setAdapter(lecturesAdapter);
                    lecturesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initGroupSpinner() {
        ArrayList<String> groups = new ArrayList<>();
        groups.add("Select Your Group ...");
        groups.add("Group 1");
        groups.add("Group 2");
        groups.add("Group 3");
        groups.add("Group 4");


        ArrayAdapter groupAdapter = new ArrayAdapter(ChooseGroupActivity.this, android.R.layout.simple_spinner_item, groups);
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(groupAdapter);
        groupSpinner.setSelection(0);
    }

    private String extractNumber(String input) {
        String number = "";
        // Regular expression to find digits
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            number = matcher.group();
        }
        return number;
    }

}