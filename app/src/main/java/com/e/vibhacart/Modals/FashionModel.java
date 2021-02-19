package com.e.vibhacart.Modals;

public class FashionModel {
    private String name;
    int image;

    public FashionModel(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
