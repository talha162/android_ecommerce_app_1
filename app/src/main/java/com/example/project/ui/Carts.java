package com.example.project.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.adapters.CartsAdapter;
import com.example.project.models.CartsModel;
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
 * Use the {@link Carts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Carts extends Fragment implements ValueEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView cartsrecyclerview;
    ArrayList<CartsModel> list=new ArrayList<>();
    Button minus,plus;
    TextView price,qty,priceTotal;
    int qty1;
    int price1;
    Carts c;
    int totalPrice=0;
    Button continue1;
     CartsAdapter cartsAdapter;
     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference,databaseReference1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Carts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Carts.
     */
    // TODO: Rename and change types and number of parameters
    public static Carts newInstance(String param1, String param2) {
        Carts fragment = new Carts();
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
        View v= inflater.inflate(R.layout.fragment_carts, container, false);
        
        cartsrecyclerview=v.findViewById(R.id.carts_recyclerview);
        continue1=v.findViewById(R.id.continue_btn);
        qty=v.findViewById(R.id.qty);
        price=v.findViewById(R.id.pricecart);
firebaseDatabase=FirebaseDatabase.getInstance();
databaseReference=firebaseDatabase.getReference("carts").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
databaseReference1=firebaseDatabase.getReference("products");
databaseReference.addListenerForSingleValueEvent(this);
c=Carts.this;
        continue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cartsAdapter.totalPrice!=0&&cartsAdapter.totalPrice>0){
                    Intent i=new Intent(getContext(), Address.class);;
                    Bundle b=new Bundle();
                    b.putInt("totalprice",cartsAdapter.totalPrice);
                    i.putExtras(b);
                    startActivity(i);
                }

            }
        });
        return v;
    }

    public void OnItemLongClick(int p){

            String productid = list.get(p).getProductid();
            Query query = databaseReference.orderByChild("productid").equalTo(productid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString(FirebaseAuth.getInstance().getCurrentUser().getUid()+productid,"" );
                            myEdit.commit();
                                s.getRef().removeValue();
                                try{
                                    list.remove(p);
                                    cartsAdapter.notifyItemRemoved(p);
                                }
                                catch(Exception e){
                                }
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("TAG", databaseError.getMessage(), databaseError.toException());
                }
            });
        }


    public void OnItemClick(int p){
//        Bundle b=new Bundle();
//        b.putString("title",list.get(p).getProduct_name());
//        Intent i=new Intent(getContext(), Product.class);
//        i.putExtras(b);
//        startActivity(i);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
            String cartproductid=dataSnapshot.child("productid").getValue(String.class);

            databaseReference1.child(cartproductid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ProductsModel p=snapshot.getValue(ProductsModel.class);
                    if(p!=null){
                        try{
                        list.add(new CartsModel(cartproductid,p.getPic1(),"1",p.getPrice(),p.getProductname()));
                        cartsAdapter=new CartsAdapter(list,getContext(),c);
                        cartsrecyclerview.setAdapter(cartsAdapter);
                        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                        cartsrecyclerview.setLayoutManager(linearLayoutManager);
                        }
                        catch(Exception e){
                        }
                        }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }


}