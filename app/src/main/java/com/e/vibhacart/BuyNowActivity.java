package com.e.vibhacart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.vibhacart.Modals.CartModel;
import com.e.vibhacart.Modals.Product;
import com.example.circulardialog.CDialog;
import com.example.circulardialog.extras.CDConstants;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import me.himanshusoni.quantityview.QuantityView;

public class BuyNowActivity extends AppCompatActivity implements PaymentResultListener {

    LinearLayout PromoCodeLay, EditPromoCodeLay, CreditCardLay, CashOndeliveryLay, EditCashOndelivery, DebitCardLay, WalletLay, EditWallet;
    ImageView ImagePromoCode, ImageCreditcard, ImageCashOndelivery, ImageDebitcard, ImageWallet;
    RelativeLayout EditCreditCardLay, EditDebitCardLay;
    boolean isPromoLayVisible = false;
    boolean isCreditLayVisible = false;
    boolean isCashOnDelivery = false;
    boolean isDebitLayVisible = false;
    boolean isWallet = false;
    RecyclerView BuyNowRec;
    Button mPlaceOrderBtn;

    List<CartModel> cartData = new ArrayList<>();

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    double TotalCartvalue = 0;

    Utils utils;
    double DISCOUNT = 30;
    double DELIVERY_CHARGE = 0;
    double GST = 0;

    double FINAL_PRICE = 0;

