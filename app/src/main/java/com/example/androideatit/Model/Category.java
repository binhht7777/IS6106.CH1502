package com.example.androideatit.Model;

public class Category {
    private String CategoryId;
    private String Name;
    private String Image;

    public Category() {
    }

    public Category(String categoryId, String name, String image) {
        CategoryId = categoryId;
        Name = name;
        Image = image;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
