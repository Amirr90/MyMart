package com.e.vibhacart.Modals;

public class ModelForSubCategory {

    private String subcat_id;
    private String subcat_desc;
    private String created_at;
    private String category_id;
    private String subcat_status;
    private String subcat_name;


    public ModelForSubCategory(String subcat_id, String subcat_desc, String created_at, String category_id, String subcat_status, String subcat_name) {
        this.subcat_id = subcat_id;
        this.subcat_desc = subcat_desc;
        this.created_at = created_at;
        this.category_id = category_id;
        this.subcat_status = subcat_status;
        this.subcat_name = subcat_name;
    }

    public String getSubcat_name() {
        return subcat_name;
    }

    public String getSubcat_id() {
        return subcat_id;
    }

    public String getSubcat_desc() {
        return subcat_desc;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getSubcat_status() {
        return subcat_status;
    }
}
