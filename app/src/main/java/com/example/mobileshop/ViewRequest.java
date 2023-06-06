package com.example.mobileshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ViewRequest extends AppCompatActivity {
    long maxid=1;
    Button btn1;
    TextView t1,t2,t3,t4,t5,t6;
    String pn1;
    String pn2;
    String phone;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
        Intent intent = getIntent();
        phone = intent.getStringExtra("Phone");
        t1=findViewById(R.id.textView5);
        t2=findViewById(R.id.textView6);
        t3=findViewById(R.id.textView7);
        t4=findViewById(R.id.textView8);
        t5=findViewById(R.id.textView9);
        t6=findViewById(R.id.textView10);
        btn1=findViewById(R.id.button1);

        dbr.child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    dbr.child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChildren())
                            {
                                for(DataSnapshot child: snapshot.getChildren())
                                {
                                    String name=child.child("Name").getValue(String.class);
                                    String phone1=child.child("Phone").getValue(String.class);
                                    String add=child.child("Address").getValue(String.class);
                                    String brand=child.child("Brand").getValue(String.class);
                                    String model=child.child("Model").getValue(String.class);
                                    String description=child.child("Description").getValue(String.class);
                                    t1.setText(name);
                                    t2.setText(phone1);
                                    t3.setText(brand);
                                    t4.setText(model);
                                    t5.setText(description);
                                    t6.setText(add);
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {

                        t1.setText("  none");
                        t2.setText("  none");
                        t3.setText("  none");
                        t4.setText("  none");
                        t5.setText("  none");
                        t6.setText("  none");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbr.child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(phone)) {
                            String uphone = snapshot.child(phone).child("service").getValue().toString();
                            if (uphone != "") {
                                flag = true;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if (flag == false)
                {
                    dbr.child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()) {
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    String name = child.child("Name").getValue(String.class);
                                    String phone1 = child.child("Phone").getValue(String.class);
                                    String add = child.child("Address").getValue(String.class);
                                    String brand = child.child("Brand").getValue(String.class);
                                    String model = child.child("Model").getValue(String.class);
                                    pn2 = phone1;
                                    String description = child.child("Description").getValue(String.class);
                                    dbr.child("service").child(phone1).child("Name").setValue(name);
                                    dbr.child("service").child(phone1).child("Phone").setValue(phone1);
                                    dbr.child("service").child(phone1).child("Address").setValue(add);
                                    dbr.child("service").child(phone1).child("Brand").setValue(brand);
                                    dbr.child("service").child(phone1).child("Model").setValue(model);
                                    dbr.child("service").child(phone1).child("Description").setValue(description);
                                    dbr.child("employee").child(phone).child("service").setValue(phone1);
                                    dbr.child("service").child(phone1).child("status").setValue("");
                                    dbr.child("token").child(phone1).removeValue();
                                    break;
                                }
                                DatabaseReference parentRef = dbr.child("token");
                                Query childQuery = parentRef.orderByKey();
                                childQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot snapshot) {
                                        // Iterate over each child node and update the "token" field
                                        for (DataSnapshot child : snapshot.getChildren()) {
                                            String pno = child.getKey();
                                            Map<String, Object> updates = new HashMap<>();
                                            updates.put("token", maxid++);
                                            DatabaseReference childRef = parentRef.child(pno);
                                            childRef.updateChildren(updates, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError error, DatabaseReference ref) {
                                                    if (error == null) {
                                                    } else {
                                                    }
                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                    }
                                });

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Intent intent=new Intent(ViewRequest.this,Edashboard.class);
                    intent.putExtra("Phone", phone);
                    startActivity(intent);
            }
                else
                {
                    Toast.makeText(ViewRequest.this, "You already have a mobile to service", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ViewRequest.this,Edashboard.class);
                    intent.putExtra("Phone", phone);
                    startActivity(intent);
                }
            }
        });
    }
}