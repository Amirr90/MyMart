package com.e.vibhacart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class test extends AppCompatActivity  {
    Slider banner_slider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_test );
        Button button=(Button)findViewById( R.id.button );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( test.this,BuyNowActivity.class ) );
            }
        } );
        banner_slider=(Slider)findViewById( R.id.banner_slider_product );
        loadBanner();

    }


    private void loadBanner() {
        Integer[] images={R.drawable.green_one,R.drawable.green_two,R.drawable.green_three,R.drawable.green_four};
        banner_slider.setAdapter( new DetailSliderAdapter(images) );
    }


    private class DetailSliderAdapter extends SliderAdapter {
        Integer[] images;

        public DetailSliderAdapter(Integer[] images) {
            this.images = images;
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        @Override
        public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
            try {
                imageSlideViewHolder.bindImageSlide( images[position] );
            }
            catch (Exception e)
            {

            }
        }
    }
}
