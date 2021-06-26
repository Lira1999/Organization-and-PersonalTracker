package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button b, b2, b3, b4;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ForCse2200);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_home_24);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("WELCOME!");
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("INFO").child(mAuth.getCurrentUser().getUid());


        b2=(Button)findViewById(R.id.journal);
        b=(Button)findViewById(R.id.profile);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent it=new Intent(MainActivity.this, profile.class);
                        //Toast.makeText(getApplicationContext(), "this", Toast.LENGTH_SHORT).show();

                        String name=snapshot.child("name").getValue().toString();
                        String email=snapshot.child("email").getValue().toString();
                        String password=snapshot.child("password").getValue().toString();
                        String birth=snapshot.child("birth").getValue().toString();
                        String gender=snapshot.child("gender").getValue().toString();
                        String id=snapshot.child("userID").getValue().toString();


                        it.putExtra("user_name", name);
                        it.putExtra("user_email", email);
                        it.putExtra("user_password", password);
                        it.putExtra("user_gender", gender);
                        it.putExtra("user_birth", birth);
                        it.putExtra("user_id", id);

                        startActivity(it);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), journal_page.class);
                startActivity(it);
            }
        });

        b3 = (Button)findViewById(R.id.calendar);
        Intent it = new Intent(getApplicationContext(), Calendar_Page.class);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                _id=snapshot.child("userID").getValue().toString();
                it.putExtra("user_id", _id);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(it);
            }
        });
        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("INFO").child(mAuth.getCurrentUser().getUid());
        Intent intent = new Intent(getApplicationContext(), Friends.class);
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                String _id2=snapshot.child("userID").getValue().toString();
                intent.putExtra("user_id", _id2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        b4 = (Button)findViewById(R.id.others_events);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);

            }
        });



    }
    //for turning the xml file into java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //clicking options

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.signout)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent it=new Intent(getApplicationContext(), Login_form.class);
            startActivity(it);
        }

        if(item.getItemId()==R.id.calendarsharing)
        {
            Intent it =new Intent(getApplicationContext(), Share_Page.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

}