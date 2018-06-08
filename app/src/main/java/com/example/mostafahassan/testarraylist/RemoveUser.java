package com.example.mostafahassan.testarraylist;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RemoveUser extends AppCompatActivity {

    private Button mDeleteBtn;
    private EditText mdeletedNumber;
    private DatabaseReference mDatabase;

    HashMap<String, String> dataMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_user);

        mDeleteBtn = (Button) findViewById(R.id.deleteBtn);
        mdeletedNumber = (EditText) findViewById(R.id.deletedNumber);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mdeletedNumber.getText().toString().trim();

                if (dataMap.equals(phone)) {
                    Toast.makeText(RemoveUser.this, phone, Toast.LENGTH_LONG).show();
                    dataMap.remove(phone);

                    DatabaseReference drf = FirebaseDatabase.getInstance().getReference().child("Users");
                    drf.removeValue();
                    drf.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RemoveUser.this, "success update", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RemoveUser.this, "faild", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                    Toast.makeText(RemoveUser.this, "number not found", Toast.LENGTH_LONG).show();
            }
        });
    }
}
