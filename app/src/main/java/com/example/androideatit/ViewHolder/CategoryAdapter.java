package com.example.androideatit.ViewHolder;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.androideatit.Model.Category;
import com.example.androideatit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> implements Filterable {
    List<Category> categoryList;
    List<Category> categoryListOld;

    private SelectedCategory selectedCategory;


    public CategoryAdapter(List<Category> categoryList, SelectedCategory selectedCategory) {
        this.categoryList = categoryList;
        this.categoryListOld = categoryList;
        this.selectedCategory = selectedCategory;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Context context = null;
        Category category = categoryList.get(position);
        if(category==null){
            return;
        }

        Picasso.with(context).load(category.getImage()).into(holder.menuImage);
        //holder.menuImage.setImageURI(Uri.parse(category.getImage()));
        holder.menuName.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        if(categoryList!=null){
            return categoryList.size();
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
                    categoryList=categoryListOld;
                }else{
                    List<Category> list = new ArrayList<>();
                    for(Category category:categoryListOld){
                        if(category.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(category);
                        }
                    }
                    categoryList=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values=categoryList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                categoryList = (List<Category>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface SelectedCategory{
        void selectedCategory(Category categoryModel);

    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView menuImage;
        private TextView menuName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            menuImage=(ImageView)itemView.findViewById(R.id.menu_image);
            menuName=(TextView)itemView.findViewById(R.id.menu_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedCategory.selectedCategory(categoryList.get(getAdapterPosition()));
                }
            });
        }
    }
}
