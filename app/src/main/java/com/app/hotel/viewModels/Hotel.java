package com.app.hotel.viewModels;

import com.google.firebase.database.Exclude;

public class Hotel {
    private String name, imageUrl, location;
    private String price;
    private String mKey;
    public Hotel() {
        //Empty Constructor
    }

    public Hotel(String name, String imageUrl, String price,  String location) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        this.mKey = key;
    }

}
