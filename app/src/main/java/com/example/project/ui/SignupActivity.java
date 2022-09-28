package com.example.project.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity
{
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth,mAuth2;
    private FirebaseDatabase firebaseDatabase;
    public Spinner spinner;
    String role[]= {"user","seller"};
    TextView loginhyperlink;
    Button signup;
    FormValidation formValidation=new FormValidation();
    EditText name,email,password,confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginhyperlink=findViewById(R.id.loginhyperlink);
        name=findViewById(R.id.signup_name);
        password=findViewById(R.id.signup_password);
        email=findViewById(R.id.signup_email);
        confirmpassword=findViewById(R.id.confirm_password);
        spinner=findViewById(R.id.signup_role);
        ArrayAdapter ad=new ArrayAdapter(this, android.R.layout.simple_spinner_item,role);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
//        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
//                .setDatabaseUrl("[https://project-a6fdb-default-rtdb.firebaseio.com/]")
//                .setApiKey("AIzaSyAWpv-FjlJlEL9VUs8GCk-yVU5jKxnMpMU")
//                .setApplicationId("project-a6fdb").build();
//        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "project");
//            mAuth2 = FirebaseAuth.getInstance(myApp);
//        } catch (IllegalStateException e){
//            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("project"));
//        }
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Creating account");
        progressDialog.setMessage("We r creating your account");
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(formValidation.validateName(name.getText().toString(),getApplicationContext())
                        &&formValidation.validatePassword(password.getText().toString(),getApplicationContext())
                        &&formValidation.validateConfirmPassword(confirmpassword.getText().toString(),password.getText().toString(),getApplicationContext())
                        &&formValidation.validateEmail(email.getText().toString(),getApplicationContext())){
                   {
                        progressDialog.show();
                       mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(SignupActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();

                                if(task.isSuccessful())   {
                                    String id=task.getResult().getUser().getUid();
                               User u=new User(id,name.getText().toString(),email.getText().toString(),spinner.getSelectedItem().toString(),password.getText().toString());
                              firebaseDatabase.getReference().child("users").child(id).setValue(u);
                                    AlertDialog alertDialog = new AlertDialog.Builder(SignupActivity.this).create();
                                    alertDialog.setTitle("Signup");
                                    alertDialog.setMessage("Signup successful");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Login",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i=new Intent(SignupActivity.this,LoginActivity.class);
                                                    startActivity(i);
                                                }
                                            });
                                    alertDialog.show();
//                              mAuth2.signOut();
                                }
                           else {
                               Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
            }
        }});
       loginhyperlink.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
           startActivity(intent);

           }
       });

    }


}



