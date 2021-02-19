package com.e.vibhacart.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e.vibhacart.Modals.TopHeaderModal;
import com.e.vibhacart.R;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class TopHeaderAdapter  extends RecyclerView.Adapter<TopHeaderAdapter.MyHolder> {
    List<TopHeaderModal> headerModals;

    public TopHeaderAdapter(List<TopHeaderModal> headerModals) {
        this.headerModals = headerModals;
    }

    @NonNull
    @Override
    public TopHeaderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.tool_item, viewGroup, false );
        return new MyHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull TopHeaderAdapter.MyHolder myHolder, int i) {

        try {
            myHolder.name.setText( headerModals.get( i ). getName());
            switch (i)
            {
                case 0:
                {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(40,5,8,5);
                    myHolder.cardView.setLayoutParams(params);
                    myHolder.name.setTextColor( Color.BLACK );
                    myHolder.layout.setBackgroundResource( R.drawable.transparent_gradient );
                    break;
                }
                case 1:
                {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8,5,8,5);
                    myHolder.cardView.setLayoutParams(params);
                    myHolder.name.setTextColor( Color.WHITE );
                    myHolder.layout.setBackgroundResource( R.drawable.transparent_gradient2);
                    break;
                }
                case 2:
                    {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(8,5,8,5);
                        myHolder.cardView.setLayoutParams(params);
                        myHolder.name.setTextColor( Color.WHITE );
                        myHolder.name.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_subway_black_24dp, 0, 0, 0);
                        myHolder.layout.setBackgroundResource( R.drawable.transparent_gradient3 );
                        break;
                    }
                default:
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8,5,8,5);
                    myHolder.cardView.setLayoutParams(params);
                    myHolder.name.setTextColor( Color.WHITE );
                    myHolder.layout.setBackgroundResource( R.drawable.transparent_gradient_def );

            }
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public int getItemCount() {
        return headerModals.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
       private TextView name;
       LinearLayout layout;
       CardView cardView;
        public MyHolder(@NonNull View itemView) {
            super( itemView );
            name=(TextView)itemView.findViewById( R.id.toot_text );
            layout=(LinearLayout)itemView.findViewById( R.id.TopproductContent );
            cardView=(CardView)itemView.findViewById( R.id.card_lay );
        }
    }
}
