package com.example.jishelpdesk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class ComplaintEmpActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnLogout;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    EmpAdapter empAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_emp);

        toolbar = findViewById(R.id.toolbar);
        btnLogout = findViewById(R.id.btn_logout);
        recyclerView = findViewById(R.id.emp_recyclerview);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintEmpActivity.this);
                builder.setMessage("Are you sure you want to logout?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                logOut();
                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });


    }

    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {
        Intent intent = new Intent(ComplaintEmpActivity.this, EmpLoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(ComplaintEmpActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
    }
}