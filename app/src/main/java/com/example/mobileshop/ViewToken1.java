package com.example.mobileshop;

import static com.example.mobileshop.R.layout.activity_view_token1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewToken1 extends AppCompatActivity {
    long maxid;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
        super.onCreate(savedInstanceState);
        setContentView(activity_view_token1);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("Phone").toString();
        TextView textView1=findViewById(R.id.textView5);
        TextView textView2=findViewById(R.id.textView6);
        textView1.setText("YOUR TOKEN NUMBER:");
        dbr.child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(phone))
                {
                    String token=snapshot.child(phone).child("token").getValue().toString();
                    textView2.setText(token);
                }
                else
                {
                    textView2.setText("YOU HAVE NO TOKEN");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}