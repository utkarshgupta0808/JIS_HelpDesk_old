package com.example.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Layout;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.EventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ComplaintActivity extends AppCompatActivity {

    EditText complaintText, numberText, nameText, addressText;
    Button btn_submit, btn_reset;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    Map<String, Object> user = new HashMap<>();
    FirebaseFirestore db;
    String cDate, tokenString;
    Calendar calendar;
    String currentDate;
    Long c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();




        complaintText=findViewById(R.id.complaint);

        nameText=findViewById(R.id.name);
        numberText=findViewById(R.id.mobile);
        addressText=findViewById(R.id.address);
        btn_submit=findViewById(R.id.buttonSubmit);
        btn_reset=findViewById(R.id.buttonReset);



        complaintText.setMovementMethod(new ScrollingMovementMethod());





        btn_submit.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            btn_submit.setEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String cName= nameText.getText().toString().trim();
                final String cMobile= numberText.getText().toString().trim();
                final String cAddress= addressText.getText().toString().trim();
                final String cComplaint= complaintText.getText().toString().trim();

                calendar= Calendar.getInstance();
                currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


                cDate=""+ currentDate;




                if (TextUtils.isEmpty(cName)){
                    nameText.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(cMobile)){
                    numberText.setError("Mobile Number is Required");
                    return;
                }
                if (TextUtils.isEmpty(cAddress)){
                    addressText.setError("Address is Required");
                    return;
                }
                if (TextUtils.isEmpty(cComplaint)){
                    complaintText.setError("Please provide us your complaint");
                    return;
                }
                showProgress();
                db=FirebaseFirestore.getInstance();
                db.collection("Counter").document("123456789")
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();

                            c= Objects.requireNonNull(documentSnapshot).getLong("countComplaint");

                            tokenString="9211"+c;
                            c=c+1;


                            user.put("tokenId", tokenString);
                            user.put("name", cName);
                            user.put("mobile", cMobile);
                            user.put("status", "Unassigned");
                            user.put("address", cAddress);
                            user.put("complaint", cComplaint);
                            user.put("date", cDate);

                            DocumentReference docRef =FirebaseFirestore.getInstance()
                                    .collection("Counter").document("123456789");

                            Map<String, Object>map1=new HashMap<>();
                            map1.put("count", c);

                            docRef.update(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ComplaintActivity.this, "", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ComplaintActivity.this,"", Toast.LENGTH_SHORT).show();
                                }
                            });

                            db.collection("Complaint")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(ComplaintActivity.this, "Complaint Registered with token id "+ tokenString, Toast.LENGTH_SHORT).show();
                                            resetFields();

                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ComplaintActivity.this,
                                                    "Error occurred in registering your complaint! Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            onSend(view);

                            progressDialog.dismiss();






                        }
                        else {
                            Toast.makeText(ComplaintActivity.this, "Error : " + Objects.requireNonNull(task.getException())
                                    .getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });





// Add a new document with a generated ID





            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFields();

            }
        });


    }
    public void onSend(View view){
        String mobileNumber = numberText.getText().toString();
        String message= "Your complaint has been registered with tokenId "+ tokenString+". Our executive will reach you soon. ThankYou";

        if(mobileNumber.length()==0){
            return;
        }
        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager= SmsManager.getDefault();
            smsManager.sendTextMessage(mobileNumber, null, message, null, null);
            Toast.makeText(this, "Message Sent!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
    public void resetFields(){
        nameText.setText("");
        numberText.setText("");
        addressText.setText("");
        complaintText.setText("");
    }
    private void showProgress() {
        Context context;
        progressDialog = new ProgressDialog(ComplaintActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}