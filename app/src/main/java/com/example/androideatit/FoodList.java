package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androideatit.Interface.ItemClickListener;
import com.example.androideatit.Model.Category;
import com.example.androideatit.Model.Food;
import com.example.androideatit.ViewHolder.CategoryAdapter;
import com.example.androideatit.ViewHolder.FoodAdapter;
import com.example.androideatit.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity implements FoodAdapter.SelectedFood {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    // Search Functionality
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    // Khai bao 1 recyclerview moi
    RecyclerView rcvFoodList;
    FoodAdapter foodAdapter;
    SearchView searchView;
    // khai bao 1 recyclerview moi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("foods");

        recyclerView = (RecyclerView) findViewById(R.id.recycle_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (GlobalVariable.Key == null) {
            GlobalVariable.Key = getIntent().getStringExtra("CategoryId");
        }
        // Get Intent
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        // Khai bao 1 recyclerview moi
        rcvFoodList= findViewById(R.id.recycle_food);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvFoodList.setLayoutManager(linearLayoutManager);

        foodAdapter = new FoodAdapter(getListFood(), this);
        rcvFoodList.setAdapter(foodAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvFoodList.addItemDecoration(itemDecoration);

        if (categoryId == null) {
            LoadListFood(GlobalVariable.Key);
        } else {
            LoadListFood(categoryId);
        }
        // Khai bao 1 recyclerview moi



//        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
//        materialSearchBar.setHint("Enter your food");
//        loadSuggest();
//        materialSearchBar.setLastSuggestions(suggestList);
//        materialSearchBar.setCardViewElevation(10);
//        materialSearchBar.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                List<String> suggest = new ArrayList<String>();
//                for (String search : suggestList) {
//                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
//                        suggest.add(search);
//                    }
//                }
//                materialSearchBar.setLastSuggestions(suggest);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//            @Override
//            public void onSearchStateChanged(boolean enabled) {
//                // When search bar is close
//                // Restore original adapter
//                if (!enabled) {
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//                // When search finish
//                // Show result of search adapter
//                startSearch(text);
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//
//            }
//        });

    }

    private List<Food> getListFood() {
        List<Food> list = new ArrayList<Food>();
        foodList.orderByChild("menuid").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Food food = postSnapshot.getValue(Food.class);
                    list.add(new Food(postSnapshot.getKey(), food.getName(), food.getImage(), null, null, null, categoryId));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return list;
    }

//    private void startSearch(CharSequence text) {
//        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
//                Food.class,
//                R.layout.food_item,
//                FoodViewHolder.class,
//                foodList.orderByChild("name").equalTo(text.toString())
//        ) {
//            @Override
//            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int position) {
//                foodViewHolder.food_name.setText(food.getName());
//                Picasso.with(getBaseContext()).load(food.getImage())
//                        .into(foodViewHolder.food_image);
//                final Food local = food;
//                foodViewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongCLick) {
//                        // BinhPT06 - Start new Activity
//                        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
//                        foodDetail.putExtra("FoodId", searchAdapter.getRef(position).getKey());
//                        startActivity(foodDetail);
//                    }
//                });
//            }
//        };
//        recyclerView.setAdapter(searchAdapter);
//    }

//    private void loadSuggest() {
//        foodList.orderByChild("menuid").equalTo(categoryId)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                            Food item = postSnapshot.getValue(Food.class);
//                            suggestList.add(item.getName());
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//    }

    private void LoadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuid").equalTo(categoryId)) // BinhPT06 - Like select * From foods where menuid= id
        {
            @Override
            protected void populateViewHolder(FoodViewHolder foodViewHolder, Food food, int position) {
                foodViewHolder.food_name.setText(food.getName());
                Picasso.with(getBaseContext()).load(food.getImage())
                        .into(foodViewHolder.food_image);
                final Food local = food;
                foodViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongCLick) {
                        // BinhPT06 - Start new Activity
                        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.nav_food_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foodAdapter.getFilter().filter(query);
                recyclerView.setAdapter(foodAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                foodAdapter.getFilter().filter(newText);
                recyclerView.setAdapter(foodAdapter);
                return true;
            }
        });

        return true;
    }

    @Override
    public void selectedFood(Food foodModel) {
        // BinhPT06 - Get  categoryId and send to new activity
        Intent foodDetail = new Intent(FoodList.this, FoodDetail.class);
        foodDetail.putExtra("FoodId", foodModel.getFoodId());
        startActivity(foodDetail);
    }
}