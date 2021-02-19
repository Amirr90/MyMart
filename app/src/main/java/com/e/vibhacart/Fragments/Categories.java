package com.e.vibhacart.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.vibhacart.CategoryDetail;
import com.e.vibhacart.Modals.ModelForSubCategory;
import com.e.vibhacart.Modals.SubCategoryModel;
import com.e.vibhacart.Modals.SubCategoryModel2;
import com.e.vibhacart.R;
import com.e.vibhacart.RInterface.RetroInterface;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */

public class Categories extends Fragment {

    RecyclerView recyclerView;
    private List<SubCategoryModel2> categoryList = new ArrayList<>();
    private List<List<ModelForSubCategory>> sub_categoryList2 = new ArrayList<>();
    HomeAdapter CategoryAdapter;
    ProgressBar progressBar;
    LinearLayout retryLayout;
    Button retry;

    public Categories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_categories, container, false );
        recyclerView = (RecyclerView) view.findViewById( R.id.main_cate_rec );


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity(), LinearLayoutManager.VERTICAL, false );
        retry=(Button)view.findViewById( R.id.rertry ) ;
        retryLayout=(LinearLayout)view.findViewById( R.id.retry_layout ) ;
        progressBar=(ProgressBar)view.findViewById( R.id.progressBar5 ) ;


        recyclerView.setHasFixedSize( false );
        recyclerView.setNestedScrollingEnabled( false );
        recyclerView.setLayoutManager( layoutManager );


        CategoryAdapter = new HomeAdapter( sub_categoryList2, categoryList, getActivity() );
        recyclerView.setAdapter( CategoryAdapter );


        loadData(  );


        retry.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
                progressBar.setVisibility( View.VISIBLE );
                recyclerView.setVisibility( View.GONE );
                retryLayout.setVisibility( View.GONE );

            }
        } );

        return view;

    }

    private void loadData() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "https://vibamart.in/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        RetroInterface uploadInterFace = retrofit.create( RetroInterface.class );

        Call<SubCategoryModel> call = uploadInterFace.getCategoryData();
        call.enqueue( new Callback<SubCategoryModel>() {
            @Override
            public void onResponse(Call<SubCategoryModel> call, Response<SubCategoryModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText( getActivity(), "error", Toast.LENGTH_SHORT ).show();
                    progressBar.setVisibility( View.GONE );
                    recyclerView.setVisibility( View.GONE );
                    retryLayout.setVisibility( View.VISIBLE );
                    return;
                }
                SubCategoryModel subCategoryModel = response.body();
                //Adding category Data
                for (int a = 0; a < subCategoryModel.getData().size(); a++) {
                    categoryList.add( new SubCategoryModel2(
                            subCategoryModel.getData().get( a ).getCategory_name(),
                            subCategoryModel.getData().get( a ).getCat_id(),
                            subCategoryModel.getData().get( a ).getCategory_name(),
                            subCategoryModel.getData().get( a ).getCategory_status() ) );

                    //Adding Sub CategoryList
                    List<ModelForSubCategory> sub_categoryList = new ArrayList<>();
                    for (int b = 0; b < subCategoryModel.getData().get( a ).getSubcat().size(); b++) {

                        sub_categoryList.add( new ModelForSubCategory(
                                subCategoryModel.getData().get( a ).getSubcat().get( b ).getSubcat_id(),
                                subCategoryModel.getData().get( a ).getSubcat().get( b ).getSubcat_desc(),
                                subCategoryModel.getData().get( a ).getSubcat().get( b ).getCreated_at(),
                                subCategoryModel.getData().get( a ).getSubcat().get( b ).getCategory_id(),
                                subCategoryModel.getData().get( a ).getSubcat().get( b ).getSubcat_status(),
                                subCategoryModel.getData().get( a ).getSubcat().get( b ).getSubcat_name()
                        ) );
                        if (b == subCategoryModel.getData().get( a ).getSubcat().size() - 1) {
                            sub_categoryList2.add( sub_categoryList );
                        }
                    }

                }


                CategoryAdapter.notifyDataSetChanged();
                progressBar.setVisibility( View.GONE );
                recyclerView.setVisibility( View.VISIBLE );
                retryLayout.setVisibility( View.GONE );
            }

            @Override
            public void onFailure(Call<SubCategoryModel> call, Throwable t) {
                Toast.makeText( getActivity(), "failed", Toast.LENGTH_SHORT ).show();
                progressBar.setVisibility( View.GONE );
                recyclerView.setVisibility( View.GONE );
                retryLayout.setVisibility( View.VISIBLE );
            }

        } );

    }


    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        private List<List<ModelForSubCategory>> sub_categoryList2;
        private List<SubCategoryModel2> categoryList;
        Context context;


        public HomeAdapter(List<List<ModelForSubCategory>> sub_categoryList2, List<SubCategoryModel2> categoryList, Context context) {
            this.sub_categoryList2 = sub_categoryList2;
            this.categoryList = categoryList;
            this.context = context;
        }

        @NonNull
        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.row_layout, viewGroup, false );
            return new HomeAdapter.MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder myViewHolder, int i) {

            myViewHolder.title.setText( categoryList.get( i ).getCategory_name() );
            try {
                GridAdapter gridAdapter = new GridAdapter( sub_categoryList2.get( i ), context );
                myViewHolder.GridRecView.setAdapter( gridAdapter );
            } catch (Exception e) {
            }


        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            RecyclerView GridRecView;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                title = (TextView) itemView.findViewById( R.id.text_fashion );
                GridRecView = (RecyclerView) itemView.findViewById( R.id.fashion_rec1 );
                GridRecView.setHasFixedSize( false );
                GridRecView.setNestedScrollingEnabled( false );
                GridRecView.setLayoutManager( new GridLayoutManager( getActivity(), 3 ) );

            }
        }

        private class GridAdapter extends RecyclerView.Adapter<HomeAdapter.GridAdapter.MyViewHolder> {
            List<ModelForSubCategory> mobileModals;
            Context context;

            public GridAdapter(List<ModelForSubCategory> mobileModals, Context context) {
                this.mobileModals = mobileModals;
                this.context = context;
            }

            @NonNull
            @Override
            public HomeAdapter.GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.fashion_lay, viewGroup, false );
                return new HomeAdapter.GridAdapter.MyViewHolder( view );
            }

            @Override
            public void onBindViewHolder(@NonNull HomeAdapter.GridAdapter.MyViewHolder myViewHolder, final int i) {
                //myViewHolder.circleImageView.setImageResource( mobileModals.get( i ).getImage() );
                myViewHolder.title.setText( mobileModals.get( i ).getSubcat_name() );
                myViewHolder.layout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText( context, "catId: "+mobileModals.get( i ).getCategory_id()+" subCatId: "+mobileModals.get( i ).getSubcat_id(), Toast.LENGTH_SHORT ).show();
                        startActivity( new Intent( getActivity(), CategoryDetail.class ) );
                    }
                } );
            }

            @Override
            public int getItemCount() {
                return mobileModals.size();
            }

            public class MyViewHolder extends RecyclerView.ViewHolder {
                private TextView title;
                CircleImageView circleImageView;
                LinearLayout layout;

                public MyViewHolder(@NonNull View itemView) {
                    super( itemView );
                    circleImageView = (CircleImageView) itemView.findViewById( R.id.image );
                    title = (TextView) itemView.findViewById( R.id.title_fashion );
                    layout = (LinearLayout) itemView.findViewById( R.id.linear );
                }
            }
        }
    }

}
