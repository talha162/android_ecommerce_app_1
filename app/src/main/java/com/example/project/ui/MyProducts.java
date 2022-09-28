package com.example.project.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.adapters.MyProductsAdapter;
import com.example.project.adapters.ProductsAdapter;
import com.example.project.models.ProductsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProducts extends Fragment implements ValueEventListener{
    RecyclerView recyclerView;
    ProductsAdapter product;
    MyProducts mp;
    ArrayList<ProductsModel> list=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProducts newInstance(String param1, String param2) {
        MyProducts fragment = new MyProducts();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_my_products, container, false);
        recyclerView= v.findViewById(R.id.recyclerView1);
        firebaseDatabase=FirebaseDatabase.getInstance();
        mp=MyProducts.this;
        databaseReference=firebaseDatabase.getReference().child("userproductids");
        databaseReference1=firebaseDatabase.getReference().child("products");
        Query q=databaseReference.orderByChild("userid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
q.addListenerForSingleValueEvent(this);

        return v;
    }
    public void OnItemClick(int p){
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
        Log.d("talha5",list.get(p).getProductid());
        Intent i=new Intent(getContext(),UpdateDeleteProduct.class);
        i.putExtras(b);
        startActivity(i);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
            String k=dataSnapshot.getKey();
            String productid1=dataSnapshot.child("productid").getValue(String.class);
            String userid=dataSnapshot.child("userid").getValue(String.class);
            if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(userid)){
                databaseReference1.child(productid1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            ProductsModel p=snapshot.getValue(ProductsModel.class);
                        if(p!=null){
                            list.add(p);
                            p.setProductid(productid1);
                            MyProductsAdapter product=new MyProductsAdapter(list,getContext(),mp);
                            recyclerView.setAdapter(product);
                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
                            recyclerView.setLayoutManager(gridLayoutManager);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }



}