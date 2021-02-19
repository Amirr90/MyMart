package com.e.vibhacart.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.e.vibhacart.Adapters.CategoryAdapter;
import com.e.vibhacart.Adapters.HomeSliderAdapter;
import com.e.vibhacart.Adapters.ProductAdapter2;
import com.e.vibhacart.Adapters.ProductsAdapter;
import com.e.vibhacart.Adapters.TopHeaderAdapter;
import com.e.vibhacart.Common.EndlessScrollListener;
import com.e.vibhacart.Common.Space;
import com.e.vibhacart.Interfaces.IBannerLoadiListner;
import com.e.vibhacart.Modals.Banners;
import com.e.vibhacart.Modals.CategoryModal;
import com.e.vibhacart.Modals.Product;
import com.e.vibhacart.Modals.SliderBanner;
import com.e.vibhacart.Modals.TopHeaderModal;
import com.e.vibhacart.R;
import com.e.vibhacart.Search;
import com.e.vibhacart.Service.PicassoImageLoadingservice;
import com.e.vibhacart.Shopping_Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ss.com.bannerslider.Slider;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IBannerLoadiListner {


    RecyclerView Category_rec;

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    CollectionReference bannerRef, category_ref, userRef, striAddref;

    CategoryAdapter categoryAdapter;
    List<CategoryModal> list;
    List<Product> products;
    List<String> products_id;


    Slider banner_slider;
    IBannerLoadiListner iBannerLoadiListner;
    List<Banners> banners;
    //strip Add
    ImageView stripimage;

    // grid view
    ProductsAdapter productsAdapter;
    ProductAdapter2 adapter2;
    RecyclerView recyclerView;


    public HomeFragment() {
        bannerRef = FirebaseFirestore.getInstance().collection( "Todays's_category" );
        category_ref = FirebaseFirestore.getInstance().collection( "Category_banner" );
        userRef = FirebaseFirestore.getInstance().collection( "Users" );
        striAddref = FirebaseFirestore.getInstance().collection( "StrpAdd" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_home, container, false );
        Toolbar toolbar = (Toolbar) view.findViewById( R.id.tool_bar_home );

        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById( R.id.app_bar_layout_home );
        ImageView imageView = (ImageView) toolbar.findViewById( R.id.cart_icon_home );
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getActivity(), Shopping_Cart.class ) );
            }
        } );

        LinearLayout mSearch_layout = (LinearLayout) toolbar.findViewById( R.id.search_bar );
        mSearch_layout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getActivity(), Search.class ) );
            }
        } );


        Slider.init( new PicassoImageLoadingservice() );
        recyclerView = (RecyclerView) view.findViewById( R.id.tool_rec );
        Category_rec = (RecyclerView) view.findViewById( R.id.category_rec );
        banner_slider = (Slider) view.findViewById( R.id.banner_slider2 );
        banners = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        iBannerLoadiListner = this;


        setProducts( false );
        if (currentUser != null) {

            loadTodaydeal();
            loadcategory( Category_rec );
            loadBanner();
            setStripAdd( view );
            loadTodaydeal( view );
            // loadGridView( view );
            loadGridProducts( view );
        }

        return view;
    }


    private void setProducts(boolean checkproduct) {


        List<HashMap> products = new ArrayList<>();
        Random rand = new Random();
        CollectionReference Ref;
        boolean isNew = false;
        HashMap<String, Object> map = new HashMap<>();

        //Ref= FirebaseFirestore.getInstance().collection( "Products" );
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (checkproduct) {
            for (int a = 0; a < 50; a++) {
                if (a % 2 == 0) {
                    isNew = true;
                } else {
                    isNew = false;
                }

                map.put( "imageResourceId", a );
                map.put( "productName", "productName" );
                map.put( "quantity", "1" );
                map.put( "productPrice", "" + rand.nextInt( 1000 ) );
                map.put( "isNew", isNew );
                map.put( "oldproductPrice", "" + rand.nextInt( 2000 ) );
                map.put( "category", "Category" );
                map.put( "image", "image" );
                map.put( "category", "Category" );
                map.put( "sub_category", "Sub_category" );
                map.put( "rating", "" + rand.nextInt( 10 ) );
                map.put( "discount", "" + rand.nextInt( 100 ) );
                map.put( "stock", rand.nextInt( 100 ) );
                DocumentReference reference = db.collection( "Products" ).document();
                reference.set( map );
            }

        }
    }

    private void loadTodaydeal() {
        LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        layoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
        recyclerView.setLayoutManager( layoutManager );
        final List<TopHeaderModal> dealModals = new ArrayList<>();
        final TopHeaderAdapter categoryAdapter2 = new TopHeaderAdapter( dealModals );
        recyclerView.setAdapter( categoryAdapter2 );

        //Adding Data To List
        CollectionReference todaydealref = FirebaseFirestore.getInstance().collection( "Header_Data" );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                TopHeaderModal banners1 = bannerSnapshot.toObject( TopHeaderModal.class );
                                dealModals.add( banners1 );
                            }
                            categoryAdapter2.notifyDataSetChanged();
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );

    }

    private void loadBanner() {
        bannerRef.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                Banners banners1 = bannerSnapshot.toObject( Banners.class );
                                banners.add( banners1 );
                            }
                            iBannerLoadiListner.onBannerLoadSuccess( banners );
                        }

                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBannerLoadiListner.onBannerLoadError( e.getMessage() );
            }
        } );


    }

    private void loadGridProducts(View view) {
        RecyclerView recyclerViewProducts = (RecyclerView) view.findViewById( R.id.recyclerViewProducts );
        recyclerViewProducts.setHasFixedSize( false );
        recyclerViewProducts.setNestedScrollingEnabled( false );
        recyclerViewProducts.setLayoutManager( new GridLayoutManager( getActivity(), 2 ) );
        recyclerViewProducts.addItemDecoration( new Space( 2, 20, true, 0 ) );
        products = new ArrayList<>();
        products_id=new ArrayList<>();
        adapter2 = new ProductAdapter2( products, currentUser, getActivity(),products_id );
        recyclerViewProducts.setAdapter( adapter2 );
        loadProductData( adapter2 );


    }

    private void loadProductData(final ProductAdapter2 adapter2) {

        CollectionReference todaydealref = FirebaseFirestore.getInstance().collection( "Products" );
//        Query first = todaydealref
//                .limit( 4 );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                Product banners1 = bannerSnapshot.toObject( Product.class );
                                products_id.add( bannerSnapshot.getId() );
                                products.add( banners1 );
                            }
                            adapter2.notifyDataSetChanged();
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );

    }

    private void loadGridView(View view) {
        //Bind RecyclerView from layout to recyclerViewProducts object
        RecyclerView recyclerViewProducts = (RecyclerView) view.findViewById( R.id.recyclerViewProducts );

        //Create new ProductsAdapter
        // productsAdapter = new ProductsAdapter( getActivity(), currentUser );
        //adapter2=new ProductAdapter2( products );
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager( getActivity(),
                2,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false );//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager
        recyclerViewProducts.setLayoutManager( gridLayoutManager );

        //Crete new EndlessScrollListener fo endless recyclerview loading
        EndlessScrollListener endlessScrollListener = new EndlessScrollListener( gridLayoutManager ) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (!productsAdapter.loading)
                    feedData();
            }
        };
        //to give loading item full single row
        gridLayoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (productsAdapter.getItemViewType( position )) {
                    case ProductsAdapter.PRODUCT_ITEM:
                        return 1;
                    case ProductsAdapter.LOADING_ITEM:
                        return 2; //number of columns of the grid
                    default:
                        return -1;
                }
            }
        } );
        //add on on Scroll listener
        recyclerViewProducts.addOnScrollListener( endlessScrollListener );
        //add space between cards
        recyclerViewProducts.addItemDecoration( new Space( 2, 20, true, 0 ) );
        //Finally set the adapter
        recyclerViewProducts.setAdapter( productsAdapter );
        //load first page of recyclerview
        endlessScrollListener.onLoadMore( 0, 0 );
    }

    private void feedData() {
        //show loading in recyclerview
        productsAdapter.showLoading();

//        int[] imageUrls = {R.drawable.image_tomto, R.drawable.dalda, R.drawable.green_two, R.drawable.image_two};
//        String[] ProductName = {"Fresh Tomato", "Dalda Mustard", "Philippine Primer", "green Broccoli"};
//        String[] ProductPrice = {"20.0", "79.2", "20.0", "45.0"};
//        String[] OldProductPrice = {"30.0", "90.0", "30.0", "60.0"};
//        boolean[] isNew = {true, false, false, true};
        CollectionReference todaydealref = FirebaseFirestore.getInstance().collection( "Header_Data" );
        Query first = todaydealref
                .limit( 4 );
        todaydealref.limit( 4 ).get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                Product banners1 = bannerSnapshot.toObject( Product.class );
                                products.add( banners1 );
                            }
                            //hide loading
                            productsAdapter.hideLoading();
                            //add products to recyclerview
                            productsAdapter.addProducts( products );
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );
       /* for (int i = 0; i < imageUrls.length; i++) {
            Product product = new Product( imageUrls[i],
                    ProductName[i],
                    ProductPrice[i],
                    isNew[i],
                    OldProductPrice[i],"1");
            products.add( product );
        }*/
       /* new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                //hide loading
                productsAdapter.hideLoading();
                //add products to recyclerview
                productsAdapter.addProducts( products );
            }
        }, 2000 );*/

    }


    private void loadTodaydeal(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById( R.id.deals_rec );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        layoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
        recyclerView.setLayoutManager( layoutManager );
        final List<TodayDealModal> dealModals = new ArrayList<>();
        final TodayDealAdapter categoryAdapter2 = new TodayDealAdapter( dealModals );
        recyclerView.setAdapter( categoryAdapter2 );

        //Adding Data To List
        CollectionReference todaydealref = FirebaseFirestore.getInstance().collection( "Todays's_Deal" );
        todaydealref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                TodayDealModal banners1 = bannerSnapshot.toObject( TodayDealModal.class );
                                dealModals.add( banners1 );
                            }
                            categoryAdapter2.notifyDataSetChanged();
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );

    }

    private void setStripAdd(View view) {

        stripimage = (ImageView) view.findViewById( R.id.strip_banner_slider );

        striAddref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                SliderBanner banners1 = bannerSnapshot.toObject( SliderBanner.class );
                                Picasso.get().load( banners1.getImage() ).placeholder( R.drawable.account_circle_black_24dp ).into( stripimage );
                            }

                        }

                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        } );
    }


    private void loadcategory(RecyclerView category_rec) {
        LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        layoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
        category_rec.setLayoutManager( layoutManager );
        list = new ArrayList<>();
        categoryAdapter = new CategoryAdapter( list );
        category_rec.setAdapter( categoryAdapter );

        //Adding Data To List
        category_ref.get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                                CategoryModal banners1 = bannerSnapshot.toObject( CategoryModal.class );
                                list.add( banners1 );
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }
                    }
                } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );


    }


    @Override
    public void onBannerLoadSuccess(List<Banners> banners) {
        banner_slider.setAdapter( new HomeSliderAdapter( banners ) );
    }

    @Override
    public void onBannerLoadError(String message) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate( R.menu.main, menu );
        super.onCreateOptionsMenu( menu, inflater );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_cart) {
            startActivity( new Intent( getActivity(), Shopping_Cart.class ) );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
    }
}
