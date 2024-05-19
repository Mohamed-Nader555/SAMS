package com.omar.sams.Common;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.sams.Common.Adapters.ClubsAdapter;
import com.omar.sams.Models.ClubsDataModel;
import com.omar.sams.R;

import java.util.ArrayList;

public class ClubsActivity extends AppCompatActivity {

    RecyclerView clubsRecyclerView;
    ClubsAdapter clubsAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        initViews();


    }


    private void initViews() {
        clubsRecyclerView = findViewById(R.id.clubsRV);
        clubsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ClubsActivity.this, RecyclerView.VERTICAL, false);
        clubsRecyclerView.setLayoutManager(mLayoutManager);
        clubsAdapter = new ClubsAdapter(ClubsActivity.this, fillData());
        clubsRecyclerView.setAdapter(clubsAdapter);
    }

    private ArrayList<ClubsDataModel> fillData() {
        ArrayList<ClubsDataModel> clubs = new ArrayList<>();

        // Dream with Us
        ClubsDataModel club1 = new ClubsDataModel();
        club1.setClubName("Dream with Us");
        club1.setClubImg("@drawable/dream");
        club1.setClubLink("https://www.facebook.com/JustDreamSAMS");
        clubs.add(club1);

        // AIBE
        ClubsDataModel club2 = new ClubsDataModel();
        club2.setClubName("AIBE");
        club2.setClubImg("@drawable/aibe");
        club2.setClubLink("https://www.facebook.com/AIBESAMS");
        clubs.add(club2);

        // Hult
        ClubsDataModel club3 = new ClubsDataModel();
        club3.setClubName("Hult");
        club3.setClubImg("@drawable/hult");
        club3.setClubLink("https://www.facebook.com/hultprize.samss");
        clubs.add(club3);

        // Enactus
        ClubsDataModel club4 = new ClubsDataModel();
        club4.setClubName("Enactus");
        club4.setClubImg("@drawable/enactus");
        club4.setClubLink("https://www.facebook.com/enactus.sams23");
        clubs.add(club4);

        return clubs;
    }

}