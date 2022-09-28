package com.example.project.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class welcome extends AppCompatActivity {
    Button login;
    Button signup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        login=findViewById(R.id.loginbutton);
        signup=findViewById(R.id.signupbutton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(welcome.this,LoginActivity.class);
                startActivity(intent);
            }

        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(welcome.this, SignupActivity.class);
                startActivity(intent1);
            };
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(welcome.this);
        progressDialog.setTitle("");
        progressDialog.setMessage("loading...");
        DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Log.d("talha","user null");
        }
        if (user != null) {
            Log.d("talha",user.getEmail());
            progressDialog.show();
            String email = user.getEmail();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        User u= ds.getValue(User.class);
                        if(u.getEmail().equals(email)){
                            if(u.getRole().equals("user")){
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), MainUsers.class);
                                startActivity(intent);
                            }
                            else if(u.getRole().equals("seller")){
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), MainSeller.class);
                                startActivity(intent);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}