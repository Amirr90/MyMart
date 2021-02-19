package com.e.vibhacart.Fragments;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.vibhacart.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

class TodayDealAdapter extends RecyclerView.Adapter<TodayDealAdapter.MyViewHolder> {

    List<TodayDealModal> dealModals;

    public TodayDealAdapter(List<TodayDealModal> dealModals) {
        this.dealModals = dealModals;
    }

    @NonNull
    @Override
    public TodayDealAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.deals_rec_layout, viewGroup, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull TodayDealAdapter.MyViewHolder myCategoryholder, int i) {
        try {
            myCategoryholder.name.setText( dealModals.get( i ).getName() );
            myCategoryholder.offer.setText( dealModals.get( i ).getOffer() );
            myCategoryholder.price.setText( dealModals.get( i ).getPrice() );
            String image_url = dealModals.get( i ).getImage();
            Picasso.get().load( image_url ).into( myCategoryholder.image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                }
            } );

            myCategoryholder.someTextView.setText( dealModals.get( i ).getPrice()); // SomeString = your old price
            myCategoryholder.someTextView.setPaintFlags(myCategoryholder.someTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public int getItemCount() {
        return dealModals.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        private TextView name,offer,price,someTextView;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            image=(ImageView)itemView.findViewById(  R.id.imageView3);
            name=(TextView)itemView.findViewById( R.id.textView7 ) ;
            offer=(TextView)itemView.findViewById( R.id. textView8) ;
            price=(TextView)itemView.findViewById( R.id.textView9 ) ;
            someTextView = (TextView) itemView.findViewById(R.id.old_price);

        }
    }
}
