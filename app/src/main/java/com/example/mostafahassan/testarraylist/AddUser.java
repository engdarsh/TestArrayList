package com.example.mostafahassan.testarraylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddUser extends AppCompatActivity {
    private Button mFirebaseBtn;
    private EditText mNameField;
    private EditText mPhoneField;
    private DatabaseReference mDatabase;

    HashMap<String, String> dataMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mFirebaseBtn = (Button) findViewById(R.id.firebaseBtn);
        mNameField = (EditText) findViewById(R.id.name_field);
        mPhoneField = (EditText) findViewById(R.id.phone_field);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {        //add button
            @Override
            public void onClick(View v) {

                String name = mNameField.getText().toString().trim();
                String phone = mPhoneField.getText().toString().trim();

                if (dataMap.containsValue(phone)) {
                    Toast.makeText(AddUser.this, "The Phone is Existing ", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    HashMap<String, String> newdataMap = new HashMap<String, String>();
                    newdataMap.put("Name", name);
                    newdataMap.put("Phone", phone);
                    dataMap.put("Name", name);
                    dataMap.put("Phone", phone);

                    mDatabase.push().setValue(newdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddUser.this, "Success", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AddUser.this, IncubatorsTable.class));
                                finish();
                            } else {
                                Toast.makeText(AddUser.this, "Faild", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

}
