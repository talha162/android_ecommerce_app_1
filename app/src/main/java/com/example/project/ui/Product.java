package com.example.project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.models.SlideModel;

import com.example.project.R;
import com.example.project.adapters.ImageAdapter;
import com.example.project.adapters.ReviewAdapter;
import com.example.project.models.ReviewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Product extends AppCompatActivity {
    String profilepic,username;
    ArrayList<ReviewModel> list =new ArrayList<>();
    RecyclerView recyclerView;
    TextView writereview;
    TextView price,productname,color,size,description;
    ImageView favoritebtn;
    Button cart;
    Boolean clicked;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1,databaseReference3,databaseReference4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
price=findViewById(R.id.priceans);
productname=findViewById(R.id.productname);
        recyclerView =findViewById(R.id.recyclerview);
        writereview=findViewById(R.id.writeyourreview);
        favoritebtn=findViewById(R.id.favoritebtn);
        cart=findViewById(R.id.addtocart);
        clicked=false;
        color=findViewById(R.id.colorans);
        price=findViewById(R.id.priceans);
        productname=findViewById(R.id.productname);
        description=findViewById(R.id.detailans);
        size=findViewById(R.id.sizeans);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("carts");
        databaseReference1=firebaseDatabase.getReference("favorite").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
Bundle b=getIntent().getExtras();
String price1=b.getString("price");
String productname1=b.getString("productname");
String pic1=b.getString("pic1");
String pic2=b.getString("pic2");
String pic3=b.getString("pic3");
String size1=b.getString("size");
String color1=b.getString("color");
String description1=b.getString("description");
String productid=b.getString("productid");
//        SharedPreferences settings = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
//                                    settings.edit().clear().commit();
price.setText(price1);
productname.setText(productname1);
size.setText(size1);
color.setText(color1);
description.setText(description1);

        Query query = databaseReference1.orderByChild("productid").equalTo(productid);
Log.d("pid",productid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()) {

                Boolean status=ds.child("status").getValue(Boolean.class);
                if(status!=null){
                    if(status){
                        clicked=true;
                        favoritebtn.setImageResource(R.drawable.favorite1);
                    }
                }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        favoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clicked){
                    Query query1 = databaseReference1.orderByChild("productid").equalTo(productid);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot s: dataSnapshot.getChildren()) {
                                clicked=false;
                                favoritebtn.setImageResource(R.drawable.favorite);
                                s.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else{
                    clicked=true;
                    String key=databaseReference1.push().getKey();
                    databaseReference1.child(key).child("productid").setValue(productid);
                    databaseReference1.child(key).child("status").setValue(true);
                    favoritebtn.setImageResource(R.drawable.favorite1);

                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String key1 = databaseReference.push().getKey();
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot ds:snapshot.getChildren()){
                                String productid1=ds.child("productid").getValue(String.class);
                                if(productid1!=null){

                                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                    myEdit.putString(FirebaseAuth.getInstance().getCurrentUser().getUid()+productid1,FirebaseAuth.getInstance().getCurrentUser().getUid()+productid1);
                                    myEdit.commit();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                String s1 = sh.getString(FirebaseAuth.getInstance().getCurrentUser().getUid()+productid, "");
                if(!s1.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()+productid)){
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key1).child("productid").setValue(productid);
                    Toast.makeText(Product.this, "added to cart", Toast.LENGTH_SHORT).show();

                }
                else if(productid.equals("")){
                    Toast.makeText(Product.this, "Product was not uploaded successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Product.this, "already in the cart", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(getApplicationContext(), MainUsers.class);
                startActivity(i);
            }
        });
       writereview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent i=new Intent(getApplicationContext(),WriteReviews.class);
               Bundle b=new Bundle();
               b.putString("productid",productid);
               i.putExtras(b);
               startActivity(i);
           }
       });
        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this,new String[]{pic1,pic2,pic3});
        viewPager.setAdapter(adapter);
        databaseReference3=firebaseDatabase.getReference("reviews").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(productid);
        databaseReference4=firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getKey()!=null){
                    username=snapshot.child("name").getValue(String.class);
                    profilepic=snapshot.child("profilepic").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Float rating =snapshot.child("rating").getValue(Float.class);
               String comments =snapshot.child("comments").getValue(String.class);
               if(rating!=null&& comments!=null){
                   list.add(new ReviewModel(username,comments,profilepic,rating));
                   ReviewAdapter reviewadapter=new ReviewAdapter(list,getApplicationContext());
                   recyclerView.setAdapter(reviewadapter);
                   LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                   recyclerView.setLayoutManager(linearLayoutManager);
               }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public  Boolean countFrequencies(String value,ArrayList<String> list)
    {
        Boolean check=false;
        // hashmap to store the frequency of element
        Map<String, Integer> hm = new HashMap<String, Integer>();

        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        // displaying the occurrence of elements in the arraylist
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            Log.d("ppid",val.getKey());
            if(value.equals(val.getKey().toString())){
                Log.d("entry","Element " + val.getKey() + " "
                        + "occurs"
                        + ": " + val.getValue() + " times");
                if(val.getValue()>=1){
                    Log.d("entry1",
                             ": " + val.getValue() + " times");
                    return  false;
                }
            }

        }
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Product.this,MainUsers.class);
        startActivity(i);
    }
}