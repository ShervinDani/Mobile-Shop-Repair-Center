package com.example.mobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Servicerequest extends AppCompatActivity {
    Button sbtn;
    long maxid;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicerequest);
        final EditText brand=findViewById(R.id.Brand);
        final EditText model=findViewById(R.id.Model);
        final EditText add=findViewById(R.id.address);
        final EditText pno=findViewById(R.id.phone);
        final EditText des=findViewById(R.id.description);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("Phone");
        sbtn=findViewById(R.id.submit);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String brand1=brand.getText().toString();
                final String model1=model.getText().toString();
                final String add1=add.getText().toString();
                final String des1=des.getText().toString();
                final String pno1=pno.getText().toString();
                if(brand1.isEmpty() || model1.isEmpty() || add1.isEmpty() || des1.isEmpty())
                {
                    Toast.makeText(Servicerequest.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbr.child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            maxid=snapshot.getChildrenCount();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    });
                    dbr.child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(pno1))
                            {
                                flag=true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    dbr.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(pno1) && flag==false)
                            {
                                final String uname=snapshot.child(pno1).child("Name").getValue(String.class);
                                final String email=snapshot.child(pno1).child("Email").getValue(String.class);
                                dbr.child("token").child(pno1).child("Name").setValue(uname,1);
                                dbr.child("token").child(pno1).child("Phone").setValue(pno1,2);
                                dbr.child("token").child(pno1).child("Email").setValue(email,3);
                                dbr.child("token").child(pno1).child("Brand").setValue(brand1,4);
                                dbr.child("token").child(pno1).child("Model").setValue(model1,5);
                                dbr.child("token").child(pno1).child("Description").setValue(des1,6);
                                dbr.child("token").child(pno1).child("Address").setValue(add1,7);
                                dbr.child("token").child(pno1).child("Flag").setValue(0,8);
                                dbr.child("token").child(pno1).child("status").setValue("",8);
                                dbr.child("token").child(pno1).child("token").setValue(maxid+1,8);
                                Toast.makeText(Servicerequest.this, "Submitted SuccessFully..!", Toast.LENGTH_SHORT).show();
                                dbr.child("service").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild(pno1))
                                        {
                                            dbr.child("service").child(pno1).removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                Intent intent=new Intent(Servicerequest.this,Dashboard.class);
                                intent.putExtra("Phone", phone);
                                startActivity(intent);
                            }
                            else if(flag==true)
                            {
                                Toast.makeText(Servicerequest.this, "You have already registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Servicerequest.this, "Enter Registered Mobile Number", Toast.LENGTH_SHORT).show();
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