package com.example.forcse2200;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class profile_editing extends AppCompatActivity {
    EditText name, password, birth, gender;
    TextView email, id;
    ImageView image;
    Uri uri;
    StorageReference storageRef;
    StorageTask storageTask;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference, databaseReference1;
    private Button b, imagechooser;
    String _email, _password, _name, _birth, _gender, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_editing);
        email=findViewById(R.id.editemail);
        name=findViewById(R.id.editname);
        password=findViewById(R.id.editpassword);
        birth=findViewById(R.id.editbirth);
        gender=findViewById(R.id.editgender);
        image=findViewById(R.id.imageView);
        imagechooser=(Button)findViewById(R.id.choose);
        id=findViewById(R.id.editid);

        Intent intent=getIntent();

        _email=intent.getStringExtra("profile_email");
        _password=intent.getStringExtra("profile_password");
        _name=intent.getStringExtra("profile_name");
        _birth=intent.getStringExtra("profile_birth");
        _gender=intent.getStringExtra("profile_gender");
        _id=intent.getStringExtra("profile_id");

        name.setText(_name);
        email.setText(_email);
        password.setText(_password);
        birth.setText(_birth);
        gender.setText(_gender);
        id.setText(_id);


        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("INFO");
        storageRef=  FirebaseStorage.getInstance().getReference("INFO").child(firebaseAuth.getCurrentUser().getUid());

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String i=snapshot.child("imageURL").getValue().toString();
                if(!i.isEmpty())
                {
                    if(snapshot.hasChild("imageURL") )
                    {
                        String image1 = snapshot.child("imageURL").getValue().toString();

                        Picasso.get().load(image1).into(image);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });



        b=(Button)findViewById(R.id.save1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String key=firebaseAuth.getCurrentUser().getUid();
                String _EMAIL, _NAME, _PASSWORD, _BIRTH, _GENDER;
                _EMAIL=email.getText().toString().trim();
                _NAME=name.getText().toString().trim();
                _PASSWORD=password.getText().toString().trim();
                _BIRTH=birth.getText().toString().trim();
                _GENDER=gender.getText().toString().trim();


                databaseReference.child(key).child("name").setValue(_NAME);
                databaseReference.child(key).child("password").setValue(_PASSWORD);
                databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("birth").setValue(_BIRTH);
                databaseReference.child(key).child("gender").setValue(_GENDER);

                Intent it=new Intent(profile_editing.this, profile.class);
                it.putExtra("user_name", _NAME);
                it.putExtra("user_email", _EMAIL);
                it.putExtra("user_password", _PASSWORD);
                it.putExtra("user_gender", _GENDER);
                it.putExtra("user_birth", _BIRTH);
                it.putExtra("user_id", _id);
                startActivity(it);
            }
        });
        b=(Button)findViewById(R.id.back2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(profile_editing.this, profile.class);
                it.putExtra("user_name", _name);
                it.putExtra("user_email", _email);
                it.putExtra("user_password", _password);
                it.putExtra("user_gender", _gender);
                it.putExtra("user_birth", _birth);
                startActivity(it);
            }
        });
        imagechooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opening();
                if(!uri.equals(null))
                {
                    Toast.makeText(getApplicationContext(), "hh", Toast.LENGTH_SHORT).show();
                    StorageReference sref=storageRef.child(System.currentTimeMillis()+"."+getFileExtentsion(uri));
                    sref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Image Successfully Uploaded", Toast.LENGTH_LONG).show();
                            //to store reference in database for accessing later

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                            while (!uriTask.isSuccessful());

                            Uri uri1 = uriTask.getResult();


                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("imageURL").setValue(uri1.toString());


                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Image Failed to Upload, "+ e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "here"+uri, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void opening() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                Picasso.get().load(uri).into(image);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    public String getFileExtentsion(Uri uri)
    {
        ContentResolver contentResolver= getContentResolver(); //The ContentResolver methods provide the basic "CRUD" (create, retrieve, update, and delete) functions of persistent storage.
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();//MimeTypeMap->Returns the file extension or an empty string if there is no extension.
        // This method is a convenience method for obtaining the extension of a url
        //getSingleton->Get the singleton instance of MimeTypeMap, singleton allows only a single intance
        return mimeTypeMap.getExtensionFromMimeType((contentResolver.getType(uri)));
        //first gets the type of content using getType(uri), then gets the extension using mimtypemap
    }


}