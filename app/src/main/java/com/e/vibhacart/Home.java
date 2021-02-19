package com.e.vibhacart;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.vibhacart.Fragments.Account;
import com.e.vibhacart.Fragments.Categories;
import com.e.vibhacart.Fragments.HomeFragment;
import com.e.vibhacart.Fragments.Shortlist;
import com.e.vibhacart.Modals.Product;
import com.e.vibhacart.Modals.Users;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import dmax.dialog.SpotsDialog;

public class Home extends AppCompatActivity {

    //Fire2
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    AlertDialog dialog;
    CollectionReference userRef;
    BottomSheetDialog bottomSheetDialog;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );

        bottomNavigationView=(BottomNavigationView)findViewById( R.id.bottom_navigation );
        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        userRef= FirebaseFirestore.getInstance().collection( "Users" );
        dialog=new SpotsDialog.Builder().setContext( this ).build();

        checkExistingUser( currentUser );

       // setProducts(true);

        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment=null;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.home)
                {
                    fragment=new HomeFragment();
                }
                else if (menuItem.getItemId()==R.id.shortlist)
                {
                    fragment=new Shortlist();
                }

                else if (menuItem.getItemId()==R.id.account)
                {
                    fragment=new Account();
                }
                else if (menuItem.getItemId()==R.id.category)
                {
                    fragment=new Categories();
                }
                return loadFragment(fragment);
            }
        } );

        bottomNavigationView.setSelectedItemId( R.id.home );




    }



    private boolean loadFragment(Fragment fragment) {
        if (fragment!=null)
        {
            getSupportFragmentManager().beginTransaction().replace( R.id.fragment_container,fragment )
                    .commit();
            return true;
        }
        return false;
    }


    private void checkExistingUser(final FirebaseUser currentUser) {
        if (currentUser!=null)
        {
            dialog.show();
            DocumentReference documentReference=userRef.document( currentUser.getPhoneNumber() );
            documentReference.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        dialog.dismiss();
                        DocumentSnapshot userSnapShot=task.getResult();
                        if (!userSnapShot.exists())
                        {
                            showUpdateDialog(currentUser.getPhoneNumber());
                        }
                    }
                }
            } );
        }

    }

    private void showUpdateDialog(final String phoneNumber) {

        if (dialog.isShowing())
        {
            dialog.dismiss();
        }
        bottomSheetDialog=new BottomSheetDialog( this );
        bottomSheetDialog.setCanceledOnTouchOutside( false );
        bottomSheetDialog.setTitle( "One more step!" );
        bottomSheetDialog.setCancelable( false );
        View view=getLayoutInflater().inflate( R.layout.layout_update_information,null );

        Button update=(Button)view.findViewById( R.id.btn_update );
        final TextInputEditText name=(TextInputEditText)view.findViewById( R.id.edit_name ) ;
        final TextInputEditText address=(TextInputEditText)view.findViewById( R.id.edit_address ) ;
        update.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                Users users=new Users( name.getText().toString(),
                        address.getText().toString(),
                        phoneNumber,"default");

                userRef.document( phoneNumber )
                        .set( users )
                        .addOnSuccessListener( new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dialog.dismiss();
                                bottomSheetDialog.dismiss();
                                Toast.makeText( Home.this, "Thank You", Toast.LENGTH_SHORT ).show();
                            }
                        } ).addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        bottomSheetDialog.dismiss();
                        Toast.makeText( Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                } );
            }
        } );

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_cart) {
            startActivity( new Intent( Home.this,Shopping_Cart.class ) );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_order) {
            // Handle the camera action
        } else if (id == R.id.my_account) {

        } else if (id == R.id.my_cart) {

        } else if (id == R.id.my_wishlist) {

        } else if (id == R.id.my_reward) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }*/
}
