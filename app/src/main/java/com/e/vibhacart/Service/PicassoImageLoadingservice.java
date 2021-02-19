package com.e.vibhacart.Service;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;

public class PicassoImageLoadingservice implements ImageLoadingService {
    @Override
    public void loadImage(String url, ImageView imageView) {
        try {
            Picasso.get().load( url ).into(imageView);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        try {
            Picasso.get().load( resource ).into(imageView);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
       try {
           Picasso.get().load( url ).placeholder( placeHolder ).error( errorDrawable ).into(imageView);
       }
       catch (Exception e)
       {

       }
    }
}
