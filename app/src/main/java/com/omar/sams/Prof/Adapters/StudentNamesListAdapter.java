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
import com.omar.sams.Prof.InsertGradesActivity;
import com.omar.sams.R;

import java.util.ArrayList;

public class StudentNamesListAdapter extends RecyclerView.Adapter<StudentNamesListAdapter.myStudentsViewHolder> {

    Context context;
    ArrayList<UserDataModel> studentsList;
    String courseID;

    public StudentNamesListAdapter(Context context, ArrayList<UserDataModel> studentsList, String courseID) {
        this.context = context;
        this.studentsList = studentsList;
        this.courseID = courseID;
    }

    @NonNull
    @Override
    public StudentNamesListAdapter.myStudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grades_row, parent, false);

        StudentNamesListAdapter.myStudentsViewHolder viewHolder = new StudentNamesListAdapter.myStudentsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentNamesListAdapter.myStudentsViewHolder holder, int position) {
        UserDataModel student = studentsList.get(position);
        int newPos = position + 1;
        holder.studentNameText.setText(newPos + " - "+ student.getFullName());
        holder.studentGradeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentGradeIntent = new Intent(context, InsertGradesActivity.class);
                studentGradeIntent.putExtra("userId", student.getUserId());
                studentGradeIntent.putExtra("courseId", courseID);
                context.startActivity(studentGradeIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class myStudentsViewHolder extends RecyclerView.ViewHolder {

        TextView studentNameText;
        CardView studentGradeCardView;

        public myStudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            studentGradeCardView = itemView.findViewById(R.id.studentGradeCardView);
            studentNameText = itemView.findViewById(R.id.studentNameText);
        }

    }
}
