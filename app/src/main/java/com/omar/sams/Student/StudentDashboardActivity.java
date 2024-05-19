package com.omar.sams.Student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.omar.sams.Common.EContentActivity;
import com.omar.sams.Common.ProfileActivity;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;
import com.omar.sams.Student.Adapters.CoursesListAdapter;
import com.omar.sams.Utils.CustomProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class StudentDashboardActivity extends AppCompatActivity {


    private final String TAG = "StudentDashboardActivity_TAG";


    ImageView eContentBtn, clubsBtn, contactBtn, studentProfileImage;
    RecyclerView coursesRecyclerView;
    CoursesListAdapter coursesAdapter;
    TextView timeTextView, dayTextView, dateTextView, userNameTextView;

    private CustomProgress mCustomProgress = CustomProgress.getInstance();
    private FirebaseAuth mAuth;
    private String currentUserId;
    private DatabaseReference mUserRef;
    private DatabaseReference mCoursesRef;
    private UserDataModel userData;
    ArrayList<CourseDataModel> mCoursesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        initView();

    }

    private void initView() {

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
        mCoursesRef = FirebaseDatabase.getInstance().getReference("Courses");

        userData = new UserDataModel();
        mCoursesList = new ArrayList<>();

        //Views
        eContentBtn = findViewById(R.id.studentEcontentBtn);
        clubsBtn = findViewById(R.id.studentClubsBtn);
        contactBtn = findViewById(R.id.studentContactBtn);
        studentProfileImage = findViewById(R.id.studentProfileImage);
        timeTextView = findViewById(R.id.studentTimeText);
        dayTextView = findViewById(R.id.studentDayText);
        dateTextView = findViewById(R.id.studentDateText);
        userNameTextView = findViewById(R.id.studentDashboardNameText);


        coursesRecyclerView = findViewById(R.id.studentCoursesRV);
        coursesRecyclerView.setHasFixedSize(true);


        eContentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, EContentActivity.class));
            }
        });

        clubsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, ClubsActivity.class));
            }
        });

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, ContactScreenActivity.class));
            }
        });


        studentProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentDashboardActivity.this, ProfileActivity.class));
            }
        });

        setTimeAndDate();

        mCustomProgress.showProgress(this, "Loading...", false);


        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userData = snapshot.getValue(UserDataModel.class);
                    String[] nameParts = userData.getFullName().split(" ");
                    String firstName = nameParts[0];
                    userNameTextView.setText("Hi, " + firstName);
                    getAllCoursesData(userData.getSemester(), userData.getGroup());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getAllCoursesData(String userSemester, String userGroup) {

        mCoursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mCoursesList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot childSnap : snapshot.getChildren()) {
                        CourseDataModel course = childSnap.getValue(CourseDataModel.class);
                        Log.e(TAG, userSemester);
                        if (userData.getSemester().equals(course.getSemester())) {
                            mCoursesList.add(course);
                            Log.e(TAG, course.toString());
                        }
                    }

                    showCourses(mCoursesList, userGroup);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void showCourses(final ArrayList<CourseDataModel> Courses, String groupName) {
        coursesAdapter = new CoursesListAdapter(StudentDashboardActivity.this, Courses, groupName);
        coursesAdapter.setOnCourseClickListener(new CoursesListAdapter.OnCourseClickListener() {
            @Override
            public void OnCourseClicked(int position, String courseId) {
                Intent courseInfoIntent = new Intent(StudentDashboardActivity.this, CoursesInfoActivity.class);
                courseInfoIntent.putExtra("courseID", courseId);
                startActivity(courseInfoIntent);
            }
        });
        coursesRecyclerView.setAdapter(coursesAdapter);
        coursesAdapter.notifyDataSetChanged();
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