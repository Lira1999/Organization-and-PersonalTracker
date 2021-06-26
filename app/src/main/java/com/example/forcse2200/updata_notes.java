package com.example.forcse2200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class updata_notes extends AppCompatActivity implements View.OnClickListener {
    EditText uptitle, updes;
    FloatingActionButton update;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_notes);

        uptitle=findViewById(R.id.updatetitle);
        updes=findViewById(R.id.updatedescription);
        update=(FloatingActionButton)findViewById(R.id.updatefloatingbutton);
        update.setOnClickListener(this);

        Intent it = getIntent();
        uptitle.setText(it.getStringExtra("title"));
        updes.setText(it.getStringExtra("description"));
        id=it.getStringExtra("id");


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.updatefloatingbutton:

                if(!TextUtils.isEmpty(uptitle.getText().toString())&& !TextUtils.isEmpty(updes.getText().toString()))
                {
                    database db = new database(updata_notes.this);
                    db.updateNotes(uptitle.getText().toString(),updes.getText().toString(),id);
                    Intent i = new Intent(updata_notes.this, journal_page.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();


                }else{
                    Toast.makeText(getApplicationContext(), "Both Field Required", Toast.LENGTH_SHORT).show();
                }


        }

    }
}