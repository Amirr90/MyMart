package com.e.vibhacart.Adapters;

import com.e.vibhacart.Modals.Banners;
import com.e.vibhacart.Modals.ProductBanner;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ProductDetailSliderAdapter extends SliderAdapter {

    List<ProductBanner> bannersList;

    public ProductDetailSliderAdapter(List<ProductBanner> bannersList) {
        this.bannersList = bannersList;
    }

    @Override
    public int getItemCount() {
        return bannersList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        imageSlideViewHolder.bindImageSlide( bannersList.get( position ).getImage() );
    }
}
