package com.omar.sams.Prof.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omar.sams.Models.AttendanceDataModel;
import com.omar.sams.Models.StudentCoursesDataModel;
import com.omar.sams.Models.UserDataModel;
import com.omar.sams.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InsertStudentAttendanceListAdapter extends RecyclerView.Adapter<InsertStudentAttendanceListAdapter.myInsertAttendanceViewHolder> {

    Context context;
    ArrayList<UserDataModel> studentsList;
    private DatabaseReference mUserRef;
    String courseId, lectureDate;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    int courseIndex=0, attendanceIndex=0;
    public InsertStudentAttendanceListAdapter(Context context, ArrayList<UserDataModel> studentsList, String courseId, String lectureDate) {
        this.context = context;
        this.studentsList = studentsList;
        this.courseId = courseId;
        this.lectureDate = lectureDate;
        mUserRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    @NonNull
    @Override
    public InsertStudentAttendanceListAdapter.myInsertAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insert_attendance_row, parent, false);
        InsertStudentAttendanceListAdapter.myInsertAttendanceViewHolder viewHolder = new InsertStudentAttendanceListAdapter.myInsertAttendanceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InsertStudentAttendanceListAdapter.myInsertAttendanceViewHolder holder, int position) {
        UserDataModel student = studentsList.get(position);

        holder.attandanceStudentNameText.setText(student.getFullName());

        for (StudentCoursesDataModel course : student.getStudentCourses()) {
            if (course.getCourseId().equals(courseId)) {
                courseIndex = student.getStudentCourses().indexOf(course);
                for (AttendanceDataModel attendance : course.getAttendance()) {
                    if (formatDate(attendance.getDate()).equals(lectureDate)) {
                        attendanceIndex = course.getAttendance().indexOf(attendance);
                        boolean isPresent = attendance.getPresent();
                        if (isPresent) {
                            holder.insertPresentRadioBtn.setChecked(true);
                            holder.insertAbsentRadioBtn.setChecked(false);
                        } else {
                            holder.insertPresentRadioBtn.setChecked(false);
                            holder.insertAbsentRadioBtn.setChecked(true);
                        }
                    }
                }
            }
        }


        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.insertPresentRadioBtn) {
                    Log.e("InsertGrades", "onDataChange: user Id : " + student.getUserId() );
                    Log.e("InsertGrades", "onDataChange: courseIndex : " + courseIndex );
                    Log.e("InsertGrades", "onDataChange: attendanceIndex : " + attendanceIndex );
                    mUserRef.child(student.getUserId()).child("studentCourses")
                            .child(String.valueOf(courseIndex)).child("attendance")
                            .child(String.valueOf(attendanceIndex))
                            .child("present").setValue(true);
                } else if (checkedId == R.id.insertAbsentRadioBtn) {
                    mUserRef.child(student.getUserId()).child("studentCourses")
                            .child(String.valueOf(courseIndex)).child("attendance")
                            .child(String.valueOf(attendanceIndex))
                            .child("present").setValue(false);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class myInsertAttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView attandanceStudentNameText;
        private RadioGroup radioGroup;
        private MaterialRadioButton insertPresentRadioBtn, insertAbsentRadioBtn;

        public myInsertAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            attandanceStudentNameText = itemView.findViewById(R.id.attandanceStudentNameText);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            insertPresentRadioBtn = itemView.findViewById(R.id.insertPresentRadioBtn);
            insertAbsentRadioBtn = itemView.findViewById(R.id.insertAbsentRadioBtn);


        }

    }

    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

}