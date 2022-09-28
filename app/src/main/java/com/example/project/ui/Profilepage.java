package com.example.project.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.formvalidation.FormValidation;
import com.example.project.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;

public class Profilepage extends AppCompatActivity {
EditText name,address,city,phoneno,password;
RadioGroup gender;
TextView email;
RadioButton male,female;
String gender1;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    String email1;
Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        name=findViewById(R.id.name2);

        address=findViewById(R.id.address2);
        city=findViewById(R.id.city2);
        phoneno=findViewById(R.id.phoneno);
        gender=findViewById(R.id.gender);
        male=findViewById(R.id.male);
        female=findViewById(R.id.Female);
        email=findViewById(R.id.email);
        update=findViewById(R.id.updatebtn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference1;
  firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1=firebaseDatabase.getReference().child("users");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User u = postSnapshot.getValue(User.class);
                    if(user.getEmail().equals(u.getEmail())){
                        email.setText(u.getEmail());
                        email1=u.getEmail();
                        name.setText(u.getName());
                        Log.d("address",postSnapshot.getKey());
                        String address1=postSnapshot.child("updateprofile").child("address").getValue(String.class);
                        String gender1=postSnapshot.child("updateprofile").child("gender").getValue(String.class);
                        String city1=postSnapshot.child("updateprofile").child("city").getValue(String.class);
                        String phoneno1=postSnapshot.child("updateprofile").child("phoneno").getValue(String.class);
//                        Log.d("address",phoneno1+address1+city1);
                        if(address1 != null){
                            address.setText(address1);
                            phoneno.setText(phoneno1);
                            city.setText(city1);
                            if(gender1.equals("Male")){
                                male.setChecked(true);
                            }
                            else if(gender1.equals("Female")){
                                female.setChecked(true);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profilepage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

// view data
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormValidation formValidation=new FormValidation();
                if(formValidation.validateName(name.getText().toString(),getApplicationContext())
                        &&formValidation.validateAddress(address.getText().toString(),getApplicationContext())
                        &&formValidation.validateCityAndState(city.getText().toString(),getApplicationContext())
                        &&formValidation.validateEmail(email.getText().toString(),getApplicationContext())
                        &&formValidation.validatePhone(phoneno.getText().toString(),getApplicationContext()))
                {
                    if(male.isChecked()){
                        gender1="Male";
                    }
                    else if(female.isChecked()){
                        gender1="Female";

                    }
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(email1.equals(user.getEmail())){
                        HashMap<String, Object> map = new HashMap<>();
                        databaseReference1.child(user.getUid()).child("name").setValue(name.getText().toString());
                        map.put("address", address.getText().toString());
                        map.put("city", city.getText().toString());
                        map.put("gender", gender1);
                        map.put("phoneno", phoneno.getText().toString());
                        databaseReference1.child(user.getUid()).child("updateprofile").updateChildren(map);
                        Toast.makeText(Profilepage.this, "successfully updated", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference2;
        databaseReference2=firebaseDatabase.getReference("users").child(user.getUid());

    }
}
