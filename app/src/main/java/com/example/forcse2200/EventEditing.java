package com.example.forcse2200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventEditing extends AppCompatActivity implements View.OnClickListener {
    EditText e1, e2, e3, e4, e5;
    Button back, update;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String _e1, _e2 ,_e3, _e4, _e5, the_date, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editing);
        e1=findViewById(R.id.updatedevent1);
        e2=findViewById(R.id.updatedevent2);
        e3=findViewById(R.id.updatedevent3);
        e4=findViewById(R.id.updatedevent4);
        e5=findViewById(R.id.updatedevent5);
        back=(Button)findViewById(R.id.go_back);
        back.setOnClickListener(this);
        update=(Button)findViewById(R.id.event_updated);
        update.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();

        Intent it=getIntent();
        id=it.getStringExtra("user_id");
        _e1 = it.getStringExtra("event1");
        _e2=it.getStringExtra("event2");
        _e3=it.getStringExtra("event3");
        _e4=it.getStringExtra("event4");
        _e5=it.getStringExtra("event5");
        the_date=it.getStringExtra("the_date");


        databaseReference= FirebaseDatabase.getInstance().getReference("INFO").child(id).child("events");

        e1.setText(_e1);
        e2.setText(_e2);
        e3.setText(_e3);
        e4.setText(_e4);
        e5.setText(_e5);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.go_back:
                Intent intent = new Intent(getApplicationContext(), Calendar_Page.class);
                startActivity(intent);
                break;
            case R.id.event_updated:
                databaseReference.child(the_date).child("event1").setValue(e1.getText().toString());
                databaseReference.child(the_date).child("event2").setValue(e2.getText().toString());
                databaseReference.child(the_date).child("event3").setValue(e3.getText().toString());
                databaseReference.child(the_date).child("event4").setValue(e4.getText().toString());
                databaseReference.child(the_date).child("event5").setValue(e5.getText().toString());
                Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();

                Intent intent1 = new Intent(getApplicationContext(), Calendar_Page.class);
                startActivity(intent1);
                break;
        }
    }
}