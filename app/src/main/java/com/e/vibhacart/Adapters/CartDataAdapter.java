package com.e.vibhacart.Adapters;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.e.vibhacart.Home;
import com.e.vibhacart.Modals.CartModel;
import com.e.vibhacart.Modals.Product;
import com.e.vibhacart.R;
import com.e.vibhacart.Shopping_Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.angmarch.views.NiceSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CartDataAdapter/* extends RecyclerView.Adapter<CartDataAdapter.MyViewHolder>*/ {
    /*private List<Product> cartData;
    private int LayId;
    private Context context;
    private List<String> cart_item_id;

    List<String> dataset;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView totalCartValue;
    private Shopping_Cart shopping_cart;

    private boolean inStock = false;

    public CartDataAdapter(List<Product> cartData, int layId, Context context, List<String> cart_item_id, TextView totalCartValue) {
        this.cartData = cartData;
        LayId = layId;
        this.context = context;
        this.cart_item_id = cart_item_id;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        this.totalCartValue = totalCartValue;
        shopping_cart = new Shopping_Cart();
    }


    @NonNull
    @Override
    public CartDataAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (LayId == R.id.rec_cart_lay) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.cart_item, viewGroup, false );
            return new MyViewHolder( view );
        } else {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.cart_products, viewGroup, false );
            return new MyViewHolder( view );
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final CartDataAdapter.MyViewHolder myViewHolder, final int i) {
        try {
            int qty = Integer.parseInt( cartData.get( i ).getQuantity() );
            if (!cartData.get( i ).getImage().equalsIgnoreCase( "image" )) {
                Picasso.get().load( cartData.get( i ).getImage() ).into( myViewHolder.productImage );
            }
            myViewHolder.mProductName.setText( cartData.get( i ).getProductName() );
            myViewHolder.mQtySpinner.setSelectedIndex( qty-1 );
            myViewHolder.mProductNewPrice.setText( cartData.get( i ).getProductPrice() );
            myViewHolder.mProductOldPrice.setText( cartData.get( i ).getOldproductPrice() );
            myViewHolder.mProductOldPrice.setPaintFlags( myViewHolder.mProductOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );


            double price = Double.parseDouble( cartData.get( i ).getProductPrice() );
            double final_rate = qty * price;
            myViewHolder.mRate.setText( "Rs." + cartData.get( i ).getProductPrice() + "X" + cartData.get( i ).getQuantity() + " = Rs." + new DecimalFormat( "0.#" ).format( final_rate ) );

        } catch (Exception e) {

        }

        checkStockProduct( cart_item_id.get( i ), myViewHolder );

        myViewHolder.mQtySpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeQty(i,cart_item_id.get( i ),myViewHolder);
               // Toast.makeText( context, dataset.get( i ), Toast.LENGTH_SHORT ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        myViewHolder.mDeleteCartItem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteDialog( cart_item_id.get( i ), i, myViewHolder );
            }
        } );

    }

    private void changeQty(int quantity, String cart_id, final MyViewHolder myViewHolder) {
        myViewHolder.mLoadinglayout.setVisibility( View.VISIBLE );
        FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();

        mFirebaseFirestore
                .collection( "Users" )
                .document( currentUser.getPhoneNumber() )
                .collection( "My_Cart" )
                .document( cart_id )
                .update(
                        "quantity", "" + quantity
                ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    myViewHolder.mLoadinglayout.setVisibility( View.GONE );
                }
            }
        } );

    }

    private void checkStockProduct(String product_id, final MyViewHolder myViewHolder) {

        DocumentReference todaydealref = FirebaseFirestore.getInstance()
                .collection( "Products" )
                .document( product_id );
        todaydealref.get().addOnSuccessListener( new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Product product = documentSnapshot.toObject( Product.class );
                if (product.getStock() == 0) {
                    inStock = false;
                    myViewHolder.mItemCount.setVisibility( View.VISIBLE );
                    myViewHolder.mItemCount.setText( "Out Of stock!" );
                    //myViewHolder.
                } else if (product.getStock() <= 10) {
                    myViewHolder.mItemCount.setVisibility( View.VISIBLE );
                    myViewHolder.mItemCount.setText( "Only " + product.getStock() + " items left!" );
                } else {
                    myViewHolder.mItemCount.setVisibility( View.GONE );
                }

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );
    }

    private void openDeleteDialog(final String itemId, final int i, final MyViewHolder myViewHolder) {
        final String product_name = cartData.get( i ).getProductName();
        new AlertDialog.Builder( context )
                .setMessage( "Remove " + product_name + " from your cart??" )
                //.setIcon(R.drawable.ninja)
                .setPositiveButton( "YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                myViewHolder.mLoadinglayout.setVisibility( View.VISIBLE );
                                myViewHolder.mRecLay.setVisibility( View.GONE );
                                removeFromCart( itemId, product_name, i, myViewHolder );
                                dialog.cancel();
                            }
                        } )
                .setNegativeButton( "NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText( context, itemId + " Mike is not awesome for you. :()", Toast.LENGTH_SHORT ).show();
                        dialog.cancel();
                    }
                } ).show();


    }

    private void removeFromCart(String itemId, final String product_name, final int i, final MyViewHolder myViewHolder) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection( "Users" )
                .document( currentUser.getPhoneNumber() ).collection( "My_Cart" )
                .document( itemId )
                .delete()
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        myViewHolder.mLoadinglayout.setVisibility( View.GONE );
                        myViewHolder.mRecLay.setVisibility( View.VISIBLE );
                        Toast.makeText( context, product_name + " removed", Toast.LENGTH_SHORT ).show();
                        notifyItemRemoved( i );
                        cartData.remove( i );
                        notifyDataSetChanged();
                        if (cartData.isEmpty()) {
                            context.startActivity( new Intent( context, Home.class ) );
                        }

                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new AlertDialog.Builder( context )
                        .setTitle( "Error!!!" )
                        .setMessage( "Could'nt remove " + product_name + " from your cart.\n Please try again !!!" )
                        .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        } ).show();
            }
        } );

    }


    @Override
    public int getItemCount() {
        return cartData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductName, mProductNewPrice, mProductOldPrice, mItemCount, mQuantity, mRate;
        private ImageView mDeleteCartItem;
        RelativeLayout mLoadinglayout, mRecLay;
        private ImageView productImage;
        private NiceSpinner mQtySpinner;

        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            mLoadinglayout = (RelativeLayout) itemView.findViewById( R.id.loading_layout );
            mRecLay = (RelativeLayout) itemView.findViewById( R.id.cart_lay );
            mProductName = (TextView) itemView.findViewById( R.id.product_name );
            mQuantity = (TextView) itemView.findViewById( R.id.product_qty );
            mProductNewPrice = (TextView) itemView.findViewById( R.id.product_price );
            mProductOldPrice = (TextView) itemView.findViewById( R.id.old_product_price );
            mDeleteCartItem = (ImageView) itemView.findViewById( R.id.imageView_delete );
            productImage = (ImageView) itemView.findViewById( R.id.cart_product_image );
            mItemCount = (TextView) itemView.findViewById( R.id.textView41 );
            mRate = (TextView) itemView.findViewById( R.id.product_taxes );
            //dataset = new ArrayList<>( Arrays.asList( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" ) );

            mQtySpinner = (NiceSpinner) itemView.findViewById(R.id.nice_spinner);
            dataset = new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5"));
            mQtySpinner.attachDataSource(dataset);


        }
    }*/
}
