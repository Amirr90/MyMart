package com.e.vibhacart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.vibhacart.Modals.Product;
import com.e.vibhacart.R;
import com.e.vibhacart.test;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter {

    List<Product> mProducts;
    Context mContext;
    FirebaseUser currentUser;


    public static final int LOADING_ITEM = 0;
    public static final int PRODUCT_ITEM = 1;
    int LoadingItemPos;
    public boolean loading = false;

    Shimmer shimmer;

    public ProductsAdapter(Context mContext, FirebaseUser currentUser) {
        mProducts = new ArrayList<>();
        this.mContext = mContext;
        this.currentUser = currentUser;

    }

    //method to add products as soon as they fetched
    public void addProducts(List<Product> products) {
        int lastPos = mProducts.size();
        this.mProducts.addAll( products );
        notifyItemRangeInserted( lastPos, mProducts.size() );
    }


    @Override
    public int getItemViewType(int position) {
        Product currentProduct = mProducts.get( position );
        if (currentProduct.isLoading()) {
            return LOADING_ITEM;
        } else {
            return PRODUCT_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( parent.getContext() );
        //Check which view has to be populated
        if (viewType == LOADING_ITEM) {
            View row = inflater.inflate( R.layout.custom_row_loading, parent, false );
            return new LoadingHolder( row );
        } else if (viewType == PRODUCT_ITEM) {
            View row = inflater.inflate( R.layout.row_products, parent, false );
            return new ProductHolder( row );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //get current product
        shimmer = new Shimmer();
        final Product currentProduct = mProducts.get( position );
        if (holder instanceof ProductHolder) {
            final ProductHolder productHolder = (ProductHolder) holder;
            //bind products information with view
           // Picasso.get().load( currentProduct.getImageResourceId() ).into( productHolder.imageViewProductThumb );
            productHolder.textViewProductName.setText( currentProduct.getProductName() );
            productHolder.textViewProductPrice.setText( currentProduct.getProductPrice() );
            if (currentProduct.isNew()) {
                productHolder.shimmerTextView.setVisibility( View.VISIBLE );
                shimmer.start( productHolder.shimmerTextView );
            } else {
                productHolder.shimmerTextView.setVisibility( View.GONE );
            }

            productHolder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // user selected product now you can show details of that product
                    Toast.makeText( mContext, "Selected " + currentProduct.getProductName(), Toast.LENGTH_SHORT ).show();
                    mContext.startActivity( new Intent( mContext, test.class ) );
                }
            } );

            productHolder.AddToCartImage.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddDataToCart( productHolder, position );
                }
            } );

            productHolder.OldPriceTextView.setText( currentProduct.getOldproductPrice() ); // SomeString = your old price
            productHolder.OldPriceTextView.setPaintFlags( productHolder.OldPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );


        }

    }

    private void AddDataToCart(final ProductHolder productHolder, final int position) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "Users" )
                .document( currentUser.getPhoneNumber() ).collection( "My_Cart" )
                .document( "" + System.currentTimeMillis() )
                .set( mProducts.get( position ) )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                            Toast.makeText( mContext, mProducts.get( position ).getProductName() + " to the cart", Toast.LENGTH_SHORT ).show();
                    }
                } )
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( mContext, "Please Try Again", Toast.LENGTH_SHORT ).show();
                    }
                } );


    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    //Holds view of product with information
    private class ProductHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProductThumb, AddToCartImage;
        TextView textViewProductName, textViewProductPrice, textViewNew, OldPriceTextView;
        ShimmerTextView shimmerTextView;


        public ProductHolder(View itemView) {
            super( itemView );
            imageViewProductThumb = itemView.findViewById( R.id.imageViewProductThumb );
            textViewProductName = itemView.findViewById( R.id.textViewProductName );
            textViewProductPrice = itemView.findViewById( R.id.textViewProductPrice );
            textViewNew = itemView.findViewById( R.id.textViewNew );
            shimmerTextView = (ShimmerTextView) itemView.findViewById( R.id.shimmer_tv );
            OldPriceTextView = (TextView) itemView.findViewById( R.id.old_price_home );
            AddToCartImage = (ImageView) itemView.findViewById( R.id.cart_home );

        }
    }

    //holds view of loading item
    private class LoadingHolder extends RecyclerView.ViewHolder {
        public LoadingHolder(View itemView) {
            super( itemView );
        }
    }

    //method to show loading
    public void showLoading() {
        Product product = new Product();
        product.setLoading( true );
        mProducts.add( product );
        LoadingItemPos = mProducts.size();
        notifyItemInserted( mProducts.size() );
        loading = true;
    }

    //method to hide loading
    public void hideLoading() {
        if (LoadingItemPos <= mProducts.size()) {
            mProducts.remove( LoadingItemPos - 1 );
            notifyItemRemoved( LoadingItemPos );
            loading = false;
        }

    }
}