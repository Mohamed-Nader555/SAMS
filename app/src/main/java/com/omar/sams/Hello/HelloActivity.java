package com.omar.sams.Hello;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sams.Prof.ProfDashboardActivity;
import com.omar.sams.R;
import com.omar.sams.Student.StudentDashboardActivity;

public class HelloActivity extends AppCompatActivity {

    TextView text2;
    Button getStartedButton;
    Boolean isAdmin;
    Boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);


        Intent i = getIntent();

        isAdmin = i.getBooleanExtra("isAdmin", false);
        isConnected = i.getBooleanExtra("isConnected", false);

        text2 = findViewById(R.id.text_2);
        String text = "Learning with Pleasure with SAMS Guide, Whenever you are. ";
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 28, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text2.setText(ss);

        getStartedButton = findViewById(R.id.get_started_btn);

        if (!isConnected) {
            Toast.makeText(HelloActivity.this, "No Internet Connection !", Toast.LENGTH_LONG).show();
            getStartedButton.setClickable(false);
        } else {

            getStartedButton.setClickable(true);

            getStartedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isAdmin) {
                        Intent pageDashIntent = new Intent(HelloActivity.this, ProfDashboardActivity.class);
                        startActivity(pageDashIntent);
                        finish();
                    } else {
                        Intent pageDashIntent = new Intent(HelloActivity.this, StudentDashboardActivity.class);
                        startActivity(pageDashIntent);
                        finish();
                    }


                }
            });

        }
    }

}