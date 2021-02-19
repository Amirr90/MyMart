package com.e.vibhacart.Modals;


public class CategoryModal {
    private String categoryIconLink;
    private String categoryName;

    private boolean response;
    private String msg;
    SubCategoryModel data;

    public boolean isResponse() {
        return response;
    }

    public String getMsg() {
        return msg;
    }

    public SubCategoryModel getData() {
        return data;
    }

    public String getCategoryIconLink() {
        return categoryIconLink;
    }

    public void setCategoryIconLink(String categoryIconLink) {
        this.categoryIconLink = categoryIconLink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
