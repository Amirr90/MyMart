package com.e.vibhacart.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.vibhacart.Modals.SliderBanner;
import com.e.vibhacart.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

class MyAdapter extends PagerAdapter {
    List<SliderBanner> posts;
    Context context;

    public MyAdapter(List<SliderBanner> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals( o );
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position)
    {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final ViewGroup layout = (ViewGroup) inflater.inflate( R.layout.banner_layout, container, false);

        final ImageView imageView=(ImageView)layout.findViewById( R.id.banner_slider );
        if (posts.get( position ).getImage().equals( "" ))
        {
            imageView.setImageResource( R.drawable.account_circle_black_24dp );
        }
        else {
            Picasso.get( ).load( posts.get( position ).getImage() )
                    .into( imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            imageView.setImageResource( R.drawable.account_circle_black_24dp );
                            Toast.makeText( context, "something wrong", Toast.LENGTH_SHORT ).show();
                        }
                    } );
        }




        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
