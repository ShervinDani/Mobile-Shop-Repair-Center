package com.example.mobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewStatus extends AppCompatActivity {
    Button btn;
    DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_status);
        btn=findViewById(R.id.button1);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("Phone");
        TextView progress1=findViewById(R.id.editTextTextPersonName1);
        TextView date1=findViewById(R.id.editTextTextPersonName2);
        TextView damage1=findViewById(R.id.editTextTextPersonName3);
        TextView des1=findViewById(R.id.editTextTextPersonName4);
        dbr.child("service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(phone))
                {
                    String damage=snapshot.child(phone).child("service").child("damage").getValue(String.class);
                    String date=snapshot.child(phone).child("service").child("date").getValue(String.class);
                    String des=snapshot.child(phone).child("service").child("description").getValue(String.class);
                    String progress=snapshot.child(phone).child("service").child("progress").getValue(String.class);
                    progress1.setText(progress);
                    date1.setText(date);
                    des1.setText(des);
                    damage1.setText(damage);
                }
                else
                {
                    progress1.setText("-");
                    date1.setText("-");
                    des1.setText("-");
                    damage1.setText("-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewStatus.this,Dashboard.class);
                intent.putExtra("Phone", phone);
                startActivity(intent);
            }
        });
    }
}