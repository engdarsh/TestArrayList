package com.example.mostafahassan.testarraylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    public EditText childName, date, weight, gender, idNum, birthDay, incNum;

    private Button submitBtn;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseRefUser;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Incubators");
        databaseRefUser = FirebaseDatabase.getInstance().getReference().child("Users");

        incNum = (EditText) findViewById(R.id.IncNum_Edite);
        childName = (EditText) findViewById(R.id.ChildNam_Edite);
        date = (EditText) findViewById(R.id.Date_Edite);
        weight = (EditText) findViewById(R.id.Weight_Edite);
        gender = (EditText) findViewById(R.id.Gender_Edite);
        idNum = (EditText) findViewById(R.id.IdNum_Edite);
        birthDay = (EditText) findViewById(R.id.BirthDay_Edite);

        submitBtn= (Button) findViewById(R.id.submitButton);

        progress = new ProgressDialog(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {
        progress.setMessage("Uploading Data ...");

        final String incNum_val = incNum.getText().toString().trim();
        final String childName_val = childName.getText().toString().trim();
        final String date_val = date.getText().toString().trim();
        final String weight_val = weight.getText().toString().trim();
        final String gender_val = gender.getText().toString().trim();
        final String idNum_val = idNum.getText().toString().trim();
        final String birthDay_val = birthDay.getText().toString().trim();

        if (!TextUtils.isEmpty(incNum_val) && !TextUtils.isEmpty(childName_val)&& !TextUtils.isEmpty(date_val)
                && !TextUtils.isEmpty(weight_val)&& !TextUtils.isEmpty(gender_val)&& !TextUtils.isEmpty(idNum_val)
                && !TextUtils.isEmpty(birthDay_val)) {
            progress.show();

            final DatabaseReference newPostRef = databaseReference.push();

            databaseRefUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPostRef.child("title").setValue(incNum_val);
                    newPostRef.child("IncNum").setValue(incNum_val);
                    newPostRef.child("ChildName").setValue(childName_val);
                    newPostRef.child("Date").setValue(date_val);
                    newPostRef.child("Weight").setValue(weight_val);
                    newPostRef.child("Gender").setValue(gender_val);
                    newPostRef.child("IdNum").setValue(idNum_val);
                    newPostRef.child("BirthDay").setValue(birthDay_val);

                    newPostRef.child("username").setValue(dataSnapshot.child("name")
                            .getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(PostActivity.this, MainActivity.class));
                            }
                            progress.dismiss();
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            if (incNum_val.isEmpty()) {
                incNum.setError("Invalid IncNum");
            }
            if (childName_val.isEmpty()) {
                childName.setError("Invalid Name");
            }
            if (date_val.isEmpty()) {
                date.setError("Invalid date");
            }
            if (weight_val.isEmpty()) {
                weight.setError("Invalid weight");
            }
            if (gender_val.isEmpty()) {
                gender.setError("Invalid gender");
            }
            if (idNum_val.isEmpty()) {
                idNum.setError("Invalid IdNum");
            }
            if (birthDay_val.isEmpty()) {
                birthDay.setError("Invalid BirthDay");
            }
        }

    }
}
