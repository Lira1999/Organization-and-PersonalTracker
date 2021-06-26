package com.example.forcse2200;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Edit_Note extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton saver;
    EditText title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        saver = (FloatingActionButton) findViewById(R.id.floatingbutton1);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);

        saver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.floatingbutton1:
                String Title = title.getText().toString();
                String Description = description.getText().toString();

                if(!Title.isEmpty() && !Description.isEmpty())
                {
                    database db = new database(getApplicationContext());
                    db.addNotes(title.getText().toString(), description.getText().toString());
                    Toast.makeText(getApplicationContext(), " "+ description.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(getApplicationContext(), journal_page.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(it);
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(), "Both fields are required", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}