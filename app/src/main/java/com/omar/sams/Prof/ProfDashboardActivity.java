package com.omar.sams.Prof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.omar.sams.Common.ClubsActivity;
import com.omar.sams.Common.ContactScreenActivity;
import com.omar.sams.Common.ProfileActivity;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.GroupDataModel;
import com.omar.sams.Models.ProfessorDataModel;
import com.omar.sams.Prof.Adapters.GroupsListAdapter;
import com.omar.sams.R;
import com.omar.sams.Utils.CustomProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfDashboardActivity extends AppCompatActivity {


    private final String TAG = "StudentDashboardActivity_TAG";


    ImageView attendanceBtn, clubsBtn, contactBtn, profProfileImage;
    RecyclerView groupsRecyclerView;
    GroupsListAdapter groupsAdapter;
    TextView timeTextView, dayTextView, dateTextView, userNameTextView;

    private CustomProgress mCustomProgress = CustomProgress.getInstance();
    private FirebaseAuth mAuth;
    private String currentUserId;
    private DatabaseReference mUserRef;
    private DatabaseReference mCoursesRef;
    private ProfessorDataModel profData;
    ArrayList<GroupDataModel> mGroupsList;
    String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_dashboard);

        initView();

    }


    private void initView() {

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses");

        profData = new ProfessorDataModel();
        mGroupsList = new ArrayList<>();

        //Views
        attendanceBtn = findViewById(R.id.profAttendanceBtn);
        clubsBtn = findViewById(R.id.profClubsBtn);
        contactBtn = findViewById(R.id.profContactBtn);
        profProfileImage = findViewById(R.id.profDashboardImage);
        timeTextView = findViewById(R.id.profTimeTextView);
        dayTextView = findViewById(R.id.profDayTextView);
        dateTextView = findViewById(R.id.profDateTextView);
        userNameTextView = findViewById(R.id.profNameDashboardText);


        groupsRecyclerView = findViewById(R.id.profGroupsRV);
        groupsRecyclerView.setHasFixedSize(true);


        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent groupSelectIntent = new Intent(ProfDashboardActivity.this, ChooseGroupActivity.class);
                groupSelectIntent.putExtra("courseId", courseID);
                startActivity(groupSelectIntent);
            }
        });

        clubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfDashboardActivity.this, ClubsActivity.class));
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfDashboardActivity.this, ContactScreenActivity.class));
            }
        });


        profProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfDashboardActivity.this, ProfileActivity.class));
            }
        });

        setTimeAndDate();

        mCustomProgress.showProgress(this, "Loading...", false);


        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    profData = snapshot.getValue(ProfessorDataModel.class);
                    String[] nameParts = profData.getName().split(" ");
                    String firstName = nameParts[0];
                    userNameTextView.setText("Hi, Prof. " + firstName);
                    getAllGroupsData(profData.getCourseId());
                    courseID = profData.getCourseId();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getAllGroupsData(String courseID) {

        mCoursesRef.child(courseID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mGroupsList.clear();
                if (snapshot.exists()) {
                    CourseDataModel course = snapshot.getValue(CourseDataModel.class);
                    String courseName = course.getName();
                    if (snapshot.child("groups").exists()) {
                        for (DataSnapshot childSnap : snapshot.child("groups").getChildren()) {
                            GroupDataModel group = childSnap.getValue(GroupDataModel.class);
                            mGroupsList.add(group);
                        }
                    }

                    showGroups(mGroupsList, courseName, courseID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void showGroups(final ArrayList<GroupDataModel> Groups, String courseName, String courseID) {
        groupsAdapter = new GroupsListAdapter(ProfDashboardActivity.this, Groups, courseName);
        groupsAdapter.setOnGroupClickListener(new GroupsListAdapter.OnGroupClickListener() {
            @Override
            public void OnGroupClicked(int position, String groupName) {
                Intent courseInfoIntent = new Intent(ProfDashboardActivity.this, GroupsInfoActivity.class);
                courseInfoIntent.putExtra("groupName", groupName);
                courseInfoIntent.putExtra("courseID", courseID);
                startActivity(courseInfoIntent);
            }
        });
        groupsRecyclerView.setAdapter(groupsAdapter);
        groupsAdapter.notifyDataSetChanged();
        mCustomProgress.hideProgress();

    }


    private void setTimeAndDate() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String am_pm = calendar.get(Calendar.AM_PM) == Calendar.AM ? "am" : "pm";

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm", Locale.getDefault());
        String formattedTime = timeFormat.format(calendar.getTime());
        String currentTime = formattedTime + " " + am_pm;

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        String dayOfWeek = dayFormat.format(calendar.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());


        timeTextView.setText(currentTime);
        dayTextView.setText(dayOfWeek);
        dateTextView.setText(formattedDate);

    }


}