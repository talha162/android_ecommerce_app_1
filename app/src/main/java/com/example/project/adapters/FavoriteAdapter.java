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
import com.example.project.ui.Favorite;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewHolder> {

    ArrayList<ProductsModel> list;
    Context context;
    Favorite p;

    public FavoriteAdapter(ArrayList<ProductsModel> list, Context context, Favorite p) {
        this.p=p;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_products,parent,false);
        return new viewHolder(view,p);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ProductsModel model=list.get(position);
        Picasso.get().load(model.getPic1()).into(holder.pic);
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
        public viewHolder(@NonNull View itemView,Favorite p){
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
