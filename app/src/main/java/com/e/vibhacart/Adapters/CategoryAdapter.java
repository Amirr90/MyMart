package com.e.vibhacart.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.vibhacart.Modals.CategoryModal;
import com.e.vibhacart.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyCategoryholder> {
    List<CategoryModal> categoryModals;

    public CategoryAdapter(List<CategoryModal> categoryModals) {
        this.categoryModals = categoryModals;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyCategoryholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.category_layout, viewGroup, false );
        return new MyCategoryholder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyCategoryholder myCategoryholder, int i) {

        try {
            myCategoryholder.cat_name.setText( categoryModals.get( i ).getCategoryName() );
            String image_url = categoryModals.get( i ).getCategoryIconLink();
            Picasso.get().load( image_url ).into( myCategoryholder.cat_Icon, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {

                }
            } );
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public int getItemCount() {
        return categoryModals.size();
    }

    public class MyCategoryholder extends RecyclerView.ViewHolder {
        ImageView cat_Icon;
        TextView cat_name;

        public MyCategoryholder(@NonNull View itemView) {
            super( itemView );
            cat_Icon = (ImageView) itemView.findViewById( R.id.catt_icon );
            cat_name = (TextView) itemView.findViewById( R.id.catt_name );

        }
    }
}






