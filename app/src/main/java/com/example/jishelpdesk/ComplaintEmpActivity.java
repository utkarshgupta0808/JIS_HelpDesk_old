package com.example.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Objects;


public class ComplaintEmpActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageButton btnLogout, btnProfile;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    ComplaintEmpAdapter complaintEmpAdapter;
    long countActive, countTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_emp);

        toolbar = findViewById(R.id.toolbar);
        btnLogout = findViewById(R.id.btn_logout);
        recyclerView = findViewById(R.id.complaint_emp_recyclerview);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnProfile=findViewById(R.id.btn_profile);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        DocumentReference documentReference = firebaseFirestore.collection("Employee").document(Objects.requireNonNull(mAuth.getUid()));
        Query query = documentReference.collection("Complaint");
//        Query query= firebaseFirestore.collection("Complaint").orderBy("tokenId", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ComplaintModel> options= new FirestoreRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(query, ComplaintModel.class).build();
        complaintEmpAdapter=new ComplaintEmpAdapter(options);
        complaintEmpAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(complaintEmpAdapter);



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Employee").document(mAuth.getUid())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            countActive=documentSnapshot.getLong("cActive");
                            countTotal=documentSnapshot.getLong("ctotal");
                        }
                        else {

                        }
                    }
                });
                DocumentReference documentReference=firebaseFirestore.collection("Employee").document(mAuth.getUid());
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            assert documentSnapshot != null;
                            if(documentSnapshot.exists()){

                                Intent intent=new Intent(ComplaintEmpActivity.this, EmpProfileActivity.class);
                                Bundle extra=new Bundle();
                                extra.putString("name", (String) documentSnapshot.get("name"));
                                extra.putString("address", (String) documentSnapshot.get("address"));
                                extra.putString("pan", (String) documentSnapshot.get("pan"));
                                extra.putLong("empid", (Long) documentSnapshot.get("empid"));
                                extra.putString("number", (String) documentSnapshot.get("number"));
                                extra.putString("aadhaar", (String) documentSnapshot.get("aadhaar"));
                                extra.putLong("cActive",  countActive);
                                extra.putLong("cTotal", countTotal);
                                extra.putBoolean("stat",false);
                                intent.putExtras(extra);
                                startActivity(intent);

                            }



                        }
                        else {

                        }
                    }
                });

            }
        });


    }
    @Override
    protected void onStop() {
        super.onStop();
        complaintEmpAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        complaintEmpAdapter.startListening();
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
    void showLogoutDialog(){
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        View view1=layoutInflater.inflate(R.layout.alert_dialog,null);
        Button yesButton=view1.findViewById(R.id.btn_yes);
        Button cancelButton=view1.findViewById(R.id.btn_cancel);

        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setView(view1)
                .create();
        alertDialog.show();

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();
            }
        });

    }
}