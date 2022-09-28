package com.example.project.ui;

import android.app.ProgressDialog;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.adapters.ProductsAdapter;
import com.example.project.interfaces.CartsInterface;
import com.example.project.models.ProductsModel;
import com.example.project.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Products#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Products extends Fragment  implements ValueEventListener{
             FirebaseDatabase firebaseDatabase;
        FirebaseStorage storage;
        FirebaseAuth auth;
        FirebaseUser user;
        DatabaseReference databaseReference,databaseReference1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
ProgressDialog progressDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    SearchView search;
    ArrayList<ProductsModel> list=new ArrayList<>();
    String productid;
//    SearchView searchView;
    public Products() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Products.
     */
    // TODO: Rename and change types and number of parameters
    public static Products newInstance(String param1, String param2) {
        Products fragment = new Products();
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_products, container, false);
        recyclerView= v.findViewById(R.id.recyclerView1);
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        user=auth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("products");
        progressDialog=new ProgressDialog(getContext());
        databaseReference.addListenerForSingleValueEvent(this);
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
        Intent i=new Intent(getContext(),Product.class);
        i.putExtras(b);
        startActivity(i);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {;
        for (DataSnapshot s:snapshot.getChildren()){
            ProductsModel p=s.getValue(ProductsModel.class);
            if(p!=null|| p.getProductid().equals(null)){
                productid=s.getKey();
                p.setProductid(productid);
                list.add(p);
                ProductsAdapter product=new ProductsAdapter(list,getContext(),this);
                recyclerView.setAdapter(product);
                GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
                recyclerView.setLayoutManager(gridLayoutManager);
            }

        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}