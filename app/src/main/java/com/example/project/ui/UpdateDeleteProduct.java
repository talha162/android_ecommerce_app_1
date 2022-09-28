package com.example.project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.formvalidation.FormValidation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateDeleteProduct extends AppCompatActivity {
EditText size,color,price,productname,description;
Button update,delete;
    private static final int GALLERY_REQUEST = 1889;
    private static final int GALLERY_REQUEST1 = 1890;
    private static final int GALLERY_REQUEST2 = 1891;
    Uri selectedImage, selectedImage2,selectedImage3;
    ImageView pic1,pic2,pic3;
    FloatingActionButton fab1,fab2,fab3;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseStorage storage;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_product);
        size=findViewById(R.id.size);
        color=findViewById(R.id.color);
        price=findViewById(R.id.price);
        productname=findViewById(R.id.Name);
        description=findViewById(R.id.description);
        update=findViewById(R.id.sell_update);
        delete=findViewById(R.id.sell_delete);
        pic1=findViewById(R.id.imageView);
        pic2=findViewById(R.id.imageView2);
        pic3=findViewById(R.id.imageView3);
        fab1=findViewById(R.id.floatingActionButton);
        fab2=findViewById(R.id.floatingActionButton2);
        fab3=findViewById(R.id.floatingActionButton3);
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        storage= FirebaseStorage.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        // databaseReference=firebaseDatabase.getReference("products");
        databaseReference1=firebaseDatabase.getReference("userproductids");
        databaseReference=firebaseDatabase.getReference("products");
        Bundle b=getIntent().getExtras();
        String price1=b.getString("price");
        String productname1=b.getString("productname");
        String pic11=b.getString("pic1");
        String pic22=b.getString("pic2");
        String pic33=b.getString("pic3");
        String size1=b.getString("size");
        String color1=b.getString("color");
        String description1=b.getString("description");
        String productid=b.getString("productid");
//        Log.d("tsl",pic11);
        price.setText(price1);
        productname.setText(productname1);
        size.setText(size1);
        color.setText(color1);
        description.setText(description1);
        Picasso.get().load(pic11).into(pic1);
        Picasso.get().load(pic22).into(pic2);
        Picasso.get().load(pic33).into(pic3);
        pic1.setTag(R.drawable.shirt1);
        pic2.setTag(R.drawable.shirt1);
        pic3.setTag(R.drawable.shirt1);
