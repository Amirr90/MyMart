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
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter2 extends RecyclerView.Adapter<ProductAdapter2.MyViewHolder> {

    List<Product> products;
    List<String> products_id;
    FirebaseUser currentUser;
    Context mContext;
    Shimmer shimmer;

    public ProductAdapter2(List<Product> products, FirebaseUser currentUser, Context mContext,List<String> products_id) {
        this.products = products;
        this.currentUser = currentUser;
        this.mContext = mContext;
        this.products_id=products_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.row_products, viewGroup, false);
        return  new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder productHolder, final int position) {

        final Product currentProduct = products.get( position );
        if (!currentProduct.getImage().equalsIgnoreCase( "image" ))
            Picasso.get().load( currentProduct.getImage() ).into( productHolder.imageViewProductThumb );
        productHolder.textViewProductName.setText( currentProduct.getProductName() );
        productHolder.textViewProductPrice.setText( currentProduct.getProductPrice() );
        if (currentProduct.isNew()) {
            productHolder.shimmerTextView.setVisibility( View.VISIBLE );
            shimmer.start( productHolder.shimmerTextView );
        } else {
            productHolder.shimmerTextView.setVisibility( View.GONE );
        }

        productHolder.mtextViewProductDiscount.setText( currentProduct.getDiscount()+"% OFF" );
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

    private void AddDataToCart(MyViewHolder productHolder, final int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection( "Users" )
                .document( currentUser.getPhoneNumber() ).collection( "My_Cart" )
                .document( products_id.get( position ) )
                .set( products.get( position ) )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText( mContext, products.get( position ).getProductName() + " to the cart", Toast.LENGTH_SHORT ).show();
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
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProductThumb, AddToCartImage;
        TextView textViewProductName, textViewProductPrice, textViewNew, OldPriceTextView,mtextViewProductDiscount;
        ShimmerTextView shimmerTextView;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            imageViewProductThumb = itemView.findViewById( R.id.imageViewProductThumb );
            textViewProductName = itemView.findViewById( R.id.textViewProductName );
            mtextViewProductDiscount = itemView.findViewById( R.id.textViewProductDiscount );
            textViewProductPrice = itemView.findViewById( R.id.textViewProductPrice );
            textViewNew = itemView.findViewById( R.id.textViewNew );
            shimmerTextView = (ShimmerTextView) itemView.findViewById( R.id.shimmer_tv );
            OldPriceTextView = (TextView) itemView.findViewById( R.id.old_price_home );
            AddToCartImage = (ImageView) itemView.findViewById( R.id.cart_home );
        }
    }

}
