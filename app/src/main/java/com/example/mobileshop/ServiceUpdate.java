package com.example.mobileshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.MoreObjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ServiceUpdate extends AppCompatActivity {
    Button btn;
    String service=null;
    DatabaseReference dbr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://mshop-a2e23-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_update);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("Phone");
        final EditText id=findViewById(R.id.editTextTextPersonName1);
        final EditText parts=findViewById(R.id.editTextTextPersonName2);
        final EditText war=findViewById(R.id.editTextTextPersonName3);
        final EditText cost=findViewById(R.id.editTextTextPersonName4);
        btn=findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id1=id.getText().toString();
                String parts1=parts.getText().toString();
                String war1=war.getText().toString();
                String cost1=cost.getText().toString();
                if(id1.isEmpty() || parts1.isEmpty() || war1.isEmpty() || cost1.isEmpty())
                {
                    Toast.makeText(ServiceUpdate.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                        dbr.child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(phone))
                                {
                                    service = snapshot.child(phone).child("service").getValue(String.class);
                                }
                                if (service != "")
                                {
                                    dbr.child("service").child(service).child("report").child("cost").setValue(cost1);
                                    dbr.child("service").child(service).child("report").child("warranty").setValue(war1);
                                    dbr.child("service").child(service).child("report").child("id").setValue(id1);
                                    dbr.child("service").child(service).child("report").child("parts").setValue(parts1);
                                    dbr.child("employee").child(phone).child("service").setValue("");
                                    Intent intent=new Intent(ServiceUpdate.this,Edashboard.class);
                                    intent.putExtra("Phone",phone);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(ServiceUpdate.this, "You not accepted any mobile", Toast.LENGTH_SHORT).show();
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