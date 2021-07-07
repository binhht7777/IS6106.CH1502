package com.example.androideatit.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Model.Food;
import com.example.androideatit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> implements Filterable {
    List<Food> foodList;
    List<Food> foodListOld;
    private FoodAdapter.SelectedFood selectedFood;

    public FoodAdapter(List<Food> foodList, SelectedFood selectedFood) {
        this.foodList = foodList;
        this.foodListOld = foodList;
        this.selectedFood = selectedFood;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        Context context = null;
        Food food = foodList.get(position);
        if(food==null){
            return;
        }

        Picasso.with(context).load(food.getImage()).into(holder.foodImage);
        holder.foodName.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        if(foodList!=null){
            return foodList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    foodList=foodListOld;
                }else{
                    List<Food> list = new ArrayList<>();
                    for(Food food:foodListOld){
                        if(food.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(food);
                        }
                    }
                    foodList=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=foodList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                foodList = (List<Food>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface SelectedFood{
        void selectedFood(Food foodModel);

    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView foodImage;
        private TextView foodName;

        public FoodViewHolder(View itemView) {
            super(itemView);
            foodImage=(ImageView)itemView.findViewById(R.id.food_image);
            foodName=(TextView)itemView.findViewById(R.id.food_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedFood.selectedFood(foodList.get(getAdapterPosition()));
                }
            });
        }
    }

}
