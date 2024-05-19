package com.omar.sams.Prof.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.sams.Models.GroupDataModel;
import com.omar.sams.R;

import java.util.ArrayList;

public class GroupsListAdapter extends RecyclerView.Adapter<GroupsListAdapter.groupViewHolder> {

    Context context;
    ArrayList<GroupDataModel> GroupsItems;
    GroupsListAdapter.OnGroupClickListener mListener;
    String courseName;

    public GroupsListAdapter(Context context, ArrayList<GroupDataModel> groupsItems, String courseName) {
        this.context = context;
        GroupsItems = groupsItems;
        this.courseName = courseName;
    }

    @NonNull
    @Override
    public groupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_row, parent, false);

        groupViewHolder viewHolder = new groupViewHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull groupViewHolder holder, int position) {

        holder.mCourseName.setText(courseName);
        holder.mGroupName.setText(GroupsItems.get(position).getName());

        holder.mCourseTime.setText(GroupsItems.get(position).getTime());
        holder.mCourseRoom.setText(GroupsItems.get(position).getRoom());

    }

    @Override
    public int getItemCount() {
        return GroupsItems.size();
    }


    public void setOnGroupClickListener(OnGroupClickListener listener) {
        mListener = listener;
    }


    public interface OnGroupClickListener {
        void OnGroupClicked(int position, String groupName);
    }


    class groupViewHolder extends RecyclerView.ViewHolder {

        TextView mCourseName, mGroupName, mCourseTime, mCourseRoom;

        public groupViewHolder(@NonNull View itemView, final OnGroupClickListener listener) {
            super(itemView);

            mCourseName = itemView.findViewById(R.id.courseNameText);
            mGroupName = itemView.findViewById(R.id.doctorNameText);
            mCourseTime = itemView.findViewById(R.id.courseTimeText);
            mCourseRoom = itemView.findViewById(R.id.courseLocationText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnGroupClicked(position, GroupsItems.get(position).getName());
                        }
                    }
                }
            });
        }
    }

}

