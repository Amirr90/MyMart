package com.e.vibhacart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.vibhacart.Modals.Address;
import com.e.vibhacart.Modals.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class VibaCash extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        String getStringFromAccountPage = getIntent().getStringExtra( "value" );
        if (getStringFromAccountPage.equals( "AccountWallet" )) {
            setContentView( R.layout.activity_viba_cash );
            Toolbar toolbar = findViewById( R.id.toolbar );
            setToolbar( toolbar );
            getSupportActionBar().setTitle( "VibaCash" );
        }
        else if (getStringFromAccountPage.equals( "AccountMyOrder" )) {
            setContentView( R.layout.account_myorder );
            Toolbar toolbar = findViewById( R.id.toolbar );
            setToolbar( toolbar );
            getSupportActionBar().setTitle( "My Orders" );

            RelativeLayout NoCart = (RelativeLayout) findViewById( R.id.no_cart );
            RecyclerView recyclerView = (RecyclerView) findViewById( R.id.acc_order_rec );
            List<Order> orders = new ArrayList<>();
            orders.add( new Order( "jhfvg", "hg", "jhjh" ) );
            orders.add( new Order( "jhfvg", "hg", "jhjh" ) );
            orders.add( new Order( "jhfvg", "hg", "jhjh" ) );
            AccountOrderAdapter adapter = new AccountOrderAdapter( orders );
            recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
            recyclerView.setAdapter( adapter );

        }
        else if (getStringFromAccountPage.equals( "AccountEdit" )) {


            setContentView( R.layout.account_edit );
            Toolbar toolbar = findViewById( R.id.toolbar );
            setToolbar( toolbar );
            getSupportActionBar().setTitle( "Edit Profile" );
            RelativeLayout MyAddress = (RelativeLayout) findViewById( R.id.acc_my_address_lay );
            RelativeLayout SavedCards = (RelativeLayout) findViewById( R.id.saved_cards );

            MyAddress.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView( R.layout.account_my_address );
                    Toolbar toolbar = findViewById( R.id.toolbar );
                    setToolbar( toolbar );
                    getSupportActionBar().setTitle( "Edit Address" );
                    RecyclerView recyclerView = (RecyclerView) findViewById( R.id.address_rec );
                    loadAddress( recyclerView );
                    Button Add_NewAddressBtn = (Button) findViewById( R.id.add_new_address_btn );
                    Add_NewAddressBtn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity( new Intent( VibaCash.this, EditAddress.class ) );
                        }
                    } );
                }
            } );

            SavedCards.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent( VibaCash.this,SavedCards.class );
                    startActivity( intent );
                }
            } );
        }
        else if (getStringFromAccountPage.equals( "ShareAndEarn" ))
        {
            setContentView( R.layout.share_and_earn );
            Toolbar toolbar = findViewById( R.id.toolbar );
            setToolbar( toolbar );
            getSupportActionBar().setTitle( "Share And Earn" );
            Button ShareEarn=(Button)findViewById( R.id.btn_share_earn ) ;

            ShareEarn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent share = new Intent(android.content.Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                    // Add data to the intent, the receiving app will decide
                    // what to do with it.
                    share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
                    share.putExtra(Intent.EXTRA_TEXT, "http://www.ndmeea.com");

                    startActivity(Intent.createChooser(share, "Share link!"));
                }
            } );
        }

    }

    private void loadAddress(RecyclerView recyclerView) {
        List<Address> addressList = new ArrayList<>();
        addressList.add( new Address( "NDMEEA", "UGF 7 HO NO 57 A, HARIHAR NAGAR,INDRA NAGAR, LUCKNOW", "+919044000123", true ) );
        addressList.add( new Address( "NDMEEA", "UGF 7 HO NO 57 A, HARIHAR NAGAR,INDRA NAGAR, LUCKNOW", "+919044000123", false ) );
        AddressAdapter addressAdapter = new AddressAdapter( addressList );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setAdapter( addressAdapter );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cart:
                Toast.makeText( getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG ).show();
                startActivity( new Intent( VibaCash.this, Shopping_Cart.class ) );
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.cart_menu, menu );
        return true;
    }

    private void loadOrder(final List<Order> orderList, final RecyclerView recyclerView, final RelativeLayout noCart) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child( "orders" );

        orderRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = currentUser.getUid();
                if (dataSnapshot.hasChild( uid )) {
                    orderRef.child( currentUser.getUid() ).addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                                Order value = dataSnapshot1.getValue( Order.class );
                                orderList.add( value );

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                }

                recyclerView.setVisibility( View.GONE );
                noCart.setVisibility( View.VISIBLE );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }


    private void setToolbar(Toolbar toolbar) {
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );

    }


    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class AccountOrderAdapter extends RecyclerView.Adapter<AccountOrderAdapter.MyViewHolder> {
        List<Order> orders;

        public AccountOrderAdapter(List<Order> orders) {
            this.orders = orders;
        }

        @NonNull
        @Override
        public AccountOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.order_history_lay, viewGroup, false );
            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull AccountOrderAdapter.MyViewHolder myViewHolder, int i) {

            myViewHolder.OrderLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent( VibaCash.this,FullOrderDetail.class );
                    startActivity( intent );
                }
            } );
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            RelativeLayout OrderLayout;
            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                OrderLayout=(RelativeLayout)itemView.findViewById( R.id.order_layout ) ;
            }
        }
    }


    private class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
        List<Address> addressList;

        public AddressAdapter(List<Address> addressList) {
            this.addressList = addressList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.address_view, viewGroup, false );
            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

            try {
                myViewHolder.name.setText( addressList.get( i ).getName() );
                myViewHolder.address.setText( addressList.get( i ).getAddress() );
                myViewHolder.mobile.setText( addressList.get( i ).getMobile() );
                myViewHolder.primary__address.setVisibility( addressList.get( i ).isPrimaryAddress() ? View.VISIBLE : View.GONE );
            } catch (Exception e) {

            }
            myViewHolder.edit_image_address.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openEditAddresDialog( addressList.get( i ), i );
                }
            } );

        }

        private void openEditAddresDialog(final Address address, final int i) {
            final CharSequence[] items = {"Edit", "Delete"};

            AlertDialog.Builder builder = new AlertDialog.Builder( VibaCash.this );
            builder.setTitle( "Make your selection" );

            builder.setItems( items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    // will toast your selection
                    //showToast("Name: " + items[item]);
                    Toast.makeText( VibaCash.this, "Name: " + items[item], Toast.LENGTH_SHORT ).show();

                    switch (item) {
                        case 0:
                            Intent intent = new Intent( VibaCash.this, EditAddress.class );
                            intent.putExtra( "name", addressList.get( i ).getName() );
                            intent.putExtra( "address", addressList.get( i ).getAddress() );
                            intent.putExtra( "mobile", addressList.get( i ).getMobile() );
                            intent.putExtra( "isPrimary", ""+addressList.get( i ).isPrimaryAddress() );
                            startActivity( intent );

                    }
                    dialog.dismiss();

                }
            } ).show();
        }

        @Override
        public int getItemCount() {
            return addressList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView name, address, mobile, primary__address;
            private ImageView edit_image_address;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                name = (TextView) itemView.findViewById( R.id.name );
                address = (TextView) itemView.findViewById( R.id.address );
                mobile = (TextView) itemView.findViewById( R.id.mobile );
                primary__address = (TextView) itemView.findViewById( R.id.primary_header );
                edit_image_address = (ImageView) itemView.findViewById( R.id.edit_address_image );
            }
        }
    }
}
