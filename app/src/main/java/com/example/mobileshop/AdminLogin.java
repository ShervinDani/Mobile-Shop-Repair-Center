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

public class AdminLogin extends AppCompatActivity {
    DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
    Button lbtn;
    public int flag1=0;
    public int flag2=0;
    String pno1;
    String pword1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        final EditText pno=findViewById(R.id.email1);
        final EditText pword=findViewById(R.id.password1);

        lbtn=findViewById(R.id.login);
        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pno1=pno.getText().toString();
                pword1=pword.getText().toString();
                if(pno1.isEmpty() || pword1.isEmpty())
                {
                    Toast.makeText(AdminLogin.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dbr.child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(pno1)) {
                                final String fpword = snapshot.child(pno1).child("Password").getValue(String.class);
                                if (fpword.equals(pword1)) {
                                    Toast.makeText(AdminLogin.this, "Login Successfull..!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminLogin.this, Edashboard.class);
                                    intent.putExtra("Phone", pno1);
                                    startActivity(intent);
                                }
                            }
                            else
                            {
                                Toast.makeText(AdminLogin.this, "Invalid Login..!", Toast.LENGTH_SHORT).show();
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