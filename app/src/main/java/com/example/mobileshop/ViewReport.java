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

public class ViewReport extends AppCompatActivity {
    DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        btn=findViewById(R.id.submit);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("Phone");
        TextView id=findViewById(R.id.editTextTextPersonName1);
        TextView parts=findViewById(R.id.editTextTextPersonName2);
        TextView war=findViewById(R.id.editTextTextPersonName3);
        TextView cost=findViewById(R.id.editTextTextPersonName4);
        dbr.child("service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(phone))
                {
                    String id1=snapshot.child(phone).child("report").child("id").getValue(String.class);
                    String parts1=snapshot.child(phone).child("report").child("parts").getValue(String.class);
                    String cost1=snapshot.child(phone).child("report").child("cost").getValue(String.class);
                    String war1=snapshot.child(phone).child("report").child("warranty").getValue(String.class);
                    id.setText(id1);
                    parts.setText(parts1);
                    war.setText(war1);
                    cost.setText(cost1);
                }
                else
                {
                    id.setText("No Report");
                    parts.setText("No Report");
                    war.setText("No Report");
                    cost.setText("No Report");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewReport.this,Dashboard.class);
                intent.putExtra("Phone", phone);
                startActivity(intent);
            }
        });
    }
}