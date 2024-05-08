package com.omar.sams.Hello;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omar.sams.R;

public class HelloActivity extends AppCompatActivity {

    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        text2 = findViewById(R.id.text_2);
        String text = "Learning with Pleasure with SAMS Guide, Whenever you are. ";
        SpannableString ss = new SpannableString(text);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 28, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text2.setText(ss);

    }
}