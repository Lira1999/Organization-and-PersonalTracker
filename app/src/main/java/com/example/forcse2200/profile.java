package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class profile extends AppCompatActivity implements View.OnClickListener {
    Button update, back;
    TextView name, email, password, dateofbirth, gender, id;
    String _email, _password, _name, _birth, _gender, _id;
    ImageView newimage;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ProgressBar pg;
    int count=0;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        update=(Button)findViewById(R.id.edit1);
        update.setOnClickListener(this);
        back=(Button)findViewById(R.id.back1);
        back.setOnClickListener(this);
        name=findViewById(R.id.profilename);
        email=findViewById(R.id.profileemail);
        password=findViewById(R.id.profilepassword);
        dateofbirth=findViewById(R.id.birthdate);
        gender=findViewById(R.id.gender);
        mAuth=FirebaseAuth.getInstance();
        id=findViewById(R.id.profileid);

        newimage = findViewById(R.id.imageView1);

        Intent intent=getIntent();

        _email=intent.getStringExtra("user_email");
        _password=intent.getStringExtra("user_password");
        _name=intent.getStringExtra("user_name");
        _birth=intent.getStringExtra("user_birth");
        _gender=intent.getStringExtra("user_gender");
        _id=intent.getStringExtra("user_id");
        //Toast.makeText(getApplicationContext(), ""+_email, Toast.LENGTH_SHORT).show();

        name.setText(_name);
        email.setText(_email);
        password.setText(_password);
        dateofbirth.setText(_birth);
        gender.setText(_gender);
        id.setText(_id);

        databaseReference= FirebaseDatabase.getInstance().getReference("INFO").child(mAuth.getCurrentUser().getUid());



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String i=snapshot.child("imageURL").getValue().toString();
                if(!i.isEmpty())
                {
                    if(snapshot.hasChild("imageURL") )
                    {
                        String image = snapshot.child("imageURL").getValue().toString();

                        Picasso.get().load(image).into(newimage);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.edit1:
                Intent it = new Intent(getApplicationContext(), profile_editing.class);
                it.putExtra("profile_email", _email);
                it.putExtra("profile_name", _name);
                it.putExtra("profile_password", _password);
                it.putExtra("profile_gender", _gender);
                it.putExtra("profile_birth", _birth);
                it.putExtra("profile_id", _id);

                startActivity(it);
                break;
            case R.id.back1:
                Intent it1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it1);
                break;
        }

    }


}