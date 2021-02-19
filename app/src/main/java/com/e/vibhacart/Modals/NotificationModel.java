package com.e.vibhacart.Modals;

public class NotificationModel {
    String title1,title2;
    int image;

    public NotificationModel(String title1, String title2, int image) {
        this.title1 = title1;
        this.title2 = title2;
        this.image = image;
    }

    public NotificationModel() {
    }

    public String getTitle1() {
        return title1;
    }

    public String getTitle2() {
        return title2;
    }

    public int getImage() {
        return image;
    }
}
