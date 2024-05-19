package com.omar.sams.Student.Adapters;

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

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.myStudentAttendanceViewHolder> {

    Context context;
    ArrayList<AttendanceDataModel> studentsAttendanceList;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public StudentAttendanceAdapter(Context context, ArrayList<AttendanceDataModel> studentsAttendanceList) {
        this.context = context;
        this.studentsAttendanceList = studentsAttendanceList;
    }

    @NonNull
    @Override
    public StudentAttendanceAdapter.myStudentAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendace_row, parent, false);
        StudentAttendanceAdapter.myStudentAttendanceViewHolder viewHolder = new StudentAttendanceAdapter.myStudentAttendanceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAttendanceAdapter.myStudentAttendanceViewHolder holder, int position) {
        AttendanceDataModel attendance = studentsAttendanceList.get(position);

        holder.numberText.setText(String.valueOf(position + 1) );
        holder.lectureDateText.setText(formatDate(attendance.getDate()));

        boolean isPresent = attendance.getPresent();
        if (isPresent) {
            holder.presentRadioBtn.setChecked(true);
            holder.absentRadioBtn.setChecked(false);
        } else {
            holder.presentRadioBtn.setChecked(false);
            holder.absentRadioBtn.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return studentsAttendanceList.size();
    }

    public class myStudentAttendanceViewHolder extends RecyclerView.ViewHolder {

        TextView numberText, lectureDateText;
        private RadioGroup radioGroup;
        private MaterialRadioButton presentRadioBtn, absentRadioBtn;

        public myStudentAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            numberText = itemView.findViewById(R.id.numberText);
            lectureDateText = itemView.findViewById(R.id.lectureDateText);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            presentRadioBtn = itemView.findViewById(R.id.presentRadioBtn);
            absentRadioBtn = itemView.findViewById(R.id.absentRadioBtn);
        }

    }

    public String formatDate(Date date) {
        return dateFormat.format(date);
    }

}