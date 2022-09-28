package com.example.project.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.models.ProductsModel;
import com.example.project.ui.Products;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.viewHolder> {
    ProgressDialog progressDialog;
    ArrayList<ProductsModel> list;
    Context context;
    Products p;

    public ProductsAdapter(ArrayList<ProductsModel> list, Context context, Products p) {
        this.p=p;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_products,parent,false);
        progressDialog=new ProgressDialog(p.getContext());
        progressDialog.setMessage("loading...");
        return new viewHolder(view,p);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel model=list.get(position);
        Picasso.get()
                .load(model.getPic1())
                .into(holder.pic);
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
        public viewHolder(@NonNull View itemView,Products p){
            super(itemView);
            pic=itemView.findViewById(R.id.productimage);
            price=itemView.findViewById(R.id.pricecart);
            product_name=itemView.findViewById(R.id.p_namecart);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    p.OnItemClick(getAdapterPosition());
                }
            });
        }
    }
}
