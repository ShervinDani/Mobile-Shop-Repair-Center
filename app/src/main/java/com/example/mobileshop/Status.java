package com.example.mobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Status extends AppCompatActivity {
Button sbtn;
    DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        sbtn=findViewById(R.id.button1);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("Phone");
        final EditText progress=findViewById(R.id.editTextTextPersonName);
        final EditText date=findViewById(R.id.editTextDate);
        final EditText damage=findViewById(R.id.editTextTextPersonName3);
        final EditText description=findViewById(R.id.editTextTextPersonName4);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String progress1=progress.getText().toString();
                String date1=date.getText().toString();
                String damage1=damage.getText().toString();
                String description1=description.getText().toString();
                if(progress1.isEmpty() || date1.isEmpty() || damage1.isEmpty() || description1.isEmpty())
                {
                    Toast.makeText(Status.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbr.child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String service=snapshot.child(phone).child("service").getValue().toString();
                            if(service!="")
                            {
                                dbr.child("service").child(service).child("service").child("progress").setValue(progress1);
                                dbr.child("service").child(service).child("service").child("date").setValue(date1);
                                dbr.child("service").child(service).child("service").child("description").setValue(description1);
                                dbr.child("service").child(service).child("service").child("damage").setValue(damage1);
                                Toast.makeText(Status.this, "Updated successfully..!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Status.this,Edashboard.class);
                                intent.putExtra("Phone",phone);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Status.this, "You have another phone to service", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}