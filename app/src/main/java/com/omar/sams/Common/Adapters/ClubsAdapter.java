package com.omar.sams.Common.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.sams.Models.ClubsDataModel;
import com.omar.sams.R;

import java.util.ArrayList;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.myClubsAdapterViewHolder> {


    private Context context;
    private ArrayList<ClubsDataModel> clubsList;

    public ClubsAdapter(Context context, ArrayList<ClubsDataModel> clubsList) {
        this.context = context;
        this.clubsList = clubsList;
    }

    @NonNull
    @Override
    public ClubsAdapter.myClubsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clubs_row, parent, false);

        ClubsAdapter.myClubsAdapterViewHolder viewHolder = new ClubsAdapter.myClubsAdapterViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClubsAdapter.myClubsAdapterViewHolder holder, int position) {
        ClubsDataModel club = clubsList.get(position);
        holder.clubNameTextView.setText(club.getClubName());

        int imageResource = context.getResources().getIdentifier(club.getClubImg(), null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        holder.clubLogoImage.setImageDrawable(res);


        holder.moreInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(club.getClubLink()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return clubsList.size();
    }

    public class myClubsAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView clubLogoImage;
        TextView clubNameTextView;
        Button moreInfoBtn;


        public myClubsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            clubLogoImage = itemView.findViewById(R.id.clubLogoImage);
            clubNameTextView = itemView.findViewById(R.id.clubNameTextView);
            moreInfoBtn = itemView.findViewById(R.id.moreInfoBtn);

        }
    }
}