    private TextView product_price, product_discount, product_gst, product_total_price1, product_totalˍprice2, product_delivry_charge;
    private LinearLayout RazorPay, VibaCash, SpinKitLayout;
    private TextView mCashonDeliveryTextView, TextVibaCash;
    private TextView mAddressName, mAddressPhone, mAddresFull;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_buy_now );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setToolbar( toolbar );

        mPlaceOrderBtn = (Button) findViewById( R.id.place_order_btn );

        utils = new Utils();
        findViwes();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Checkout.preload( getApplicationContext() );
        setVisibilityAction();

        setBuyNowCartRecItems();

        EditCashOndelivery.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pay_mode = "CashOnDelivery";
                openBottomConfirmDialog( pay_mode );
            }
        } );
        setVibaCash();
        TextVibaCash.setText( "200" );
        RazorPay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder( "RazorPay" );
            }
        } );

        VibaCash.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double Vibacash = Double.parseDouble( TextVibaCash.getText().toString() );
                if (Vibacash > FINAL_PRICE) {

                    openBottomConfirmDialog( "VibaCash" );
                    return;
                }
                Toast.makeText( BuyNowActivity.this, "insufficient VibaCash", Toast.LENGTH_SHORT ).show();
            }

        } );


    }

    private void openBottomConfirmDialog(final String pay_mode) {
        new BottomDialog.Builder( BuyNowActivity.this )
                .setTitle( "Confirm Order!" )
                .setContent( "Press Confirm to place order!" )
                .setPositiveText( "Confirm" )
                .setPositiveBackgroundColorResource( R.color.blue_dark )
                //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                .setPositiveTextColorResource( android.R.color.white )
                //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                .onPositive( new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        placeOrder( pay_mode );

                    }
                } )
                .setNegativeText( "Cancel" )
                .setNegativeTextColorResource( R.color.blue_dark )
                .onNegative( new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {
                        bottomDialog.dismiss();
                    }
                } )
                .show();
    }

    private void setVibaCash() {

    }

    private void setToolbar(Toolbar toolbar) {
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( "Buy Now" );
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setBuyNowCartRecItems() {

        BuyNowRec = (RecyclerView) findViewById( R.id.buy_now_cart_recyclerView );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        BuyNowRec.setLayoutManager( layoutManager );
        //List<CartModel> cartData=new ArrayList<>(  );
        List<String> cart_item_id = new ArrayList<>();
        BuyNowAdapter buyNowAdapter = new BuyNowAdapter( cartData, R.id.buy_now_cart_recyclerView, this, cart_item_id );
        BuyNowRec.setAdapter( buyNowAdapter );

        setCartData( buyNowAdapter, cartData, cart_item_id );
    }

    private void setCartData(final BuyNowAdapter cartDataAdapter, final List<CartModel> cartData, final List<String> cart_item_id) {

        CollectionReference todaydealref = FirebaseFirestore.getInstance()
                .collection( "Users" )
                .document( currentUser.getPhoneNumber() )
                .collection( "My_Cart" );


        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                CartModel banners1 = bannerSnapshot.toObject( CartModel.class );
                                cart_item_id.add( bannerSnapshot.getId() );
                                TotalCartvalue = TotalCartvalue + ((Double.parseDouble( banners1.getProductPrice() ) * Double.parseDouble( banners1.getQuantity() )));
                                cartData.add( banners1 );
                            }
                            cartDataAdapter.notifyDataSetChanged();
                            setPriceChart();
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );
        cartDataAdapter.notifyDataSetChanged();


    }

    private void setPriceChart() {

        try {
            SpinKitLayout.setVisibility( View.INVISIBLE );
        } catch (Exception e) {
        }

        product_price.setText( utils.RS + (new DecimalFormat( ".#" ).format( TotalCartvalue )) );
        if (TotalCartvalue >= 150) {
            product_delivry_charge.setText( "FREE" );
            product_delivry_charge.setTextColor( getResources().getColor( R.color.green ) );
        } else {
            product_delivry_charge.setTextColor( getResources().getColor( R.color.backgroundColor ) );
            DELIVERY_CHARGE = 59;
            product_delivry_charge.setText( "+" + utils.RS + DELIVERY_CHARGE );
        }

        //Set Discount
        product_discount.setText( "-" + utils.RS + new DecimalFormat( ".#" ).format( DISCOUNT ) );

        //Setting GSt
        try {
            GST = ((18 * TotalCartvalue) / 100);
            product_gst.setText( "+" + utils.RS + new DecimalFormat( ".#" ).format( GST ) );
        } catch (Exception e) {

        }
        FINAL_PRICE = TotalCartvalue + GST - DISCOUNT + DELIVERY_CHARGE;
        mPlaceOrderBtn.setText( utils.PAY_RS + (new DecimalFormat( ".#" ).format( FINAL_PRICE )) );
        product_total_price1.setText( utils.PAY_RS + new DecimalFormat( ".#" ).format( FINAL_PRICE ) );
        product_totalˍprice2.setText( utils.PAY_RS + new DecimalFormat( ".#" ).format( FINAL_PRICE ) );
        mCashonDeliveryTextView.setText( utils.RS + new DecimalFormat( ".#" ).format( FINAL_PRICE ) );
    }

    private void placeOrder(String order_type) {
        if (order_type.equals( "RazorPay" )) {
            startRazorPayPayment();
            return;
        }


        setContentView( R.layout.activity_placing_order_layout );

        final RelativeLayout mOrderPlacinglayout = (RelativeLayout) findViewById( R.id.placing_layout );

        String OrderId = "" + System.currentTimeMillis();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get a new write batch
        WriteBatch batch = db.batch();

        for (int a = 0; a < cartData.size(); a++) {
            DocumentReference nycRef = db.collection( "Users" )
                    .document( currentUser.getPhoneNumber() )
                    .collection( "Orders" )
                    .document( OrderId )
                    .collection( "Order_Items" )
                    .document();
            batch.set( nycRef, cartData.get( a ) );

        }

        //reference for Order detail
        DocumentReference nycRef = db.collection( "Users" )
                .document( currentUser.getPhoneNumber() )
                .collection( "Orders" )
                .document( OrderId );

        HashMap<String, String> map = new HashMap<>();
        map.put( "order_date", OrderId );
        map.put( "pay_mode", order_type );
        map.put( "status", "Started" );
        map.put( "product_price", "" + TotalCartvalue );
        map.put( "discount", "" + DISCOUNT );
        map.put( "delivery_charge", "" + DELIVERY_CHARGE );
        map.put( "taxes", "" + GST );
        map.put( "net_price", "" + FINAL_PRICE );
        map.put( "uid", currentUser.getUid() );
        map.put( "mobile", currentUser.getPhoneNumber() );
        batch.set( nycRef, map );

        //reference for Order Address
        DocumentReference addressRef = db.collection( "Users" )
                .document( currentUser.getPhoneNumber() )
                .collection( "Orders" )
                .document( OrderId )
                .collection( "Order_Address" )
                .document();
        Map<String, String> addressMap = new HashMap<>();
        addressMap.put( "mobile", mAddressPhone.getText().toString() );
        addressMap.put( "address", mAddresFull.getText().toString() );
        addressMap.put( "name", mAddressName.getText().toString() );
        batch.set( addressRef, addressMap );


        batch.commit().addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mOrderPlacinglayout.setVisibility( View.GONE );
                    Toast.makeText( BuyNowActivity.this, "Order placed successfully", Toast.LENGTH_SHORT ).show();
                    showPlacedOrderDialog( true );
                }

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( BuyNowActivity.this, "try again!!", Toast.LENGTH_SHORT ).show();
                showPlacedOrderDialog( false );
            }
        } );


    }

    private void startRazorPayPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage( R.drawable.rzp_logo );

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put( "name", "NDMEEA" );

            /**
             * Description can be anything
             * eg: Order #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put( "description", "Order #123456" );
            options.put( "order_id", "order_9A33XWu170gUtm" );
            options.put( "currency", "INR" );

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            String Amount = String.valueOf( FINAL_PRICE * 100 );
            options.put( "amount", Amount );

            checkout.open( activity, options );
        } catch (Exception e) {
            Toast.makeText( activity, "Error in starting Razorpay Checkout", Toast.LENGTH_SHORT ).show();
        }

    }

    private void showPlacedOrderDialog(boolean b) {
        if (b) {
            new FancyGifDialog.Builder( this )
                    .setTitle( "Yah!!" )
                    .setMessage( "Your Order Placed Successfully, sit relaxed we will inform you once order confirmed" )
                    .setNegativeBtnText( "Home Page" )
                    .setPositiveBtnBackground( "#FF4081" )
                    .setPositiveBtnText( "Order Detail" )
                    .setNegativeBtnBackground( "#FFA9A7A8" )
                    .setGifResource( R.drawable.checkk_two )   //Pass your Gif here
                    .isCancellable( false )
                    .OnPositiveClicked( new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            Toast.makeText( BuyNowActivity.this, "Ok", Toast.LENGTH_SHORT ).show();
                        }
                    } )
                    .OnNegativeClicked( new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            Toast.makeText( BuyNowActivity.this, "Cancel", Toast.LENGTH_SHORT ).show();
                        }
                    } )
                    .build();
            /*new CDialog(this).createAlert("Placed Successfully",
                    CDConstants.SUCCESS,   // Type of dialog
                    CDConstants.LARGE)    //  size of dialog
                    .setAnimation(CDConstants.SCALE_FROM_BOTTOM_TO_TOP)     //  Animation for enter/exit
                    .setDuration(3000)   // in milliseconds
                    .setTextSize(CDConstants.LARGE_TEXT_SIZE)  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                    .show();*/

           /*startActivity( new Intent( BuyNowActivity.this,FullOrderDetail.class ) );
           finish();*/

        }
        if (!b) {
            new CDialog( this ).createAlert( "Failed to place order",
                    CDConstants.ERROR,   // Type of dialog
                    CDConstants.LARGE )    //  size of dialog
                    .setAnimation( CDConstants.SCALE_FROM_BOTTOM_TO_TOP )     //  Animation for enter/exit
                    .setDuration( 3000 )   // in milliseconds
                    .setTextSize( CDConstants.LARGE_TEXT_SIZE )  // CDConstants.LARGE_TEXT_SIZE, CDConstants.NORMAL_TEXT_SIZE
                    .show();
        }
    }

    private void findViwes() {
        product_price = (TextView) findViewById( R.id.product_price2 );
        product_discount = (TextView) findViewById( R.id.product_discount2 );
        product_gst = (TextView) findViewById( R.id.product_gst );
        product_total_price1 = (TextView) findViewById( R.id.product_final_price2 );
        product_totalˍprice2 = (TextView) findViewById( R.id.product_price3 );
        product_delivry_charge = (TextView) findViewById( R.id.product_delivery_charge );
        mCashonDeliveryTextView = (TextView) findViewById( R.id.cash_on_textView );
        mAddressName = (TextView) findViewById( R.id.addres_name );
        mAddressPhone = (TextView) findViewById( R.id.address_ˍmobile );
        mAddresFull = (TextView) findViewById( R.id.address_full );
        RazorPay = (LinearLayout) findViewById( R.id.razor_pay );
        VibaCash = (LinearLayout) findViewById( R.id.vibacash );
        TextVibaCash = (TextView) findViewById( R.id.textWallet );
        SpinKitLayout = (LinearLayout) findViewById( R.id.spin_kit_buy_now );


    }

    private void setVisibilityAction() {
        ImagePromoCode = (ImageView) findViewById( R.id.promo_code_arrow );
        PromoCodeLay = (LinearLayout) findViewById( R.id.promo_code_lay );
        EditPromoCodeLay = (LinearLayout) findViewById( R.id.edit_promo_code_lay );
        PromoCodeLay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPromoLayVisible) {
                    EditPromoCodeLay.setVisibility( View.VISIBLE );
                    isPromoLayVisible = true;
                    return;
                }

                if (isPromoLayVisible) {
                    EditPromoCodeLay.setVisibility( View.GONE );
                    isPromoLayVisible = false;
                }

            }
        } );


        //credit card Layout
        ImageCreditcard = (ImageView) findViewById( R.id.debit_card_arrow );
        CreditCardLay = (LinearLayout) findViewById( R.id.debit_card_lay );
        EditCreditCardLay = (RelativeLayout) findViewById( R.id.edit_debit_card_lay );
        CreditCardLay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCreditLayVisible) {
                    EditCreditCardLay.setVisibility( View.VISIBLE );
                    isCreditLayVisible = true;
                    return;
                }

                if (isCreditLayVisible) {
                    EditCreditCardLay.setVisibility( View.GONE );
                    isCreditLayVisible = false;
                }

            }
        } );


        //debit card Layout
        ImageDebitcard = (ImageView) findViewById( R.id.credit_card_arrow );
        DebitCardLay = (LinearLayout) findViewById( R.id.credit_card_lay );
        EditDebitCardLay = (RelativeLayout) findViewById( R.id.edit_credit_card_lay );
        DebitCardLay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isDebitLayVisible) {
                    EditDebitCardLay.setVisibility( View.VISIBLE );
                    isDebitLayVisible = true;
                    return;
                }

                if (isDebitLayVisible) {
                    EditDebitCardLay.setVisibility( View.GONE );
                    isDebitLayVisible = false;
                }

            }
        } );


        //cash On delivery Layout
        ImageCashOndelivery = (ImageView) findViewById( R.id.cash_on_delivery_arrow );
        CashOndeliveryLay = (LinearLayout) findViewById( R.id.cash_on_delivery_lay );
        EditCashOndelivery = (LinearLayout) findViewById( R.id.edit_cash_on_delivery );
        CashOndeliveryLay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCashOnDelivery) {
                    EditCashOndelivery.setVisibility( View.VISIBLE );
                    isCashOnDelivery = true;
                    return;
                }

                if (isCashOnDelivery) {
                    EditCashOndelivery.setVisibility( View.GONE );
                    isCashOnDelivery = false;
                }

            }
        } );


        //wallet Layout
        ImageWallet = (ImageView) findViewById( R.id.wallet_arrow );
        WalletLay = (LinearLayout) findViewById( R.id.wallet_lay );
        EditWallet = (LinearLayout) findViewById( R.id.edit_wallet );
        WalletLay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWallet) {
                    EditWallet.setVisibility( View.VISIBLE );
                    isWallet = true;
                    return;
                }

                if (isWallet) {
                    EditWallet.setVisibility( View.GONE );
                    isWallet = false;
                }

            }
        } );
    }

    @Override
    public void onPaymentSuccess(String s) {
        placeOrder( "RAZOR_PAY" );
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText( this, s, Toast.LENGTH_SHORT ).show();
    }

    private class BuyNowAdapter extends RecyclerView.Adapter<BuyNowAdapter.MyViewHolder> {
        private List<CartModel> cartData;
        private int buy_now_cart_recyclerView;
        private Context context;
        private List<String> cart_item_id;


        public BuyNowAdapter(List<CartModel> cartData, int buy_now_cart_recyclerView, Context context, List<String> cart_item_id) {
            this.cartData = cartData;
            this.buy_now_cart_recyclerView = buy_now_cart_recyclerView;
            this.context = context;
            this.cart_item_id = cart_item_id;
        }

        @NonNull
        @Override
        public BuyNowAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.cart_products, viewGroup, false );
            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull BuyNowAdapter.MyViewHolder myViewHolder, int i) {
            setQuantity( myViewHolder, i );

        }

        private void setQuantity(final MyViewHolder myViewHolder, final int i) {
            myViewHolder.quantityView.setOnQuantityChangeListener( new QuantityView.OnQuantityChangeListener() {
                @Override
                public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                    myViewHolder.progressBar.setVisibility( View.VISIBLE );
                    Toast.makeText( context, "quatity " + newQuantity, Toast.LENGTH_SHORT ).show();
                    setQuantityToCart( newQuantity, myViewHolder, cart_item_id.get( i ) );


                }

                @Override
                public void onLimitReached() {
                    Toast.makeText( context, "You can not Add more than 10 ", Toast.LENGTH_SHORT ).show();

                }
            } );
            Double Price=Double.parseDouble( cartData.get( i ).getProductPrice() )*Double.parseDouble( cartData.get( i ).getQuantity() );
            myViewHolder.mPrice.setText( "Rs."+new DecimalFormat( "0.#" ).format( Price ) );
        }

        private void setQuantityToCart(int newQuantity, final MyViewHolder myViewHolder, final String cart_item_id) {

            SpinKitLayout.setVisibility( View.VISIBLE );
            final CollectionReference todaydealref = FirebaseFirestore.getInstance()
                    .collection( "Users" )
                    .document( currentUser.getPhoneNumber() )
                    .collection( "My_Cart" );
            FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();

            mFirebaseFirestore
                    .collection( "Users" )
                    .document( currentUser.getPhoneNumber() )
                    .collection( "My_Cart" )
                    .document( cart_item_id )
                    .update(
                            "quantity", "" + newQuantity
                    )
                    .addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                myViewHolder.progressBar.setVisibility( View.GONE );

                                DocumentReference reference = todaydealref.document( cart_item_id );
                                reference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                        if (e != null) {
                                            Toast.makeText( context, "failed", Toast.LENGTH_SHORT ).show();
                                            return;
                                        }

                                        if (documentSnapshot != null && documentSnapshot.exists()) {
                                            String qua = documentSnapshot.getString( "quantity" );
                                            String product_price = documentSnapshot.getString( "productPrice" );
                                            TotalCartvalue = TotalCartvalue + ((Double.parseDouble( product_price ) * Double.parseDouble( qua )));
                                            setPriceChart();

                                        } else {

                                        }
                                    }
                                } );
                            }
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    myViewHolder.progressBar.setVisibility( View.GONE );
                    Toast.makeText( context, "try again", Toast.LENGTH_SHORT ).show();
                }
            } );
        }

        @Override
        public int getItemCount() {
            return cartData.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private QuantityView quantityView;
            private ProgressBar progressBar;
            private TextView mPrice;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                quantityView = (QuantityView) itemView.findViewById( R.id.quantityView );
                mPrice = (TextView) itemView.findViewById( R.id.textView27 );
                progressBar = (ProgressBar) itemView.findViewById( R.id.progressBar2 );


            }
        }
    }


}