//        Log.d("tal5",productid);
update.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FormValidation formValidation = new FormValidation();
        if (formValidation.validateSize(size.getText().toString().toLowerCase(), getApplicationContext())
                && formValidation.validateColor(color.getText().toString(), getApplicationContext()) && formValidation.validateColor(color.getText().toString(), getApplicationContext())
                && formValidation.validateName(productname.getText().toString(), getApplicationContext())
                && formValidation.validatePrice(price.getText().toString(), getApplicationContext())
                && formValidation.validateDescription(description.getText().toString(), getApplicationContext())
        ) {

            int drawableId = (Integer) pic1.getTag();
            if (R.drawable.shirt1 == drawableId) {
                Toast.makeText(getApplicationContext(), "please upload image1", Toast.LENGTH_SHORT).show();
            }
            int drawableId1 = (Integer) pic2.getTag();
            if (R.drawable.shirt1 == drawableId1) {
                Toast.makeText(getApplicationContext(), "please upload image2", Toast.LENGTH_SHORT).show();
            }
            int drawableId2 = (Integer) pic3.getTag();
            if (R.drawable.shirt1 == drawableId2) {
                Toast.makeText(getApplicationContext(), "please upload image3", Toast.LENGTH_SHORT).show();
            }
            if (R.drawable.shirt1 != drawableId && R.drawable.shirt1 != drawableId1 && R.drawable.shirt1 != drawableId2) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
//

                final StorageReference reference = storage.getReference().child("seller").child("products").child(user.getUid()).child("pic1").child(formattedDate);
                reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child(productid).child("pic1").setValue(uri.toString());
//                                databaseReference.child("pic1").push().setValue(uri.toString());
                            }
                        });
                    }
                });
                //pic2
                Calendar c1 = Calendar.getInstance();
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate1 = df1.format(c1.getTime());
                final StorageReference reference1 = storage.getReference().child("seller").child("products").child(user.getUid()).child("pic2").child(formattedDate1);
                reference1.putFile(selectedImage2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                databaseReference.child(productid).child("pic2").setValue(uri.toString());
//                                        databaseReference.child("pic2").push().setValue(uri.toString());
                            }
                        });
                    }
                });
                //pic3

                Calendar c2 = Calendar.getInstance();
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate2 = df2.format(c2.getTime());
                final StorageReference reference3 = storage.getReference().child("seller").child("products").child(user.getUid()).child("pic3").child(formattedDate2);
                reference3.putFile(selectedImage3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child(productid).child("pic3").setValue(uri.toString());
//                                    databaseReference.push().child("pic3").setValue(uri.toString());
//                                    databaseReference.child("pic3").push().setValue(uri.toString());
                            }
                        });
                    }
                });
                String userproductsid = databaseReference1.push().getKey();
                databaseReference.child(productid).child("productname").setValue(productname.getText().toString());
                databaseReference.child(productid).child("price").setValue(price.getText().toString());
                databaseReference.child(productid).child("size").setValue(size.getText().toString());
                databaseReference.child(productid).child("color").setValue(color.getText().toString());
                databaseReference.child(productid).child("description").setValue(description.getText().toString());
                Toast.makeText(UpdateDeleteProduct.this, "successfully updated", Toast.LENGTH_SHORT).show();
                productname.setText("");
                price.setText("");
                size.setText("");
                color.setText("");
                description.setText("");
                pic1.setImageURI(Uri.parse(""));
                pic2.setImageURI(Uri.parse(""));
                pic3.setImageURI(Uri.parse(""));
                Intent i=new Intent(UpdateDeleteProduct.this,MainSeller.class);
                startActivity(i);
            }
        }
    }

});
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormValidation formValidation=new FormValidation();
                if(formValidation.validateSize(size.getText().toString().toLowerCase(),getApplicationContext())
                        &&formValidation.validateColor(color.getText().toString(),getApplicationContext()) &&formValidation.validateColor(color.getText().toString(),getApplicationContext())
                        &&formValidation.validateName(productname.getText().toString(),getApplicationContext())
                        &&formValidation.validatePrice(price.getText().toString(),getApplicationContext())
                        &&formValidation.validateDescription(description.getText().toString(),getApplicationContext())
                )
                {
                    databaseReference.child(productid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot s:snapshot.getChildren()){
                                if(s.getKey()!=null){
                                    s.getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    databaseReference1.orderByChild("productid").equalTo(productid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds:snapshot.getChildren()){
                                if(ds.getKey()!=null){
                                    ds.getRef().removeValue();
                                    Toast.makeText(UpdateDeleteProduct.this, "successfully deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),MainSeller.class);
                                    startActivity(intent);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery1();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery2();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery3();
            }
        });

    }
    private void pickFromGallery1(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST);
    }
    private void pickFromGallery2(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST1);
    }
    private void pickFromGallery3(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST2);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    pic1.setTag(0);
                    pic1.setImageURI(selectedImage);
                    break;
                case GALLERY_REQUEST1:
                    //data.getData returns the content URI for the selected Image
                    selectedImage2 = data.getData();
                    pic2.setTag(0);
                    pic2.setImageURI(selectedImage2);
                    break;
                case GALLERY_REQUEST2:
                    //data.getData returns the content URI for the selected Image
                   selectedImage3 = data.getData();
                    pic3.setTag(0);
                    pic3.setImageURI(selectedImage3);
                    break;
            }
    }
}