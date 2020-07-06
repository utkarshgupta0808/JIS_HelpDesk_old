package com.example.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class ComplaintInfoEmpActivity extends AppCompatActivity {


    Toolbar toolbar;
    TextView tokenText, nameText, addressText, numberText, dateText, complaintText;
    Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_info_emp);

        toolbar = findViewById(R.id.toolbar);
        tokenText = findViewById(R.id.tokenid);
        nameText = findViewById(R.id.name);
        dateText = findViewById(R.id.date);
        complaintText = findViewById(R.id.complaint);
        numberText = findViewById(R.id.number);
        addressText = findViewById(R.id.address);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        extra = getIntent().getExtras();

        if (extra != null) {
            tokenText.setText(extra.getString("tokenId"));
            nameText.setText(extra.getString("name"));
            dateText.setText(extra.getString("date"));
            complaintText.setText(extra.getString("complaint"));
            addressText.setText(extra.getString("address"));
            numberText.setText(extra.getString("number1"));
        }


    }

}