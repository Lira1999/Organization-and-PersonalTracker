package com.example.forcse2200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Share_Page extends AppCompatActivity {
    Button share, see;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Mytheme1);
        setContentView(R.layout.activity_share_page);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        share=findViewById(R.id.sharing);
        see=findViewById(R.id.friends);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Sharing_Calendar.class));
            }
        });
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Others_Shared_Calendar.class));
            }
        });
    }
}