package com.foodie.foodiev1.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.foodie.foodiev1.R;
import com.foodie.foodiev1.UpdateVerticalRec;
import com.foodie.foodiev1.models.HomeHorModel;
import com.foodie.foodiev1.models.HomeVerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> implements Serializable {


    Context context;
    ArrayList<HomeHorModel> list;
    private DatabaseReference mDatabaseRef;
    boolean select=true;
    int row_index=-1;
    ItemClickListener listener;


    public HomeHorAdapter(Context context, ArrayList<HomeHorModel> list) {
        this.context=context;
        this.list=list;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("restaurants");
    }

    public HomeHorAdapter(Context context, ArrayList<HomeHorModel> list, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(String category);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_horizontal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.imageView.setImageResource(list.get(position).getImage());
        holder.textView.setText(list.get(position).getTitle());
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                row_index = position;
//                notifyDataSetChanged();
//
//                mDatabaseRef.child(list.get(position).getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists()) {
//                            ArrayList<HomeVerModel> homeVerModels = new ArrayList<>();
//                            for (DataSnapshot ds : snapshot.getChildren()) {
//                                HomeVerModel model = ds.getValue(HomeVerModel.class);
//                                homeVerModels.add(model);
//                            }
//
//                            if (context instanceof UpdateVerticalRec) {
//                                ((UpdateVerticalRec) context).callBack(position, homeVerModels);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Log.d("HomeHorAdapter", "DatabaseError: " + error.getMessage());
//                    }
//                });
//            }
//        });
//
//        if (select && position == 0) {
//            holder.cardView.setBackgroundResource(R.drawable.change_bg);
//            select = false;
//        } else if (row_index == position) {
//            holder.cardView.setBackgroundResource(R.drawable.change_bg);
//        } else {
//            holder.cardView.setBackgroundResource(R.drawable.default_bg);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        CardView cardView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.hor_img);
            cardView=itemView.findViewById(R.id.cardView);
            textView=itemView.findViewById(R.id.hor_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(textView.getText().toString());
                }
            });
        }
    }
}
