package com.e.vibhacart;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.vibhacart.Modals.MobileModal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        recyclerView = (RecyclerView) findViewById( R.id.rv_main );

        List<List<MobileModal>>dataList = new ArrayList<>();
        List<String> strings=new ArrayList<>(  );
        strings.add( "Data1" );
        strings.add( "Data2" );
        strings.add( "Data3" );
        List<MobileModal> mobileModals=new ArrayList<>(  ) ;
        List<MobileModal> mobileModals2=new ArrayList<>(  ) ;
        List<MobileModal> mobileModals3=new ArrayList<>(  ) ;
       for (int i=0;i<5;i++)
       {
           mobileModals.add( new MobileModal(  "Men's",R.drawable.category_men ));
           mobileModals2.add( new MobileModal(  "Women",R.drawable.womens_category ));
           mobileModals3.add( new MobileModal(  "Fitness", R.drawable.fitness ));
       }
       dataList.add(mobileModals);
        dataList.add(mobileModals2);
        dataList.add(mobileModals3);

        // initialize the home adapter passing the list and the context
        HomeAdapter adapter = new HomeAdapter(dataList, this,strings);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false );

        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setAdapter( adapter );


    }

    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        List<List<MobileModal>> lists;
        Context context;
        List<String>string;

        public HomeAdapter(List<List<MobileModal>> lists, Context context, List<String> string) {
            this.lists = lists;
            this.context = context;
            this.string = string;
        }

        @NonNull
        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.row_layout, viewGroup, false );
            return new MyViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder myViewHolder, int i) {

            myViewHolder.title.setText( string.get( i ) );
            GridAdapter gridAdapter=new GridAdapter(lists.get( i ),context);
            myViewHolder.GridRecView.setAdapter(gridAdapter);

        }

        @Override
        public int getItemCount() {
            return string.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            RecyclerView GridRecView;
            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
               /* title=(TextView)itemView.findViewById( R.id.tv_movie_category ) ;
                GridRecView =(RecyclerView)itemView.findViewById( R.id.home_recycler_view_grid ) ;*/
                GridRecView.setLayoutManager( new GridLayoutManager(getApplicationContext(), 3) );

            }
        }

        private class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
            List<MobileModal> mobileModals;
            Context context;

            public GridAdapter(List<MobileModal> mobileModals, Context context) {
                this.mobileModals = mobileModals;
                this.context = context;
            }

            @NonNull
            @Override
            public GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.fashion_lay, viewGroup, false );
                return new MyViewHolder( view );
            }

            @Override
            public void onBindViewHolder(@NonNull GridAdapter.MyViewHolder myViewHolder, int i) {
                myViewHolder.circleImageView.setImageResource( mobileModals.get( i).getImage());
                myViewHolder.title.setText( mobileModals.get( i ).getName() );
            }

            @Override
            public int getItemCount() {
                return mobileModals.size();
            }

            public class MyViewHolder extends RecyclerView.ViewHolder {
                private TextView title;
                CircleImageView circleImageView;
                public MyViewHolder(@NonNull View itemView) {
                    super( itemView );
                    circleImageView=(CircleImageView)itemView.findViewById( R.id.image ) ;
                    title=(TextView)itemView.findViewById( R.id.title_fashion ) ;
                }
            }
        }
    }
}
