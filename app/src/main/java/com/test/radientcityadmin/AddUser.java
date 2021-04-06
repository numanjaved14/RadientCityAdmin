package com.test.radientcityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.test.radientcityadmin.Models.User;

public class AddUser extends AppCompatActivity {

    EditText uName, uEmail, uPass, uAddress;
    Button btnSubmit;

    private FirebaseAuth mAuth;
    FirebaseDatabase dataBase;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mAuth = FirebaseAuth.getInstance();
        dataBase = FirebaseDatabase.getInstance();

        initializeViews();
        registerUser();

    }

    private void registerUser() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                mAuth.createUserWithEmailAndPassword
                        (uEmail.getText().toString(), uPass.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {

                                    User user = new User(uName.getText().toString(),
                                            uEmail.getText().toString(),
                                            uPass.getText().toString(),
                                            uAddress.getText().toString());

                                    String id = task.getResult().getUser().getUid();
                                    dataBase.getReference().child("Users").child(id).setValue(user);

                                    Toast.makeText(AddUser.this, "User Registered Successfully!",
                                            Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(AddUser.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }

    private void initializeViews() {
        uName = findViewById(R.id.u_name);
        uEmail = findViewById(R.id.u_email);
        uPass = findViewById(R.id.u_pass);
        uAddress = findViewById(R.id.u_address);
        btnSubmit = findViewById(R.id.submit);
        progressDialog = new ProgressDialog(AddUser.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are Registering the account");
    }
}