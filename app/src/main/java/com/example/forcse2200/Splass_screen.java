package com.example.forcse2200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class Splass_screen extends AppCompatActivity {
    ProgressBar pg;
    int prog=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //below two lines always should be called before content view
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //to make it full screen to hide system bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splass_screen);
        pg=findViewById(R.id.progressBar);
        pg.setMax(100);
        pg.setProgress(0);
        new Handler(Looper.getMainLooper()).postDelayed(runnable, 100);

    }
    //the time is always in milisec, 100ms every time
    Runnable runnable=new Runnable() {

        @Override
        public void run() {
            prog+=5;
            if(prog>100)
            {
                startActivity(new Intent(getApplicationContext(), Login_form.class));

            }
            else
            {
                pg.setProgress(prog);
                new Handler(Looper.getMainLooper()).postDelayed(runnable, 100);
            }

        }
    };
}