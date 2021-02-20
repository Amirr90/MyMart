package com.e.vibhacart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.vibhacart.AppUtils.AddressUtils;
import com.e.vibhacart.AppUtils.AppInterface;
import com.e.vibhacart.Modals.Address;

import java.util.HashMap;
import java.util.Map;

public class EditAddress extends AppCompatActivity {

    Toolbar toolbar;
    EditText name, address, mobile, pinCode, landMark, city;
    CheckBox primary_address;
    Spinner state;

    Button btnUpdateAddress;

    String addId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setEditText();


    }

    @Override
    protected void onStart() {
        super.onStart();
        btnUpdateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Address add = new Address();
                add.setAddress(address.getText().toString());
                add.setName(name.getText().toString());
                add.setPinCode(pinCode.getText().toString());
                add.setCity(city.getText().toString());
                add.setLandMark(landMark.getText().toString());
                add.setMobile(mobile.getText().toString());
                add.setAddId(String.valueOf(System.currentTimeMillis()));
                add.setPrimaryAddress(primary_address.isChecked());

                AddressUtils addressUtils = new AddressUtils();
                if (btnUpdateAddress.getTag().equals("0"))
                    addressUtils.addNewAddress(add, new AppInterface() {
                        @Override
                        public void onSuccess(String msg) {
                            Toast.makeText(EditAddress.this, msg, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(String msg) {
                            Toast.makeText(EditAddress.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                else {
                    Map<String, Object> addressMap = new HashMap<>();
                    addressMap.put("name", add.getName());
                    addressMap.put("address", add.getAddress());
                    addressMap.put("pinCode", add.getPinCode());
                    addressMap.put("city", add.getCity());
                    addressMap.put("landMark", add.getLandMark());
                    addressMap.put("mobile", add.getMobile());
                    addressMap.put("primaryAddress", add.isPrimaryAddress());
                    addressUtils.updateAddress(addId, addressMap, new AppInterface() {
                        @Override
                        public void onSuccess(String msg) {
                            Toast.makeText(EditAddress.this, msg, Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }

                        @Override
                        public void onFailed(String msg) {
                            Toast.makeText(EditAddress.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void setEditText() {


        btnUpdateAddress = (Button) findViewById(R.id.btnUpdateAddress);
        name = (EditText) findViewById(R.id.edit_name1);
        address = (EditText) findViewById(R.id.edit_address1);
        mobile = (EditText) findViewById(R.id.edit_mobile1);
        pinCode = (EditText) findViewById(R.id.edit_pincode1);
        landMark = (EditText) findViewById(R.id.edit_land_mark1);
        city = (EditText) findViewById(R.id.edit_city1);

        primary_address = (CheckBox) findViewById(R.id.checkBox);

        state = (Spinner) findViewById(R.id.spinner);

        if (getIntent().hasExtra("name")) {
            addId = getIntent().getStringExtra("addId");
            btnUpdateAddress.setTag("1");
            if (getIntent() != null) {
                name.setText(getIntent().getStringExtra("name"));
                address.setText(getIntent().getStringExtra("address"));
                mobile.setText(getIntent().getStringExtra("mobile"));
                city.setText("Lucknow");
                pinCode.setText("226017");
                landMark.setText("Indra Nagar");
                String isPrimary = getIntent().getStringExtra("isPrimary");
                primary_address.setChecked(isPrimary.equals("true") ? true : false);

            }
        } else {
            btnUpdateAddress.setTag("0");
            primary_address.setChecked(false);
            getSupportActionBar().setTitle("Add New Address");
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
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                startActivity(new Intent(EditAddress.this, Shopping_Cart.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

}
