package com.e.vibhacart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.e.vibhacart.Adapters.HomeSliderAdapter;
import com.e.vibhacart.Adapters.ProductDetailSliderAdapter;
import com.e.vibhacart.Interfaces.IProductBannerLoadiListner;
import com.e.vibhacart.Modals.ProductBanner;
import com.e.vibhacart.Service.PicassoImageLoadingservice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class Product_Detail extends AppCompatActivity implements IProductBannerLoadiListner {

    Slider banner_slider;
    IProductBannerLoadiListner iBannerLoadiListner;
    List<ProductBanner> banners;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product__detail );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        FloatingActionButton fab = (FloatingActionButton) findViewById( R.id.fab_order );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );
        Slider.init( new PicassoImageLoadingservice() );

        banner_slider=(Slider)findViewById( R.id.banner_slider_product );
        iBannerLoadiListner=this;

        loadProductBanner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.product_menu, menu );
        return true;
    }

    private void loadProductBanner() {
        banners=new ArrayList<>();
        CollectionReference bannerRef;
        bannerRef = FirebaseFirestore.getInstance().collection( "Todays's_category" );
        bannerRef.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot bannerSnapshot :task.getResult() )
                            {
                                ProductBanner banners1=bannerSnapshot.toObject( ProductBanner.class );
                                banners.add( banners1 );
                            }
                            iBannerLoadiListner.onBannerLoadSuccess( banners );
                        }

                    }
                } ) .addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBannerLoadiListner.onBannerLoadError( e.getMessage() );
            }
        } );


    }


    @Override
    public void onBannerLoadSuccess(List<ProductBanner> banners) {
        banner_slider.setAdapter( new ProductDetailSliderAdapter(banners) );
    }

    @Override
    public void onBannerLoadError(String message) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();

    }
}
