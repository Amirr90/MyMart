package com.e.vibhacart.Adapters;

import com.e.vibhacart.Modals.Banners;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class HomeSliderAdapter extends SliderAdapter {

    List<Banners> bannersList;

    public HomeSliderAdapter(List<Banners> bannersList) {
        this.bannersList = bannersList;
    }

    @Override
    public int getItemCount() {
        return bannersList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        try {
            imageSlideViewHolder.bindImageSlide( bannersList.get( position ).getImage() );
        }
        catch (Exception e)
        {

        }
    }
}
