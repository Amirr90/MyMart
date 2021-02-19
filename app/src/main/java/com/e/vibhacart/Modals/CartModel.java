package com.e.vibhacart.Modals;

public class CartModel {

    private String quantity;
    private String image;
    private String productName;
    private String productPrice;
    private String oldproductPrice;


    public String getQuantity() {
        return quantity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public void setOldproductPrice(String oldproductPrice) {
        this.oldproductPrice = oldproductPrice;
    }

    public CartModel(String image, String productName) {
        this.image = image;
        this.productName = productName;
    }

    public CartModel() {
    }

    public String getImage() {
        return image;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getOldproductPrice() {
        return oldproductPrice;
    }
}
