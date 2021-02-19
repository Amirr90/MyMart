package com.e.vibhacart;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FullOrderDetail extends AppCompatActivity {
    TextView trackBtn;
    LinearLayout trackLayout;
    private boolean isTrackLayoutShowing=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_full_order_detail );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Order Detail" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        trackBtn=(TextView)findViewById( R.id.track_btn );
        trackLayout=(LinearLayout)findViewById( R.id.tracking_layout ) ;

        trackBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTrackLayoutShowing)
                {
                    Drawable img = getResources().getDrawable( R.drawable.arrow_down_black_24dp );
                    trackBtn.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    isTrackLayoutShowing=true;
                    trackLayout.setVisibility( View.VISIBLE );
                }
                else
                    {
                    Drawable img = getResources().getDrawable( R.drawable.ic_keyboard_arrow_right_white_24dp );
                    trackBtn.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
                    isTrackLayoutShowing=false;
                    trackLayout.setVisibility( View.GONE );
                }
            }
        } );



    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cart:
                Toast.makeText( getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG ).show();
                startActivity( new Intent( FullOrderDetail.this, Shopping_Cart.class ) );
                return true;
            case R.id.action_home:
                startActivity( new Intent( FullOrderDetail.this,Home.class ).putExtra( "value","home" ) );
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.cart_menu, menu );
        return true;
    }

}
