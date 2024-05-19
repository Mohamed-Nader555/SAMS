package com.omar.sams.Prof.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.sams.Models.UserDataModel;
import com.omar.sams.Prof.InsertAttendanceActivity;
import com.omar.sams.Prof.InsertGradesActivity;
import com.omar.sams.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LectureDatesListAdapter extends RecyclerView.Adapter<LectureDatesListAdapter.myLectureDatesViewHolder>{

    Context context;
    ArrayList<Date> datesList;
    String groupName;
    String courseID;
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public LectureDatesListAdapter(Context context, ArrayList<Date> datesList, String courseID, String groupName) {
        this.context = context;
        this.datesList = datesList;
        this.groupName = groupName;
        this.courseID = courseID;
    }

    @NonNull
    @Override
    public LectureDatesListAdapter.myLectureDatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_selection_row, parent, false);

        LectureDatesListAdapter.myLectureDatesViewHolder viewHolder = new LectureDatesListAdapter.myLectureDatesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LectureDatesListAdapter.myLectureDatesViewHolder holder, int position) {

        Date date = datesList.get(position);
        int newPos = position + 1;
        holder.lectureDateText.setText("Lecture " + newPos + " - " + formatDate(date) );
        holder.lectureDatesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentGradeIntent = new Intent(context, InsertAttendanceActivity.class);
                studentGradeIntent.putExtra("courseId", courseID);
                studentGradeIntent.putExtra("groupName", groupName);
                studentGradeIntent.putExtra("lectureDate", formatDate(date));
                context.startActivity(studentGradeIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datesList.size();
    }


    public class myLectureDatesViewHolder extends RecyclerView.ViewHolder {

        TextView lectureDateText;
        CardView lectureDatesCardView;

        public myLectureDatesViewHolder(@NonNull View itemView) {
            super(itemView);
            lectureDatesCardView = itemView.findViewById(R.id.lectureDatesCardView);
            lectureDateText = itemView.findViewById(R.id.lectureDateText);
        }

    }

    public String formatDate(Date date) {
        return dateFormat.format(date);
    }


}

