package com.example.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class ComplaintInfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tokenText, nameText, addressText, numberText, dateText, complaintText, statusText;
    Bundle extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_info);

        toolbar=findViewById(R.id.toolbar);
        tokenText=findViewById(R.id.tokenid);
        nameText=findViewById(R.id.name);
        statusText=findViewById(R.id.status);
        dateText=findViewById(R.id.date);
        complaintText=findViewById(R.id.complaint);
        numberText=findViewById(R.id.number);
        addressText=findViewById(R.id.address);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        extra=getIntent().getExtras();

        if(extra != null){
            tokenText.setText(extra.getString("tokenId"));
            nameText.setText(extra.getString("name"));
            dateText.setText(extra.getString("date"));
            statusText.setText(extra.getString("status"));
            complaintText.setText(extra.getString("complaint"));
            addressText.setText(extra.getString("address"));
            numberText.setText(extra.getString("number1"));
        }

    }
}