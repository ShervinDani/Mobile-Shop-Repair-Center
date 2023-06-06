package com.example.mobileshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {
    Button rbtn1;
    Button rbtn2;
    Button rbtn3;
    Button rbtn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        rbtn1=findViewById(R.id.rbtn);
        rbtn2=findViewById(R.id.rbtn1);
        rbtn3=findViewById(R.id.rbtn2);
        rbtn4=findViewById(R.id.rbtn3);
        Intent intent = getIntent();
        String phone = intent.getStringExtra("Phone");

        rbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,Servicerequest.class);
                intent.putExtra("Phone", phone);
                startActivity(intent);
            }
        });
        rbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,ViewToken1.class);
                intent.putExtra("Phone", phone);
                startActivity(intent);
            }
        });
        rbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,ViewStatus.class);
                intent.putExtra("Phone",phone);
                startActivity(intent);
            }
        });
        rbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,ViewReport.class);
                intent.putExtra("Phone",phone);
                startActivity(intent);
            }
        });
    }
}