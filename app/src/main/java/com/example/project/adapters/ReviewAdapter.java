package com.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.models.ReviewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    ArrayList<ReviewModel> List;
    Context context;

    public ReviewAdapter(ArrayList<ReviewModel> list, Context context) {
        List = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_review,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ReviewModel model=List.get(position);
    holder.username.setText(model.getUsername1());
    holder.comments.setText(model.getComment1());
        Picasso.get().load(model.getProfilepic()).into(holder.imageView);
    holder.ratingBar.setRating(model.getRatingBar1());
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,comments;
        ImageView imageView;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            comments=itemView.findViewById(R.id.comment);
            imageView=itemView.findViewById(R.id.imageView);
            ratingBar=itemView.findViewById(R.id.ratingBar);
        }
    }
}
