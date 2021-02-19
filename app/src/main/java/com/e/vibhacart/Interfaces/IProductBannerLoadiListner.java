package com.e.vibhacart.Interfaces;

import com.e.vibhacart.Modals.Banners;
import com.e.vibhacart.Modals.ProductBanner;

import java.util.List;

public interface IProductBannerLoadiListner {
    void onBannerLoadSuccess(List<ProductBanner> banners);
    void onBannerLoadError(String message);
}