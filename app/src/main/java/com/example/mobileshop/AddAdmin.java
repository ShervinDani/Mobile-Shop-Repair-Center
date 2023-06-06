package com.example.mobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;


public class AddAdmin extends AppCompatActivity {
    Button sbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);
        final EditText uname=findViewById(R.id.name);
        final EditText email=findViewById(R.id.email1);
        final EditText pword=findViewById(R.id.password1);
        final EditText address=findViewById(R.id.homeaddress);
        final EditText pno=findViewById(R.id.phoneno);
        sbtn=findViewById(R.id.signupbtn);
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uname1=uname.getText().toString();
                final String email1=email.getText().toString();
                final String pword1=pword.getText().toString();
                final String address1=address.getText().toString();
                final String pno1=pno.getText().toString();
                if(uname1.isEmpty() || email1.isEmpty() || pword1.isEmpty() || address1.isEmpty() || pno1.isEmpty())
                {
                    Toast.makeText(AddAdmin.this,"Please fill all details",Toast.LENGTH_SHORT).show();
                }
                else if(pword1.length()<6)
                {
                    Toast.makeText(AddAdmin.this,"Password must be alteast 6 characters",Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
                {
                    Toast.makeText(AddAdmin.this,"Enter valid email",Toast.LENGTH_SHORT).show();
                }
                else if(pno1.toString().length()!=10)
                {
                    Toast.makeText(AddAdmin.this,"Enter valid phone number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbr.child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(pno1))
                            {
                                Toast.makeText(AddAdmin.this,"Phone number is already registered !",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                dbr.child("employee").child(pno1).child("Name").setValue(uname1,1);
                                dbr.child("employee").child(pno1).child("Password").setValue(pword1,2);
                                dbr.child("employee").child(pno1).child("Address").setValue(address1,4);
                                dbr.child("employee").child(pno1).child("Email").setValue(email1,3);
                                dbr.child("employee").child(pno1).child("service").setValue("",5);
                                Toast.makeText(AddAdmin.this,"Registered Successfully..!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(AddAdmin.this,LoginPage.class);
                                startActivity(intent);
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