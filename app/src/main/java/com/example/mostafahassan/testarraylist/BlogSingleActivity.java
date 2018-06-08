package com.example.mostafahassan.testarraylist;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
            childName, date, weight, gender, idNum, birthDay ,DeleteBt,CloseBt;

    private DatabaseReference mDatabase;

    NewMessageNotification notfic=new NewMessageNotification();

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

        Temperature = (TextView) findViewById(R.id.Temperature);
        BodyTemperature = (TextView) findViewById(R.id.BodyTemperature);
        Humidity = (TextView) findViewById(R.id.Humidity);
        Heart = (TextView) findViewById(R.id.Heart);

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

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Heart");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String HeartValue =dataSnapshot.getValue().toString();
                Heart.setText(HeartValue);
                int i=Integer.parseInt(HeartValue.replaceAll("[\\D]", ""));

                if( i<60 || i>120)
                {
                    note(1);
                    Log.v("Heart",HeartValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Temperature");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String TemperatureValue =dataSnapshot.getValue().toString();
                Temperature.setText(TemperatureValue);
                int i=Integer.parseInt(TemperatureValue.replaceAll("[\\D]", ""));

                if( i<20 || i>35 )
                {
                    note(4);
                    Log.v("Temperature",TemperatureValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("BodyTemperature");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String BodyTemperatureValue =dataSnapshot.getValue().toString();
                BodyTemperature.setText(BodyTemperatureValue);

                int i=Integer.parseInt(BodyTemperatureValue.replaceAll("[\\D]", ""));

                if( i<30 || i>38 )
                {
                    note(2);
                    Log.v("BodyTemperature",BodyTemperatureValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Humidity");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String HumidityValue =dataSnapshot.getValue().toString();
                Humidity.setText(HumidityValue);
                int i=Integer.parseInt(HumidityValue.replaceAll("[\\D]", ""));

                if(i<40 || i >60)
                {
                    note(3);
                    Log.v("Humidity",HumidityValue);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        singleRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }
        });
    }

    private void displayDialog()
    {
        Dialog d= new Dialog(this);
        d.setContentView(R.layout.customdialog_layout);
        d.setTitle("Remove Incubator");
        DeleteBt= (TextView) d.findViewById(R.id.Delete);
        CloseBt=(TextView)d.findViewById(R.id.Cancel);

        DeleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseRef.child(postKey).removeValue();
                Toast.makeText(BlogSingleActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                Intent singleBlogIntent = new Intent(BlogSingleActivity.this, IncubatorsTable.class);
                startActivity(singleBlogIntent);
                finish();
            }
        });

        CloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(BlogSingleActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        d.show();

    }


    Integer note(int z){
        if(z==1)
            notfic.notify(this,"Danger Heart",1);
        else if(z==2)
            notfic.notify(this,"Danger BodyTemperature",1);
        else if(z==3)
            notfic.notify(this,"Danger Humidity",1);
        else if(z==4)
            notfic.notify(this,"Danger Temperature",1);

        return 0;
    }
}
