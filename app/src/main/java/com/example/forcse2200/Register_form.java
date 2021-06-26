package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register_form extends AppCompatActivity implements View.OnClickListener {
    private EditText editemailforregister, editusernameforregister, editpasswordforregister, editconfirmpasswordforregister;
     private Button b1;
     private Button b2;
     ProgressBar pg;
     private FirebaseAuth mAuth;
     RadioGroup rg;
     String gender="";
     DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("INFO");
     String _ID, _id;
     int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        b1 = (Button)findViewById(R.id.register1);
        b2 = (Button)findViewById(R.id.login1);
        editemailforregister=findViewById(R.id.emailforregister);
        editusernameforregister=findViewById(R.id.usernameforregister);
        editpasswordforregister=findViewById(R.id.passwordforregister);
        editconfirmpasswordforregister=findViewById(R.id.confirmpasswordforregister);
        pg=findViewById(R.id.progressbrsignup);
        rg=findViewById(R.id.radiogroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton)
                {
                    gender="Female";
                }
                else
                {
                    gender="Male";
                }
            }
        });

        mAuth=FirebaseAuth.getInstance();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("lastID"))
                {
                    _ID=snapshot.child("lastID").getValue().toString();
                    id = Integer.valueOf(snapshot.child("lastID").getValue().toString());

                }
                else {
                    myRef.child("lastID").setValue("1");
                    _ID="1";
                    id=1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.register1:
                userregister();

                break;

            case R.id.login1:
                Intent it = new Intent(getApplicationContext(), Login_form.class);
                startActivity(it);
                break;
        }
    }

    private void userregister() {
        String email, password, username, confirmpassword, birth;
        email=editemailforregister.getText().toString().trim();
        password=editpasswordforregister.getText().toString();
        username=editusernameforregister.getText().toString();
        confirmpassword=editconfirmpasswordforregister.getText().toString();


        if(email.isEmpty())
        {
            editemailforregister.setError("Please enter Email");
            editemailforregister.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editpasswordforregister.setError("Please enter Password");
            editpasswordforregister.requestFocus();
            return;
        }
        if(username.isEmpty())
        {
            editusernameforregister.setError("Please enter User Name");
            editusernameforregister.requestFocus();
            return;
        }
        if(confirmpassword.isEmpty())
        {
            editconfirmpasswordforregister.setError("Please Confirm your password");
            editconfirmpasswordforregister.requestFocus();
            return;
        }
        if(gender.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Set your gender", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editemailforregister.setError("Please enter a valid Email");
            editemailforregister.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            editpasswordforregister.setError("Password has to be more than 5 characters");
            editpasswordforregister.requestFocus();
            return;
        }
        if(!confirmpassword.equals(password))
        {
            editconfirmpasswordforregister.setError("Passwords are not same");
            editconfirmpasswordforregister.requestFocus();
            return;
        }

        pg.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pg.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    //storing data in firebase
                    String uid = "User"+_ID;
                    PROFILE_FORM profile_form= new PROFILE_FORM(username, email, password, gender, "", "", "", uid);
                    User user = new User("", "", "", "");
                    myRef.child(uid).setValue(user);
                    myRef.child(mAuth.getCurrentUser().getUid()).setValue(profile_form);
                    id=id+1;
                    myRef.child("lastID").setValue(String.valueOf(id));
                    finish();
                    Intent it = new Intent(getApplicationContext(), Login_form.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(it);
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "User already have an account", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


}