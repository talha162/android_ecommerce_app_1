package com.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.models.CartsModel;
import com.example.project.ui.Carts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.viewHolder> {
    ArrayList<CartsModel> list;
    Carts carts;
    Context context;
    Button plus,minus;
    public int totalPrice=0;
//    int qty3=1;
String price;

    public CartsAdapter(ArrayList<CartsModel> list, Context context,Carts carts) {
        this.carts=carts;
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.sample_carts,parent,false);

        return new viewHolder(v,carts);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CartsModel model=list.get(position);
        Picasso.get()
                .load(model.getPic())
                .into(holder.pic);
        holder.product_name.setText(model.getProduct_name());
        holder.qty.setText(model.getQty());
        holder.price.setText(model.getPrice());

        final int[] qty = {1};
        StringBuilder s=new StringBuilder(model.getPrice());
        s.deleteCharAt(0);
         price=s.toString();
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            qty[0]++;
                holder.qty.setText(Integer.toString(qty[0]));
                holder.price.setText("$"+Integer.toString(Integer.parseInt(price)*qty[0]));

                totalPrice=totalPrice+(Integer.parseInt(price));
//                qty3=qty[0];
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                qty[0]=Integer.parseInt(model.getQty());
                if(qty[0]>1){
                    qty[0]--;
                    holder.qty.setText(Integer.toString(qty[0]));
                    holder.price.setText("$"+Integer.toString(Integer.parseInt(price)*qty[0]));
                    totalPrice=totalPrice-(Integer.parseInt(price));
//                    qty3=qty[0];
                }

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                carts.OnItemLongClick(holder.getAdapterPosition());
                totalPrice=totalPrice-(Integer.parseInt(price)*qty[0]);
            }
        });
        totalPrice=totalPrice+Integer.parseInt(price);

    }


    public class viewHolder extends RecyclerView.ViewHolder{
            ImageView pic;
            TextView price,product_name, qty;
            Button minus,plus;
        ImageView delete;
        public viewHolder(@NonNull View itemView,Carts carts) {
            super(itemView);
            delete=itemView.findViewById(R.id.delete);
            pic=itemView.findViewById(R.id.cartimage);
            qty=itemView.findViewById(R.id.qty);
            price=itemView.findViewById(R.id.pricecart);
            product_name=itemView.findViewById(R.id.p_namecart);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    carts.OnItemClick(getAdapterPosition());
                }
            });

        }
    }

}
