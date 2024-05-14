package com.omar.sams.Models.FillData;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.GroupDataModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddDataActivity extends AppCompatActivity {

    String TAG = "AddDataActivity";
    Date date = new Date();
    DatabaseReference mCoursesRef;
    String courseKey = "";
    String databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addCourses();
    }


    private void addCourses() {
        ArrayList<CourseDataModel> courses = new ArrayList<>();

        try {
            courses = fillCoursesData();
        } catch (ParseException e) {
            Log.e(TAG, "Error Occurred" + e.getMessage());

        }


        databaseRef = "Courses";
        mCoursesRef = FirebaseDatabase.getInstance().getReference(databaseRef);


        for (CourseDataModel courseDataModel : courses) {

            courseKey = mCoursesRef.push().getKey();
            mCoursesRef.child(courseKey).setValue(courseDataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.e(TAG, "Done Adding Course");
                    } else {
                        String message = task.getException().getMessage();
                        Log.e(TAG, "Error Occurred" + message);
                    }
                }
            });

        }


    }


    private ArrayList<CourseDataModel> fillCoursesData() throws ParseException {

        ArrayList<CourseDataModel> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");


        ArrayList<Date> lecDates = new ArrayList<>();
        lecDates.add(df.parse("11-01-2024"));
        lecDates.add(df.parse("18-01-2024"));
        lecDates.add(df.parse("24-01-2024"));
        lecDates.add(df.parse("30-01-2024"));
        lecDates.add(df.parse("05-02-2024"));
        lecDates.add(df.parse("12-02-2024"));


        ArrayList<GroupDataModel> groups = new ArrayList<>();
        groups.add(new GroupDataModel("Group 1", "Room 301", "9 - 11", "Wednesday", lecDates));
        groups.add(new GroupDataModel("Group 2", "Room 305", "11 - 1", "Wednesday", lecDates));
        groups.add(new GroupDataModel("Group 3", "Room 402", "1 - 3", "Wednesday", lecDates));
        groups.add(new GroupDataModel("Group 4", "Room 407", "9 - 11", "Sunday", lecDates));


        CourseDataModel course1 = new CourseDataModel();
        course1.setSemester("Third Term");
        course1.setName("Management Information System");
        course1.setProf("Heba Sabri");
        course1.setDriveLink("https://drive.google.com/drive/folders/1buEvZVlo-_A5eVgaqeelfV9n7zEmiNku?usp=sharing");
        course1.setDescription("MIS course dives into how businesses leverage technology to manage information and make data-driven choices.");
        course1.setNotes("");
        course1.setGroups(groups);


        list.add(course1);


        return list;

    }


}