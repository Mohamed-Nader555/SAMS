package com.omar.sams.Common;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.sams.Common.Adapters.EContentAdapter;
import com.omar.sams.Models.EContentYearDataModel;
import com.omar.sams.R;

import java.util.ArrayList;

public class EContentActivity extends AppCompatActivity {

    RecyclerView eContentRecyclerView;
    EContentAdapter eContentAdapter;
     private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_econtent);


        initViews();


    }

    private void initViews() {
        eContentRecyclerView = findViewById(R.id.econtentRV);
        eContentRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(EContentActivity.this, RecyclerView.VERTICAL, false);
        eContentRecyclerView.setLayoutManager(mLayoutManager);
        eContentAdapter = new EContentAdapter(EContentActivity.this,  fillData());
        eContentRecyclerView.setAdapter(eContentAdapter);


    }

    private ArrayList<EContentYearDataModel> fillData() {
        ArrayList<EContentYearDataModel> years = new ArrayList<>();

        // Year 1
        EContentYearDataModel year1 = new EContentYearDataModel();
        year1.setYearName("Year 1 Material");
        year1.setSemesterUpName("1st Semester");
        year1.setSemesterDownName("2nd Semester");
        year1.setSemesterUpLink("https://drive.google.com/drive/folders/16WL36ZMowYbM2wG5_n0iiZCgImADKazJ?usp=sharing");
        year1.setSemesterDownLink("https://drive.google.com/drive/folders/12mlGg9dr0Lp1mgEXEFfbqfxLQ0WeR_Ls?usp=sharing");
        years.add(year1);

        // Year 2
        EContentYearDataModel year2 = new EContentYearDataModel();
        year2.setYearName("Year 2 Material");
        year2.setSemesterUpName("3rd Semester");
        year2.setSemesterDownName("4th Semester");
        year2.setSemesterUpLink("https://drive.google.com/drive/folders/1OerbTI0gPNovnl2npCX_yC6ceDKfS3lZ?usp=sharing");
        year2.setSemesterDownLink("https://drive.google.com/drive/folders/10rMkoIKY9WOh8Ok2DyN-E4zU2fwVzA51?usp=sharing");
        years.add(year2);

        // Year 3
        EContentYearDataModel year3 = new EContentYearDataModel();
        year3.setYearName("Year 3 Material");
        year3.setSemesterUpName("5th Semester");
        year3.setSemesterDownName("6th Semester");
        year3.setSemesterUpLink("https://drive.google.com/drive/folders/1urTr2dwHSAsvkzXHljqWMEKPJBSRK_vD?usp=sharing");
        year3.setSemesterDownLink("https://drive.google.com/drive/folders/1Zk8zfR6zSnjFk0_m0yv9h-0P9VmY4yS-?usp=sharing");
        years.add(year3);

        // Year 4
        EContentYearDataModel year4 = new EContentYearDataModel();
        year4.setYearName("Year 4 Material");
        year4.setSemesterUpName("7th Semester");
        year4.setSemesterDownName("8th Semester");
        year4.setSemesterUpLink("https://drive.google.com/drive/folders/1UBR_B8ZQrpfxEiZ32FfmzXu0Hqq3lgwM?usp=sharing");
        year4.setSemesterDownLink("https://drive.google.com/drive/folders/1Oml-TEcGadle511ybwuXZHe29PWAKMaj?usp=sharing");
        years.add(year4);

        return years;
    }

}