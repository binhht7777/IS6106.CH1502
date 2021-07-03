package com.example.androideatit.Model;

import java.util.List;

public class Request {
    private String Phone;
    private String Name;
    private String Address;
    private String Total;
    private List<Order> foods;

    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> foods) {
        Phone = phone;
        Name = name;
        Address = address;
        Total = total;
        this.foods = foods;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
