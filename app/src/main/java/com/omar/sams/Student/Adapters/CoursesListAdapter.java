package com.omar.sams.Student.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.R;

import java.util.ArrayList;

public class CoursesListAdapter extends RecyclerView.Adapter<CoursesListAdapter.courseViewHolder> {

    Context context;
    ArrayList<CourseDataModel> CoursesItems;
    String mGroupName;
    CoursesListAdapter.OnCourseClickListener mListener;

    public CoursesListAdapter(Context context, ArrayList<CourseDataModel> coursesItems, String groupName) {
        this.context = context;
        CoursesItems = coursesItems;
        mGroupName = groupName;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_row, parent, false);

        courseViewHolder viewHolder = new courseViewHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {

        holder.mCourseName.setText(CoursesItems.get(position).getName());
        holder.mProfName.setText(CoursesItems.get(position).getProfName());

        int groupNumber = Integer.parseInt(mGroupName) - 1;

        holder.mCourseTime.setText(CoursesItems.get(position).getGroups().get(groupNumber).getTime());
        holder.mCourseRoom.setText(CoursesItems.get(position).getGroups().get(groupNumber).getRoom());

    }

    @Override
    public int getItemCount() {
        return CoursesItems.size();
    }


    public void setOnCourseClickListener(OnCourseClickListener listener) {
        mListener = listener;
    }


    public interface OnCourseClickListener {
        void OnCourseClicked(int position, String orderKey);
    }


    class courseViewHolder extends RecyclerView.ViewHolder {

        TextView mCourseName, mProfName, mCourseTime, mCourseRoom;

        public courseViewHolder(@NonNull View itemView, final OnCourseClickListener listener) {
            super(itemView);

            mCourseName = itemView.findViewById(R.id.courseNameText);
            mProfName = itemView.findViewById(R.id.doctorNameText);
            mCourseTime = itemView.findViewById(R.id.courseTimeText);
            mCourseRoom = itemView.findViewById(R.id.courseLocationText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnCourseClicked(position, CoursesItems.get(position).getCourseId());
                        }
                    }
                }
            });
        }
    }

}

