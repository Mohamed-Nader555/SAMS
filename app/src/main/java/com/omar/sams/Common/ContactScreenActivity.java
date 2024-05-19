package com.omar.sams.Common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sams.R;

public class ContactScreenActivity extends AppCompatActivity {

    ImageView locationImageView, facebookBtn, instagramBtn, linkedinBtn;
    String locationLink, faceBookLink, instagramLink, linkedInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);

        initViews();


    }

    private void initViews() {

        locationLink = "https://www.google.com/maps/place/Sadat+Academy+for+Management+Sciences/@29.9594058,31.2485934,18.88z/data=!4m9!1m2!2m1!1sextract+color+map+from+photo!3m5!1s0x145847efb305d439:0x7b99c3a86f98ca43!8m2!3d29.9595123!4d31.2488609!16zL20vMGd6dG5s?entry=ttu";
        faceBookLink = "https://www.facebook.com/sams.edu.eg";
        instagramLink = "https://www.instagram.com/sadatacademy?igsh=MWVoNHJwN3J0ZTRvaA==";
        linkedInLink = "https://www.linkedin.com/school/sadat-academy-for-management-sciences/?original_referer=https%3A%2F%2Fwww%2Egoogle%2Ecom%2F&originalSubdomain=eg";

        locationImageView = findViewById(R.id.locationImage);
        facebookBtn = findViewById(R.id.facebookBtn);
        instagramBtn = findViewById(R.id.instagramBtn);
        linkedinBtn = findViewById(R.id.linkedinBtn);


        locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(locationLink));
                startActivity(intent);
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(faceBookLink));
                startActivity(intent);
            }
        });


        instagramBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(instagramLink));
                startActivity(intent);
            }
        });

        linkedinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(linkedInLink));
                startActivity(intent);
            }
        });
    }
}