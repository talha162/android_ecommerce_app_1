package com.example.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project.R;
import com.example.project.formvalidation.FormValidation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Address extends AppCompatActivity {
    EditText name,phone,address,city,postalcode;
    Button continue_to_payment;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        name=findViewById(R.id.name2);
        phone=findViewById(R.id.ad_phone_number);
        address=findViewById(R.id.address2);
        city=findViewById(R.id.city2);
        postalcode=findViewById(R.id.postalcode2);
        name.getText().toString();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("address");
        continue_to_payment=findViewById(R.id.continue_to_payment);
Bundle b=getIntent().getExtras();
        int totalPrice=b.getInt("totalprice");
        continue_to_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormValidation formValidation = new FormValidation();
                if (formValidation.validateName(name.getText().toString(), getApplicationContext())
                        && formValidation.validateAddress(address.getText().toString(), getApplicationContext())
                        && formValidation.validateCityAndState(city.getText().toString(), getApplicationContext())
                        && formValidation.validatePostalCode(postalcode.getText().toString(), getApplicationContext())
                        && formValidation.validatePhone(phone.getText().toString(), getApplicationContext())) {
                    String key=databaseReference.push().getKey();
                    databaseReference.child(key).child("name").setValue(name.getText().toString());
                    databaseReference.child(key).child("address").setValue(address.getText().toString());
                    databaseReference.child(key).child("city").setValue(city.getText().toString());
                    databaseReference.child(key).child("postalcode").setValue(postalcode.getText().toString());
                    databaseReference.child(key).child("phoneno").setValue(phone.getText().toString());
                    Intent i = new Intent(getApplicationContext(), CheckOut.class);
                    Bundle b = new Bundle();
                    b.putInt("totalprice", totalPrice);
                    i.putExtras(b);
                    startActivity(i);

                }
            }
        });
    }
}