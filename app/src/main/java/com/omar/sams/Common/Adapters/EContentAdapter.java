package com.omar.sams.Common.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.sams.Models.EContentYearDataModel;
import com.omar.sams.R;

import java.util.ArrayList;

public class EContentAdapter extends RecyclerView.Adapter<EContentAdapter.myEcontentAdapterViewHolder> {

    private Context context;
    private ArrayList<EContentYearDataModel> yearsList;

    public EContentAdapter(Context context, ArrayList<EContentYearDataModel> yearsList) {
        this.context = context;
        this.yearsList = yearsList;
    }

    @NonNull
    @Override
    public EContentAdapter.myEcontentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.econtent_row, parent, false);

        EContentAdapter.myEcontentAdapterViewHolder viewHolder = new EContentAdapter.myEcontentAdapterViewHolder(view);


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull myEcontentAdapterViewHolder holder, int position) {
        EContentYearDataModel year = yearsList.get(position);
        holder.yearNameText.setText(year.getYearName());
        holder.firstSemesterBtn.setText(year.getSemesterUpName());
        holder.secondSemesterBtn.setText(year.getSemesterDownName());


        holder.firstSemesterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(year.getSemesterUpLink()));
                context.startActivity(intent);
            }
        });


        holder.secondSemesterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(year.getSemesterDownLink()));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return yearsList.size();
    }


    public class myEcontentAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView yearNameText, firstSemesterBtn, secondSemesterBtn;

        public myEcontentAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            yearNameText = itemView.findViewById(R.id.yearNameText);
            firstSemesterBtn = itemView.findViewById(R.id.firstSemesterBtn);
            secondSemesterBtn = itemView.findViewById(R.id.secondSemesterBtn);
        }
    }

}
