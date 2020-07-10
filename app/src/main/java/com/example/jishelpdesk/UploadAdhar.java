package com.example.jishelpdesk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class UploadAdhar extends AppCompatActivity {

    Button btnChoose, btnUpload;
    ImageView imageView;
    Toolbar toolbar;
    private Uri filePath;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_adhar);

        toolbar=findViewById(R.id.toolbar);
        btnChoose=findViewById(R.id.btn_choose);
        btnUpload=findViewById(R.id.btn_upload);
        imageView=findViewById(R.id.img_aadhar);

        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1&& resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filePath=data.getData();

            try {
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage() {

        if (filePath!=null){

            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference reference=storageReference.child("Aadhar_Card/" + UUID.randomUUID().toString());
            reference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();
                            Toast.makeText(UploadAdhar.this, "Aadhar uploaded", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(UploadAdhar.this,EmpRegisterActivity.class);
                            intent.putExtra("upload","Aadhar Uploaded");
                            startActivity(intent);
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            });
        }

    }

    private void chooseImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
    }
}