package com.e.vibhacart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.vibhacart.Modals.MobileModal;
import com.e.vibhacart.Modals.MobileModal2;
import com.e.vibhacart.Modals.TopHeaderModal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetail extends AppCompatActivity {

    RecyclerView recyclerView, recyclerView2, recyclerView3;
    ImageView banner1, banner2;
    RecyclerView StripRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category_detail2 );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setToobar( toolbar );
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        setBanners();

        setStripRecycler();

        //setting recyclerview1
        recyclerView = (RecyclerView) findViewById( R.id.grid_rec3 );
        recyclerView.setLayoutManager( new GridLayoutManager( this, 3 ) );
        List<MobileModal2> mobileModals = new ArrayList<>();


        //setting recyclerView2
        recyclerView2 = (RecyclerView) findViewById( R.id.grid_rec4 );

        recyclerView2.setHasFixedSize( false );
        recyclerView2.setNestedScrollingEnabled( false );
        recyclerView2.setLayoutManager( new GridLayoutManager( this, 2 ) );
        List<MobileModal2> mobileModals2 = new ArrayList<>();

        //setting recyclerView3
        recyclerView3 = (RecyclerView) findViewById( R.id.grid_rec5 );

        recyclerView3.setHasFixedSize( false );
        recyclerView3.setNestedScrollingEnabled( false );
        recyclerView3.setLayoutManager( new GridLayoutManager( this, 2 ) );
        List<MobileModal2> mobileModals3 = new ArrayList<>();
        addData( mobileModals, recyclerView );
        addData2( mobileModals2, recyclerView2 );
        addData3( mobileModals3, recyclerView3 );


    }

    private void setStripRecycler() {
        StripRec=(RecyclerView)findViewById( R.id.strip_recycler ) ;
        StripRec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        StripRec.setNestedScrollingEnabled( false );


        CollectionReference todaydealref = FirebaseFirestore.getInstance()
                .collection( "banner" )
                .document( "banner_inside" )
                .collection( "strip_banner" );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<MobileModal2> mobileModals=new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                MobileModal2 modal = bannerSnapshot.toObject( MobileModal2.class );
                                mobileModals.add( modal );
                            }
                            StripRec.setAdapter( new StripAdapter(mobileModals) );
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );


    }

    private void setBanners() {
        banner1 = (ImageView) findViewById( R.id.banner_one );
        banner2 = (ImageView) findViewById( R.id.banner_two );
//Adding Banner1
        CollectionReference todaydealref = FirebaseFirestore.getInstance()
                .collection( "banner" )
                .document( "banner_inside" )
                .collection( "banner_one" );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<MobileModal2> mobileModals = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                MobileModal2 modal = bannerSnapshot.toObject( MobileModal2.class );
                                mobileModals.add( modal );
                            }
                            /*categoryAdapter2.notifyDataSetChanged();*/
                            try {
                                Picasso.get().load( mobileModals.get( 0 ).getImage() ).into( banner1 );
                            } catch (Exception e) {

                            }
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );
//Adding Banner 2
        CollectionReference bannerRef = FirebaseFirestore.getInstance()
                .collection( "banner" )
                .document( "banner_inside" )
                .collection( "banner_two" );
        bannerRef.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<MobileModal2> mobileModals = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                MobileModal2 modal = bannerSnapshot.toObject( MobileModal2.class );
                                mobileModals.add( modal );
                            }
                            /*categoryAdapter2.notifyDataSetChanged();*/
                            try {
                                Picasso.get().load( mobileModals.get( 0 ).getImage() ).into( banner2 );
                            } catch (Exception e) {

                            }
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );

    }

    private void addData(final List<MobileModal2> mobileModals, final RecyclerView recyclerView) {
        //Adding Data To List
        CollectionReference todaydealref = FirebaseFirestore.getInstance()
                .collection( "Fashion" )
                .document( "men" )
                .collection( "everyday" );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                MobileModal2 modal = bannerSnapshot.toObject( MobileModal2.class );
                                mobileModals.add( modal );
                            }
                            /*categoryAdapter2.notifyDataSetChanged();*/

                            recyclerView.setAdapter( new CategoryGridAdapter( mobileModals ) );
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );


    }

    private void addData2(final List<MobileModal2> mobileModals, final RecyclerView recyclerView) {
        //Adding Data To List
        CollectionReference todaydealref = FirebaseFirestore.getInstance()
                .collection( "Fashion" )
                .document( "men" )
                .collection( "pickyourstyle" );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                MobileModal2 modal = bannerSnapshot.toObject( MobileModal2.class );
                                mobileModals.add( modal );
                            }
                            /*categoryAdapter2.notifyDataSetChanged();*/

                            recyclerView2.setAdapter( new CategoryGridAdapter2( mobileModals ) );
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );


    }

    private void addData3(final List<MobileModal2> mobileModals, final RecyclerView recyclerView) {
        //Adding Data To List
        CollectionReference todaydealref = FirebaseFirestore.getInstance()
                .collection( "Fashion" )
                .document( "men" )
                .collection( "brands_we_love" );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                MobileModal2 modal = bannerSnapshot.toObject( MobileModal2.class );
                                mobileModals.add( modal );
                            }
                            /*categoryAdapter2.notifyDataSetChanged();*/

                            recyclerView3.setAdapter( new CategoryGridAdapter3( mobileModals ) );
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );


    }


    private void setToobar(Toolbar toolbar) {
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setTitle( "Men's " );
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.MyViewHolder> {
        List<MobileModal2> mobileModals;

        public CategoryGridAdapter(List<MobileModal2> mobileModals) {
            this.mobileModals = mobileModals;
        }

        @NonNull
        @Override
        public CategoryGridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.rec_lay_item, viewGroup, false );
            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryGridAdapter.MyViewHolder myViewHolder, int i) {
            try {
                Picasso.get().load( mobileModals.get( i ).getImage() ).into( myViewHolder.imageView );
                myViewHolder.textView.setText( mobileModals.get( i ).getTitle() );
            } catch (Exception e) {

            }
        }

        @Override
        public int getItemCount() {
            return mobileModals.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                imageView = (ImageView) itemView.findViewById( R.id.ima_view );
                textView = (TextView) itemView.findViewById( R.id.text23 );
            }
        }
    }

    private class CategoryGridAdapter2 extends RecyclerView.Adapter<CategoryGridAdapter2.MyViewHolder> {
        List<MobileModal2> mobileModals;

        public CategoryGridAdapter2(List<MobileModal2> mobileModals) {
            this.mobileModals = mobileModals;
        }

        @NonNull
        @Override
        public CategoryGridAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.rec_lay_item2, viewGroup, false );
            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryGridAdapter2.MyViewHolder myViewHolder, int i) {
            try {
                Picasso.get().load( mobileModals.get( i ).getImage() ).into( myViewHolder.imageView );
                myViewHolder.textView.setText( mobileModals.get( i ).getTitle() );
            } catch (Exception e) {

            }


        }

        @Override
        public int getItemCount() {
            return mobileModals.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView, textView2;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                imageView = (ImageView) itemView.findViewById( R.id.ima_view2 );
                textView = (TextView) itemView.findViewById( R.id.text24 );
                textView2 = (TextView) itemView.findViewById( R.id.text25 );
            }
        }
    }

    private class CategoryGridAdapter3 extends RecyclerView.Adapter<CategoryGridAdapter3.MyViewHolder> {
        List<MobileModal2> mobileModals;

        public CategoryGridAdapter3(List<MobileModal2> mobileModals) {
            this.mobileModals = mobileModals;
        }

        @NonNull
        @Override
        public CategoryGridAdapter3.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.rec_lay_item3, viewGroup, false );
            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryGridAdapter3.MyViewHolder myViewHolder, int i) {
            try {
                Picasso.get().load( mobileModals.get( i ).getImage() ).into( myViewHolder.imageView );
            } catch (Exception e) {

            }

        }

        @Override
        public int getItemCount() {
            return mobileModals.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                imageView = (ImageView) itemView.findViewById( R.id.ima_view3 );
            }
        }
    }

    private class StripAdapter extends RecyclerView.Adapter<StripAdapter.MyViewHolder> {

        List<MobileModal2> mobileModal;

        public StripAdapter(List<MobileModal2> mobileModal) {
            this.mobileModal = mobileModal;
        }

        @NonNull
        @Override
        public StripAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
           View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_layout, viewGroup, false);
           return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull StripAdapter.MyViewHolder myViewHolder, int i) {
            try {
                Picasso.get().load( mobileModal.get( i ).getImage() ).into( myViewHolder.imageView );
            }
            catch (Exception e)
            {

            }

        }

        @Override
        public int getItemCount() {
            return mobileModal.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
           private ImageView imageView;
            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                imageView=(ImageView)itemView.findViewById( R.id.strip_image ) ;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cart:
               startActivity( new Intent( this,Shopping_Cart.class ) );
              finish();
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }
}
