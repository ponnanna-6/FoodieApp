package com.foodie.foodiev1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodie.foodiev1.MenuActivity;
import com.foodie.foodiev1.R;
import com.foodie.foodiev1.home.MainActivity;
import com.foodie.foodiev1.models.HomeVerModel;

import java.util.ArrayList;

public class HomeVerAdapter extends RecyclerView.Adapter<HomeVerAdapter.ViewHolder> {
    private static final String Tag="RecyclerView";
    Context context;
    ArrayList<HomeVerModel> list;

    private ItemClickListener mClickListener;
    public HomeVerAdapter(Context context, ArrayList<HomeVerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeVerAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(list.get(position).getName());
        holder.ratingsTextView.setText(list.get(position).getRatings());
        holder.priceRangeTextView.setText(list.get(position).getPriceRange());

        Glide.with(context)
                        .load(list.get(position).getImage())
                                .into(holder.homeVerImageView);
        //holder.homeVerImageView.setImageResource(list.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onItemClick(HomeVerModel restaurant);
    }

    public void setClickListener(ItemClickListener listener) {
        this.mClickListener = listener;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView homeVerImageView;
        TextView nameTextView, ratingsTextView, priceRangeTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeVerImageView=itemView.findViewById(R.id.vertical_image);
            nameTextView=itemView.findViewById(R.id.nameTextViewVertical);
            ratingsTextView=itemView.findViewById(R.id.ratingsTextView);
            priceRangeTextView=itemView.findViewById(R.id.priceRangeTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            HomeVerModel restaurant = list.get(position);
                            mClickListener.onItemClick(restaurant);
                        }
                    }
                }
            });
        }
    }
}
