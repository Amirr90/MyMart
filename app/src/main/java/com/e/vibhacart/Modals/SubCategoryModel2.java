package com.e.vibhacart.Modals;

import java.util.List;

public class SubCategoryModel2 {


    private String category_name;
    private String cat_id;
    private String image;
    private String category_status;


    List<ModelForSubCategory> subcat;

    public List<ModelForSubCategory> getSubcat() {
        return subcat;
    }

    public SubCategoryModel2(String category_name, String cat_id, String image, String category_status) {
        this.category_name = category_name;
        this.cat_id = cat_id;
        this.image = image;
        this.category_status = category_status;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getImage() {
        return image;
    }

    public String getCategory_status() {
        return category_status;
    }

    public String getCategory_name() {
        return category_name;
    }
}
