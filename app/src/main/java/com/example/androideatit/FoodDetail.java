package com.example.androideatit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androideatit.Database.DatabaseHelper;
import com.example.androideatit.Model.Food;
import com.example.androideatit.Model.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description, txtSum;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ImageButton btnTang, btnGiam;

    String foodId;
    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentFood;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // firebase
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("foods");

        // init view
        btnTang=(ImageButton)findViewById(R.id.btnTang);
        btnGiam=(ImageButton)findViewById(R.id.btnGiam);
        txtSum=(TextView)findViewById(R.id.txtSum);

        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);

        databaseHelper = new DatabaseHelper(this, "EatItDB.db", null, 1);
        databaseHelper.CreateTable();

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Database(getBaseContext()).addToCart(new Order(
//                        foodId,
//                        currentFood.getName(),
//                        txtSum.getText().toString(),
//                        currentFood.getPrice(),
//                        currentFood.getDiscount()
//                ));
                databaseHelper.AddToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        txtSum.getText().toString(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Toast.makeText(FoodDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        food_description=(TextView)findViewById(R.id.food_description);
        food_name=(TextView)findViewById(R.id.food_name);
        food_price=(TextView)findViewById(R.id.food_price);
        food_image=(ImageView)findViewById(R.id.img_food);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        // BingPT06 -  get food id from intent
        if(getIntent()!=null){
            foodId=getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty() && foodId!=null){
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                //set image
                Picasso.with(getBaseContext()).load(currentFood.getImage())
                    .into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}