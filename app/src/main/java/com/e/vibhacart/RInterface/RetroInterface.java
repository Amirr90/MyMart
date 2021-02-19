package com.e.vibhacart.RInterface;

import com.e.vibhacart.Modals.CategoryModal;
import com.e.vibhacart.Modals.SubCategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroInterface {

    @GET("app/category")
    Call<SubCategoryModel> getCategoryData();
}
