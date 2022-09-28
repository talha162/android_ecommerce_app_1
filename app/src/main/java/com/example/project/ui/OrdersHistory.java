package com.example.project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.project.R;
import com.example.project.adapters.OrdersAdapter;
import com.example.project.models.ProductsModel;

import java.util.ArrayList;

public class OrdersHistory extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ProductsModel> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        recyclerView= findViewById(R.id.recycler_orders);
//        list.add(new ProductsModel(R.drawable.shirt1,"$20","Men Shirt"));
//        list.add(new ProductsModel(R.drawable.shirt2,"$30","Men half T-Shirt"));
//        list.add(new ProductsModel(R.drawable.shirt3,"$40","Men white Shirt"));
        OrdersAdapter order=new OrdersAdapter(list,getApplicationContext(),this);
        recyclerView.setAdapter(order);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

    }
}