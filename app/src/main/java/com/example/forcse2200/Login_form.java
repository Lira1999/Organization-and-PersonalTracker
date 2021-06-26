package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_form extends AppCompatActivity implements View.OnClickListener {
    private EditText editSigninemail, editsigninpassword;
    Button signinbutton, registerbutton;
    private ProgressBar progressBarSignin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        setTitle("Sign in");

        editSigninemail = findViewById(R.id.emaillogin);
        editsigninpassword = findViewById(R.id.passwordlogin);
        registerbutton = (Button) findViewById(R.id.register2);
        signinbutton = (Button)findViewById(R.id.login2);

        progressBarSignin = findViewById(R.id.progressbrsignin);
        mAuth = FirebaseAuth.getInstance();
        registerbutton.setOnClickListener(this);
        signinbutton.setOnClickListener(this);

        if(mAuth.getCurrentUser() != null)
        {
            Toast.makeText(getApplicationContext(), "Found User", Toast.LENGTH_SHORT).show();
            Intent it =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(it);
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())//there will be two cases in switch for button & text view
        {
            case R.id.login2:
                userrLogin();
                break;

            case R.id.register2:

                Intent it = new Intent(getApplicationContext(), Register_form.class);
                startActivity(it);
                break;
        }

    }

    private void userrLogin() {
        String email = editSigninemail.getText().toString().trim();
        String password = editsigninpassword.getText().toString().trim();

        //checking validity
        if(email.isEmpty())
        {
            editSigninemail.setError("Enter an email address");
            editSigninemail.requestFocus();//cursor will go back there
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editSigninemail.setError("Enter an valid email address");
            editSigninemail.requestFocus();//cursor will go back there
            return;
        }

        if(password.isEmpty())
        {
            editsigninpassword.setError("Enter a password");
            editsigninpassword.requestFocus();//cursor will go back there
            return;
        }

        if(password.length()<6)
        {
            editsigninpassword.setError("Password too small, should be minimum 7 characters");
            editsigninpassword.requestFocus();//cursor will go back there
            return;
        }

        progressBarSignin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {

                progressBarSignin.setVisibility(View.GONE);
                if(task.isSuccessful())
                {


                    finish();//for finishing the activity
                    Intent it =new Intent(getApplicationContext(), MainActivity.class);
                    //to clear everything in top after going to homepage
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(it);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}