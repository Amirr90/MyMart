package com.e.vibhacart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditAddress extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_address );
        toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Edit Address" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        setEditText();

    }

    private void setEditText() {
        EditText name,address,mobile,pincode,landMark,city;
        CheckBox primary_address;
        Spinner state;

        name=(EditText)findViewById( R.id.edit_name1 ) ;
        address=(EditText)findViewById( R.id.edit_address1 ) ;
        mobile=(EditText)findViewById( R.id.edit_mobile1 ) ;
        pincode=(EditText)findViewById( R.id.edit_pincode1 ) ;
        landMark=(EditText)findViewById( R.id.edit_land_mark1 ) ;
        city=(EditText)findViewById( R.id.edit_city1 ) ;

        primary_address=(CheckBox)findViewById( R.id.checkBox );

       state=(Spinner)findViewById( R.id.spinner ) ;

      if (getIntent().hasExtra( "name" ))
      {
          if (getIntent()!=null)
          {
              name.setText( getIntent().getStringExtra( "name" ) );
              address.setText( getIntent().getStringExtra( "address" ) );
              mobile.setText( getIntent().getStringExtra( "mobile" ) );
              city.setText( "Lucknow" );
              pincode.setText( "226017" );
              landMark.setText( "Indra Nagar" );
              String isPrimary=getIntent().getStringExtra( "isPrimary" );
              primary_address.setChecked( isPrimary.equals( "true" )?true:false );

          }
      }

       else {
           primary_address.setChecked( false );
           getSupportActionBar().setTitle( "Add New Address" );
       }
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
                startActivity( new Intent( EditAddress.this, Shopping_Cart.class ) );
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

}
