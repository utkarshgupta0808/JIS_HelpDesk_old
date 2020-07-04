package com.example.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class EmpLoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnLogin;
    TextView resendOTP;
    EditText mobileNumber ,otp;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    CountryCodePicker countryCodePicker;
    String verficationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

        firebaseAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.toolbar);
        countryCodePicker=findViewById(R.id.ccp);

        btnLogin=findViewById(R.id.login_button);
        resendOTP=findViewById(R.id.resend);
        otp=findViewById(R.id.otp);
        progressBar=findViewById(R.id.progress_bar);
        mobileNumber=findViewById(R.id.number);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verificationInProgress){
                    String numberMob=mobileNumber.getText().toString();
                    if (!numberMob.isEmpty() && numberMob.length()==10){
                        String number=countryCodePicker.getSelectedCountryCodeWithPlus()+numberMob;
                        progressBar.setVisibility(View.VISIBLE);
                        otp.setVisibility(View.VISIBLE);
                        requestOTP(number);
                        }
                    else {
                        mobileNumber.setError("Number is not valid");
                    }
                }
                else {
                    String userOTP=otp.getText().toString();
                    if(!userOTP.isEmpty() && userOTP.length()==6){
                        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verficationId,userOTP);
                        progressBar.setVisibility(View.VISIBLE);
                        verifyAuth(phoneAuthCredential);
                    }
                    else {
                        otp.setError("Enter the valid OTP");
                    }
                }
            }
        });




    }

    private void verifyAuth(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(EmpLoginActivity.this, "Authentication is Succesfull", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(EmpLoginActivity.this, "Authenticationa Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void requestOTP(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                verficationId=s;
                token=forceResendingToken;
                btnLogin.setText("Verify");
                verificationInProgress=true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(EmpLoginActivity.this, "Cannot create account "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}