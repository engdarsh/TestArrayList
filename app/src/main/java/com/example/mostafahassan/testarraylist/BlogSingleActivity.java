package com.example.mostafahassan.testarraylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BlogSingleActivity extends AppCompatActivity {

    public TextView Temperature, BodyTemperature, Humidity, Heart, incNum,
            childName, date, weight, gender, idNum, birthDay;

    private DatabaseReference databaseRef;
    private String postKey = null;
    private Button singleRemoveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_single);

        postKey = getIntent().getExtras().getString("postKey");

        databaseRef = FirebaseDatabase.getInstance().getReference().child("Incubators");

        incNum = (TextView) findViewById(R.id.IncNum_View);
        childName = (TextView) findViewById(R.id.ChildNam_View);
        date = (TextView) findViewById(R.id.Date_View);
        weight = (TextView) findViewById(R.id.Weight_View);
        gender = (TextView) findViewById(R.id.Gender_View);
        idNum = (TextView) findViewById(R.id.IdNum_View);
        birthDay = (TextView) findViewById(R.id.BirthDay_View);

        singleRemoveButton = (Button) findViewById(R.id.singleRemoveButton);

        databaseRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String incNum_val = (String) dataSnapshot.child("title").getValue();
                String childName_val = (String) dataSnapshot.child("ChildName").getValue();
                String date_val = (String) dataSnapshot.child("Date").getValue();
                String weight_val = (String) dataSnapshot.child("Weight").getValue();
                String gender_val = (String) dataSnapshot.child("Gender").getValue();
                String idNum_val = (String) dataSnapshot.child("IdNum").getValue();
                String birthDay_val = (String) dataSnapshot.child("BirthDay").getValue();

                incNum .setText(incNum_val);
                incNum.setEnabled(true);
                childName.setText(childName_val);
                date.setText(date_val);
                weight.setText(weight_val);
                gender.setText(gender_val);
                idNum.setText(idNum_val);
                birthDay.setText(birthDay_val);

//                singleRemoveButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        singleRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseRef.child(postKey).removeValue();
                Intent singleBlogIntent = new Intent(BlogSingleActivity.this, MainActivity.class);
                Toast.makeText(BlogSingleActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                startActivity(singleBlogIntent);
                finish();
            }
        });
    }
}
