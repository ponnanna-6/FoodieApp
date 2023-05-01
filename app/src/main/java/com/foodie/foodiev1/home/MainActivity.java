package com.foodie.foodiev1.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.foodie.foodiev1.FoodieDecision;
import com.foodie.foodiev1.MenuActivity;
import com.foodie.foodiev1.ProfileActivity;
import com.foodie.foodiev1.R;
import com.foodie.foodiev1.UpdateVerticalRec;
import com.foodie.foodiev1.adapters.HomeHorAdapter;
import com.foodie.foodiev1.adapters.HomeVerAdapter;
import com.foodie.foodiev1.models.HomeHorModel;
import com.foodie.foodiev1.models.HomeVerModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements UpdateVerticalRec, HomeVerAdapter.ItemClickListener, HomeHorAdapter.ItemClickListener{

    ImageButton profileImageButton, aboutUsImageButton;
    TextView profileTextView, aboutUsTextView;
    FloatingActionButton fab;
    DatabaseReference databaseReference;
    RecyclerView homeHorizontalRec, homeVerticalRec;
    ArrayList<HomeHorModel> homeHorModelList;
    ArrayList<HomeVerModel> homeVerModelList;
    HomeHorAdapter homeHorAdapter;
    HomeVerAdapter homeVerAdapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileImageButton=findViewById(R.id.profileSymbolImageView);
        profileTextView=findViewById(R.id.profileTextView);
        fab=findViewById(R.id.fab);
        aboutUsImageButton=findViewById(R.id.aboutUsSymbolImageView);
        aboutUsTextView=findViewById(R.id.aboutUsTextView);
        // Profile click listener ///
        profileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        // Foodie Decision listener ///
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FoodieDecision.class);
                startActivity(i);
            }
        });

        //Hor and vertical views//
        homeHorizontalRec =findViewById(R.id.homeHorRecyclerView);
        homeVerticalRec=findViewById(R.id.homeVerRecyclerView);

        ////////Horizontal recycler view/////////
        homeHorModelList = new ArrayList<>();
        homeHorModelList.add(new HomeHorModel(R.drawable.non_veg_icon, "Non-Veg"));
        homeHorModelList.add(new HomeHorModel(R.drawable.veg_icon, "Veg"));
        homeHorModelList.add(new HomeHorModel(R.drawable.street_food_icon, "Local"));

        homeHorAdapter = new HomeHorAdapter(MainActivity.this, homeHorModelList, this);
        homeHorizontalRec.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL,false));
        homeHorizontalRec.setHasFixedSize(true);
        homeHorizontalRec.setNestedScrollingEnabled(false);
        homeHorizontalRec.setAdapter(homeHorAdapter);

        ////////Vertical recycler view/////////
        databaseReference= FirebaseDatabase.getInstance().getReference();
        homeVerModelList = new ArrayList<>();
        ClearAll();
        GetDataFromFirebase("Non-Veg");
        homeVerticalRec.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false));
        homeVerticalRec.setHasFixedSize(true);
        homeVerticalRec.setNestedScrollingEnabled(true);


    }

    private void GetDataFromFirebase(String category) {
        Query query=databaseReference.child("restaurants").child(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    HomeVerModel homeVerModel=new HomeVerModel();
                    homeVerModel.setImage(snapshot.child("image").getValue().toString());
                    homeVerModel.setName(snapshot.child("name").getValue().toString());
                    homeVerModel.setPriceRange(snapshot.child("priceRange").getValue().toString());
                    homeVerModel.setRatings(snapshot.child("ratings").getValue().toString());

                    ArrayList<String> menuImagesList = new ArrayList<>();
                    for(DataSnapshot menuImageSnapshot : snapshot.child("menuImages").getChildren()){
                        String menuImage = menuImageSnapshot.getValue(String.class);
                        menuImagesList.add(menuImage);
                    }

                    homeVerModel.setMenuImages(menuImagesList);

                    homeVerModelList.add(homeVerModel);

                }
                homeVerAdapter = new HomeVerAdapter(MainActivity.this, homeVerModelList);
                homeVerticalRec.setAdapter(homeVerAdapter);
                homeVerAdapter.notifyDataSetChanged();
                homeVerAdapter.setClickListener(new HomeVerAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(HomeVerModel restaurant) {
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        intent.putStringArrayListExtra("menuImagesList", restaurant.getMenuImages());
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ClearAll(){
        if(homeVerModelList!=null){
            homeVerModelList.clear();
        }
        if(homeVerAdapter!=null){
            homeVerAdapter.notifyDataSetChanged();
        }
        else{
            homeVerAdapter = new HomeVerAdapter(MainActivity.this, homeVerModelList);
            homeVerticalRec.setAdapter(homeVerAdapter);
        }
    }

    @Override
    public void callBack(int position, ArrayList<HomeVerModel> homeVerModels) {
        homeVerAdapter=new HomeVerAdapter(this, homeVerModels);
        homeVerAdapter.notifyDataSetChanged();
        homeVerticalRec.setAdapter(homeVerAdapter);
    }

    @Override
    public void onItemClick(HomeVerModel restaurant) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }

    @Override
    public void onItemClick(String category) {
        GetDataFromFirebase(category);
    }
}