package com.e.vibhacart.Modals;

public class Product {
    private String quantity;
    private int imageResourceId;
    private String productName;
    private String productPrice;
    private boolean isLoading = false;
    private boolean isNew = false;
    private String oldproductPrice;
    private String category;
    private String sub_category;
    private String rating;
    private String discount;
    private String image;
    private int stock;



    public Product(int imageResourceId, String productName, String productPrice, boolean isNew, String oldproductPrice,String quantity) {
        this.imageResourceId = imageResourceId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.isNew = isNew;
        this.oldproductPrice = oldproductPrice;
        this.quantity=quantity;
    }

    public String getCategory() {
        return category;
    }


    public int getStock() {
        return stock;
    }

    public Product(int imageResourceId, String productName, String productPrice, boolean isNew, String oldproductPrice, String quantity, String category, String sub_category, String rating) {
        this.imageResourceId = imageResourceId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.isNew = isNew;
        this.oldproductPrice = oldproductPrice;
        this.quantity=quantity;
        this.category=category;
        this.sub_category=sub_category;
        this.rating=rating;
    }

    public String getSub_category() {
        return sub_category;
    }

    public String getRating() {
        return rating;
    }


    public String getQuantity() {
        return quantity;
    }

    public String getOldproductPrice() {
        return oldproductPrice;
    }

    public Product() {
    }


    public String getDiscount() {
        return discount;
    }

    public String getImage() {
        return image;
    }

    public boolean isNew() {
        return isNew;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }


}
