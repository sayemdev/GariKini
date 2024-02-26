package com.app.garikini.Model;

public class CarBrand {
    private String name;
    private int imageUrl;

    public CarBrand(String name, int imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}
