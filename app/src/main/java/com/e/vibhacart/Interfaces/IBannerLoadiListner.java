package com.e.vibhacart.Interfaces;


import com.e.vibhacart.Modals.Banners;

import java.util.List;

public interface IBannerLoadiListner {
    void onBannerLoadSuccess(List<Banners> banners);
    void onBannerLoadError(String message);
}

