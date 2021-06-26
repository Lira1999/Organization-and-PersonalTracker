package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Calendar_Page extends AppCompatActivity implements View.OnClickListener {
    EditText event, number;
    TextView e1, e2, e3, e4, e5;
    private Button b1, for_update;
    private CalendarView calendarView;
    String the_date, id, mid, ss, date;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference, d;
    For_Events for_events;
    LinearLayout for_adding, for_viewing;
    Calendar_Adapter calendar_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Mytheme1);
        setContentView(R.layout.activity_calendar_page);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        event = findViewById(R.id.event_input);
        number = findViewById(R.id.event_no);
        calendarView = findViewById(R.id.calendarView);
        b1=(Button)findViewById(R.id.add_event);
        b1.setOnClickListener(this);
        for_update=(Button)findViewById(R.id.event_edit);
        for_update.setOnClickListener(this);
        firebaseAuth=FirebaseAuth.getInstance();
        for_adding=findViewById(R.id.eventadding);


        Intent it = getIntent();
        mid=it.getStringExtra("user_id");

        databaseReference= FirebaseDatabase.getInstance().getReference("INFO");
        e1=findViewById(R.id.my_event1);
        e2=findViewById(R.id.my_event2);
        e3=findViewById(R.id.my_event3);
        e4=findViewById(R.id.my_event4);
        e5=findViewById(R.id.my_event5);
        for_viewing=findViewById(R.id.viewer);

        //recyclerView = findViewById(R.id.calendar_recycler);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //using setter method in PROFILE_FORM all the data is given into options
        /*FirebaseRecyclerOptions<For_Events> options =
                new FirebaseRecyclerOptions.Builder<For_Events>()
                        .setQuery(databaseReference, For_Events.class)
                        .build();
        calendar_adapter = new Calendar_Adapter(options);
        recyclerView.setAdapter(calendar_adapter);*/

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                for_adding.setVisibility(View.VISIBLE);
                the_date = Integer.toString(year)+ Integer.toString(month)+Integer.toString(dayOfMonth);
                date=Integer.toString(dayOfMonth)+"-"+Integer.toString(month)+"-"+Integer.toString(year);
                for_viewing.setVisibility(View.INVISIBLE);
                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        id=snapshot.child("userID").getValue().toString();
                        //Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                ReadDatabase(view);

            }
        });




    }

    /*@Override
    protected void onStart() {
        super.onStart();
        calendar_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        calendar_adapter.stopListening();
    }*/

    private void ReadDatabase(CalendarView view) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(id).child("events").hasChild(the_date))
                {
                    for_viewing.setVisibility(View.VISIBLE);
                    String s1=snapshot.child(id).child("events").child(the_date).child("event1").getValue().toString();
                    String s2=snapshot.child(id).child("events").child(the_date).child("event2").getValue().toString();
                    String s3=snapshot.child(id).child("events").child(the_date).child("event3").getValue().toString();
                    String s4=snapshot.child(id).child("events").child(the_date).child("event4").getValue().toString();
                    String s5=snapshot.child(id).child("events").child(the_date).child("event5").getValue().toString();

                    e1.setText(s1);
                    e2.setText(s2);
                    e3.setText(s3);
                    e4.setText(s4);
                    e5.setText(s5);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.add_event:
                InsertData();
                break;
            case R.id.event_edit:
                Update_Event();
                break;
        }

    }


    private void Update_Event() {
        databaseReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("events").hasChild(the_date))
                {

                    Intent it =new Intent(getApplicationContext(), EventEditing.class);

                    String s1=snapshot.child("events").child(the_date).child("event1").getValue().toString();
                    String s2=snapshot.child("events").child(the_date).child("event2").getValue().toString();
                    String s3=snapshot.child("events").child(the_date).child("event3").getValue().toString();
                    String s4=snapshot.child("events").child(the_date).child("event4").getValue().toString();
                    String s5=snapshot.child("events").child(the_date).child("event5").getValue().toString();

                    it.putExtra("event1", s1);
                    it.putExtra("event2", s2);
                    it.putExtra("event3", s3);
                    it.putExtra("event4", s4);
                    it.putExtra("event5", s5);
                    it.putExtra("the_date", the_date);
                    it.putExtra("user_id", id);


                    startActivity(it);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void InsertData() {
        String events = event.getText().toString();
        String numbers = number.getText().toString();
        int i=Integer.valueOf(numbers);
        if(events.isEmpty() && numbers.isEmpty())
        {
            event.setError("Please enter an event");
            event.requestFocus();
            return;
        }
        if(events.isEmpty())
        {
            event.setError("Please enter an event");
            event.requestFocus();
            return;
        }
        if(numbers.isEmpty())
        {
            number.setError("Please enter Your event number");
            number.requestFocus();
            return;
        }
        if(i>5)
        {
            number.setError("You can add only 5 events");
            number.requestFocus();
            return;
        }
        else
        {
            databaseReference.child(id).child("events").child(the_date).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull  DataSnapshot snapshot) {
                    String strPattern = "^0+";
                    String key = numbers.replaceAll(strPattern, "");
                    if(!snapshot.hasChild("last_event"))
                    {
                        for_events = new For_Events("", "", "", "", "", "");
                        databaseReference.child(id).child("events").child(the_date).setValue(for_events);
                        databaseReference.child(id).child("events").child(the_date).child("last_event").setValue(date);
                        databaseReference.child(id).child("events").child(the_date).child("event"+key).setValue(events);
                    }
                    else
                    {
                        databaseReference.child(id).child("events").child(the_date).child("event"+key).setValue(events);
                        databaseReference.child(id).child("events").child(the_date).child("last_event").setValue(date);

                    }
                    event.setText("");
                    number.setText("");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

}