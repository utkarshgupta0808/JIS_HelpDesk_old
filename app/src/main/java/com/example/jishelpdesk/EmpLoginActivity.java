package com.example.jishelpdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EmpLoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnReg, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

        toolbar=findViewById(R.id.toolbar);
        btnReg=findViewById(R.id.reg_button);
        btnLogin=findViewById(R.id.login_button);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EmpLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EmpLoginActivity.this, EmpComplaintActivity.class);
                startActivity(intent);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EmpLoginActivity.this, EmpRegisterActivity.class);
                startActivity(intent);

            }
        });


    }
}