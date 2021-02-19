package com.e.vibhacart;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.e.vibhacart.Modals.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    RecyclerView recyclerView;
    RelativeLayout No_Notification_lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_notifications );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "Notification" );
        recyclerView=(RecyclerView)findViewById( R.id.notification_rec );
        No_Notification_lay=(RelativeLayout)findViewById( R.id.no_notification_lay ) ;

        setNotofication();
    }

    private void setNotofication() {
        List<NotificationModel>notificationModels=new ArrayList<>();
        notificationModels.add( new NotificationModel("Budget period","hi, Use Code SD30 for Extra Savings on Grocery Prodect",R.drawable.banner) );
        NotificationAdapter adapter=new NotificationAdapter(notificationModels);
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setAdapter(adapter);

        if (notificationModels.size()==0)
        {
            recyclerView.setVisibility( View.GONE );
            No_Notification_lay.setVisibility( View.VISIBLE );

        }
        else {
            recyclerView.setVisibility( View.VISIBLE );
            No_Notification_lay.setVisibility( View.GONE );
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
                startActivity( new Intent( Notifications.this, Shopping_Cart.class ) );
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

    private class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.MyViewholder>{
        List<NotificationModel> notificationModels;

        public NotificationAdapter(List<NotificationModel> notificationModels) {
            this.notificationModels = notificationModels;
        }

        @NonNull
        @Override
        public NotificationAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_view, viewGroup, false);
            return  new MyViewholder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationAdapter.MyViewholder myViewholder, int i) {

            try {
                myViewholder.Notification_image.setImageResource( notificationModels.get( i ).getImage() );
                myViewholder.title.setText( notificationModels.get( i ).getTitle2() );
            }
            catch (Exception e)
            {

            }
        }

        @Override
        public int getItemCount() {
            return notificationModels.size();
        }

        public class MyViewholder extends RecyclerView.ViewHolder {
            private ImageView Notification_image;
            private TextView title;
            public MyViewholder(@NonNull View itemView) {
                super( itemView );

                Notification_image=(ImageView)itemView.findViewById( R.id.notification_image );
                title=(TextView)itemView.findViewById( R.id.textView54 );
            }
        }
    }
}
