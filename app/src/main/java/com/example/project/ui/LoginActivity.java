package com.example.project.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.formvalidation.FormValidation;
import com.example.project.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
// ...
    public Spinner spinner,login_role;
    String role[]= {"user","seller"};
    TextView signuphyperlink;
    EditText emailetv, passwordetv;
    Button login;
    User u=new User();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailetv=findViewById(R.id.login_email);
        passwordetv=findViewById(R.id.login_password);
        login=findViewById(R.id.loginb);
        signuphyperlink=findViewById(R.id.signuphyperlink);
        spinner=findViewById(R.id.signup_role);
        login_role=findViewById(R.id.signup_role);
        ArrayAdapter ad=new ArrayAdapter(this, android.R.layout.simple_spinner_item,role);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("logging in");
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();





login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FormValidation formValidation=new FormValidation();
        if(formValidation.validateEmail(emailetv.getText().toString(),getApplicationContext())&&formValidation.validatePassword(passwordetv.getText().toString(),getApplicationContext())){
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(emailetv.getText().toString(),passwordetv.getText().toString()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("email",emailetv.getText().toString());
                    if(task.isSuccessful()){
                        Log.d("email2",emailetv.getText().toString());
                        databaseReference=firebaseDatabase.getReference("users");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("role1",snapshot.getKey());
                                for(DataSnapshot ds:snapshot.getChildren()){
                                    u= ds.getValue(User.class);
                                    Log.d("role2",login_role.getSelectedItem().toString()+u.getRole().equals("seller"));
                                    if(u.getEmail().equals(emailetv.getText().toString())&&u.getPassword().equals(passwordetv.getText().toString())) {
                                        Log.d("role4",login_role.getSelectedItem().toString()+u.getRole().equals("seller"));

                                        Log.d("role3",u.getRole()+ds.getValue());
                                        if (login_role.getSelectedItem().toString().equals("user")&&u.getRole().equals("user")) {
                                            Log.d("yaar",u.getRole()+u.getEmail());
                                            Intent intent = new Intent(LoginActivity.this, MainUsers.class);
                                            startActivity(intent);
                                        }

                                        else if (login_role.getSelectedItem().toString().equals("seller")&&u.getRole().equals("seller")) {
                                            Log.d("yaar1",u.getRole()+u.getEmail());
                                            Intent intent = new Intent(LoginActivity.this, MainSeller.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginActivity.this, "Wrong data entered", Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            });

        }
    }
});

        signuphyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }

}