package com.e.vibhacart.Modals;

public class Order {
    private String cancelled_date,product_name,image,size,order_id,uid;

    public Order() {
    }

    public Order(String cancelled_date, String product_name, String image) {
        this.cancelled_date = cancelled_date;
        this.product_name = product_name;
        this.image = image;
    }

    public String getCancelled_date() {
        return cancelled_date;
    }

    public void setCancelled_date(String cancelled_date) {
        this.cancelled_date = cancelled_date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
