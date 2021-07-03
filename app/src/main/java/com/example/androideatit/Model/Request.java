package com.example.androideatit.Model;

import java.util.List;

public class Request {
    private String Phone;
    private String Name;
    private String Address;
    private String Total;
    private List<Order> foods;
    private String status;
    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> foods) {
        Phone = phone;
        Name = name;
        Address = address;
        Total = total;
        this.foods = foods;
        status = "0"; // Default is 0: Placed, 1: Shipping, 2: Shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
