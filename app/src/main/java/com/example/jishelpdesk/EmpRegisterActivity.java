package com.example.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EmpRegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnReg, btnReset;
    TextView eName, eAddress, ePanNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_register);

        toolbar=findViewById(R.id.toolbar);
        btnReg=findViewById(R.id.btn_reg);
        btnReset=findViewById(R.id.btn_reset);
        eName=findViewById(R.id.emp_name);
        eAddress=findViewById(R.id.emp_address);
        ePanNumber=findViewById(R.id.emp_pan);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EmpRegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EmpRegisterActivity.this, EmpComplaintActivity.class);
                startActivity(intent);
                resetFields();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();
            }
        });
    }
    public void resetFields(){

        ePanNumber.setText("");
        eAddress.setText("");
        eName.setText("");

    }
}