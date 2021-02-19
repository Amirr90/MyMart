package com.e.vibhacart.Modals;

public class MobileModal {
    private String name;
    int image;


    public MobileModal(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public MobileModal() {
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
