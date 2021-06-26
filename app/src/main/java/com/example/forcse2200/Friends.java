package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Friends extends AppCompatActivity {
    DatabaseReference d1;
    String uid, _userid;
    EditText u2;
    Button show;
    RecyclerView recyclerView;
    TextView u;
    Show_Share_Adapter show_share_adapter;
    ArrayList<For_Events> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        show = (Button)findViewById(R.id.showevents);
        u2=findViewById(R.id.user_id2);
        recyclerView = findViewById(R.id.events);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        show_share_adapter = new Show_Share_Adapter(this, list);
        recyclerView.setAdapter(show_share_adapter);


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it=getIntent();
                uid = it.getStringExtra("user_id");
                //Toast.makeText(getApplicationContext(), ""+uid, Toast.LENGTH_SHORT).show();
                String m1 = u2.getText().toString();
                list.clear();
                DatabaseReference d = FirebaseDatabase.getInstance().getReference("INFO").child(uid).child("sharee_from");

                d.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int i=0;

                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            //Toast.makeText(getApplicationContext(), ""+ds.getValue().toString(), Toast.LENGTH_SHORT).show();
                            if(m1.equals(ds.getValue().toString()))
                            {

                                DatabaseReference d3 = FirebaseDatabase.getInstance().getReference("INFO").child(m1).child("events");
                                i=i+1;
                                d3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot dss : snapshot.getChildren())
                                        {

                                            For_Events for_events = dss.getValue(For_Events.class);
                                            list.add(for_events);


                                        }
                                        show_share_adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }


                        }
                        if(i==0)
                        {
                            Toast.makeText(getApplicationContext(), "You are not Connected", Toast.LENGTH_SHORT).show();
                        }
                        // calendar_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                //Toast.makeText(getApplicationContext(), ""+uid, Toast.LENGTH_SHORT).show();
                //_userid=userid.getText().toString();
               /* d1= FirebaseDatabase.getInstance().getReference("INFO").child(uid).child("share_from");
                d1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(getApplicationContext(), ""+_userid, Toast.LENGTH_SHORT).show();
                        //check(_userid);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
            }
        });


    }


}