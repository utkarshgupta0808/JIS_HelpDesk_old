package com.example.jishelpdesk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.Query.Direction;
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

public class ComplaintAdminActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button button;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    ComplaintAdminAdapter complaintAdminAdapter;

//    DocumentReference documentReference;
//    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_admin);

        toolbar=findViewById(R.id.toolbar);
        button=findViewById(R.id.btn_logout);
        mAuth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.complaint_recyclerview);
//        userId = getIntent().getStringExtra("user_id");
        firebaseFirestore= FirebaseFirestore.getInstance();


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);







//        documentReference = firebaseFirestore.collection("Complaint").document("userId");
        Query query= firebaseFirestore.collection("Complaint").orderBy("tokenId", Direction.DESCENDING);
        FirestoreRecyclerOptions<ComplaintModel> options= new FirestoreRecyclerOptions.Builder<ComplaintModel>()
                .setQuery(query, ComplaintModel.class).build();
        complaintAdminAdapter=new ComplaintAdminAdapter(options);
        complaintAdminAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(complaintAdminAdapter);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintAdminActivity.this);
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
        Intent intent = new Intent(ComplaintAdminActivity.this, AdminLoginActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(ComplaintAdminActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        complaintAdminAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        complaintAdminAdapter.startListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,WelcomePageActivity.class);
        startActivity(intent);
        finish();
    }
}