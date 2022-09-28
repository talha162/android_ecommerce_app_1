package com.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.models.ProductsModel;
import com.example.project.ui.OrdersHistory;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.viewHolder> {

    ArrayList<ProductsModel> list;
    Context context;
    OrdersHistory p;

    public OrdersAdapter(ArrayList<ProductsModel> list, Context context, OrdersHistory p) {
        this.p=p;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_orders,parent,false);
        return new viewHolder(view,p);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel model=list.get(position);
//        holder.pic.setImageResource(model.getPic1());
        holder.price.setText(model.getPrice());
        holder.product_name.setText(model.getProductname());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView pic;
        TextView price,product_name;
        public viewHolder(@NonNull View itemView, OrdersHistory p){
            super(itemView);
            pic=itemView.findViewById(R.id.pic_orders);
            price=itemView.findViewById(R.id.price_orders);
            product_name=itemView.findViewById(R.id.p_name_orders);

        }
    }
}
