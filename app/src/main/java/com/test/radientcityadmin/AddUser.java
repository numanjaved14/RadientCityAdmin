package com.test.radientcityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.radientcityadmin.Models.User;

import java.util.HashMap;

public class AddUser extends AppCompatActivity {

    EditText uName, uEmail, uPass, uAddress;
    Button btnSubmit;

    private String address = "";
    private String name = "";
    private String email = "";
    private String password = "";

    private FirebaseAuth mAuth;
    FirebaseDatabase dataBase;
    DatabaseReference firebaseRef;

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
                name = uName.getText().toString().trim();
                address = uAddress.getText().toString().trim();
                email = uEmail.getText().toString().trim();
                password = uPass.getText().toString().trim();

                requestRegistration(email, password);
            }
        });
    }

    private void requestRegistration(String mail, final String pass) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    FirebaseUser fUser = mAuth.getCurrentUser();
                    assert fUser != null;
                    final String userId = fUser.getUid();
                    System.out.println("User ID is " + userId);
                    firebaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    HashMap<String, String> hashmap = new HashMap<>();
                    hashmap.put("firebaseId", userId);
                    hashmap.put("userName", name);
                    hashmap.put("userMail", email);
                    hashmap.put("address", address);
                    hashmap.put("password", pass);
                    hashmap.put("type", "user");
                    Log.i("PYC_LOG", "Sending Params " + hashmap);
                    firebaseRef.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddUser.this, "User Registered Successfully!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AddUser.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(AddUser.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
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