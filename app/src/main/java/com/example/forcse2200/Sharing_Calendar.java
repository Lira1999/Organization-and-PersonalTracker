package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sharing_Calendar extends AppCompatActivity {
    Button floatingActionButton, delete;

    EditText users;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String uid;
    Calendar_Adapter calendar_adapter;
    ArrayList<Share_to_Users> share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_ForCse2200);
        setContentView(R.layout.activity_sharing_calendar);
        setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        floatingActionButton = findViewById(R.id.new_share_to);
        users=findViewById(R.id.share_to);

        recyclerView = findViewById(R.id.share_to_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        share = new ArrayList<>();
        calendar_adapter = new Calendar_Adapter(this, share);
        recyclerView.setAdapter(calendar_adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = users.getText().toString().trim();
                if(uid.isEmpty())
                {
                    users.setError("Enter User ID");
                    users.requestFocus();
                    return;
                }
                else
                {
                    data();
                }
            }
        });
                DatabaseReference d = FirebaseDatabase.getInstance().getReference("INFO").child(firebaseAuth.getCurrentUser().getUid());
                d.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        String id = snapshot.child("userID").getValue().toString();
                        well(id);
                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });


    }


    private void well(String id) {

        DatabaseReference d = FirebaseDatabase.getInstance().getReference("INFO").child(id).child("share_to");

        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    Share_to_Users user = ds.getValue(Share_to_Users.class);
                    share.add(user);
                   // Toast.makeText(getApplicationContext(), ""+share.toString(), Toast.LENGTH_SHORT).show();
                }
                calendar_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




    private void data() {
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("INFO");
        //Toast.makeText(getApplicationContext(), ""+uid, Toast.LENGTH_SHORT).show();
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(uid))
                {
                    DatabaseReference d = FirebaseDatabase.getInstance().getReference("INFO").child(firebaseAuth.getCurrentUser().getUid());
                    d.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull  DataSnapshot snapshot) {
                            String id = snapshot.child("userID").getValue().toString();
                            if(uid.equals(id))
                            {
                                Toast.makeText(getApplicationContext(), "You are the user", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                go(id);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull  DatabaseError error) {

                        }
                    });
                }
                else
                {
                    users.setError("The User doesn't exist");
                    users.requestFocus();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void go(String id) {

        DatabaseReference dref1 = FirebaseDatabase.getInstance().getReference("INFO").child(id);

        dref1.child("sharee_to").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Toast.makeText(getApplicationContext(), ""+i[0], Toast.LENGTH_SHORT).show();
                int i=0;
                for(DataSnapshot d : snapshot.getChildren())
                {

                    if(uid.equals(d.getValue().toString()))
                    {

                        i=i+1;
                       // Toast.makeText(getApplicationContext(), "Already exists", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
                if(i==0)
                {
                    share.clear();

                    Share_to_Users share_to_users = new Share_to_Users(uid);
                    dref1.child("share_to").push().setValue(share_to_users);
                    dref1.child("sharee_to").push().setValue(uid);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_SHORT).show();
        //Share_to_Users share_to_users = new Share_to_Users(uid);
        //alist.add(share_to_users);

        return;

    }

}