package com.example.project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.project.R;
import com.example.project.adapters.FavoriteAdapter;
import com.example.project.models.ProductsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ProductsModel> list=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    Favorite f;
    DatabaseReference databaseReference,databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView= findViewById(R.id.recyclerView1);
        firebaseDatabase=FirebaseDatabase.getInstance();
        f=Favorite.this;
        databaseReference=firebaseDatabase.getReference("favorite").child(FirebaseAuth.getInstance().getUid());
        databaseReference1=firebaseDatabase.getReference("products");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("fav",snapshot.getKey());
                    for(DataSnapshot snapshot1:snapshot.getChildren()){
                        String favoriteproductid=snapshot1.child("productid").getValue(String.class);
                        databaseReference1.child(favoriteproductid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                ProductsModel p=snapshot2.getValue(ProductsModel.class);
                                if(p!=null){
                                    try{
                                        p.setProductid(favoriteproductid);
                                        list.add(p);
                                        FavoriteAdapter favorite=new FavoriteAdapter(list,getApplicationContext(),f);
                                        recyclerView.setAdapter(favorite);
                                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
                                        recyclerView.setLayoutManager(gridLayoutManager);
                                    }
                                    catch(Exception e){

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void OnItemClick(int p) {
        Bundle b=new Bundle();
        b.putString("productname",list.get(p).getProductname());
        b.putString("pic1",list.get(p).getPic1());
        b.putString("pic2",list.get(p).getPic2());
        b.putString("pic3",list.get(p).getPic3());
        b.putString("price",list.get(p).getPrice());
        b.putString("size",list.get(p).getSize());
        b.putString("color",list.get(p).getColor());
        b.putString("description",list.get(p).getDescription());
        b.putString("productid",list.get(p).getProductid());
        Intent i=new Intent(getApplicationContext(),Product.class);
        i.putExtras(b);
        startActivity(i);
    }
}