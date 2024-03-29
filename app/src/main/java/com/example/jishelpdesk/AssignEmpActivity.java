package com.example.jishelpdesk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

public class AssignEmpActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EmpAdapter empAdapter;
    static String  name, date, number, status, complaint, address;
    static long tokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_emp);

        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.emp_recyclerview);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.emp_recyclerview);
        firebaseFirestore= FirebaseFirestore.getInstance();

        Query query= firebaseFirestore.collection("Employee").orderBy("empid", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<EmpModel> options= new FirestoreRecyclerOptions.Builder<EmpModel>()
                .setQuery(query, EmpModel.class).build();
        empAdapter=new EmpAdapter(options);
        empAdapter.notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(empAdapter);

        Bundle extra=getIntent().getExtras();

        assert extra != null;
        tokenId=extra.getLong("tokenId");
        name=extra.getString("name");
        date=extra.getString("date");
        status=extra.getString("status");
        complaint=extra.getString("complaint");
        address=extra.getString("address");
        number=extra.getString("number1");


    }

    @Override
    protected void onStart() {
        super.onStart();
        empAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        empAdapter.stopListening();

    }
}
