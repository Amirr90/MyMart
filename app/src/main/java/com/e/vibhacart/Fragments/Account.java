package com.e.vibhacart.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.e.vibhacart.Notifications;
import com.e.vibhacart.R;
import com.e.vibhacart.VibaCash;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {


    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate( R.layout.fragment_account, container, false );

       android.support.v7.widget.Toolbar toolbar=(Toolbar)view.findViewById( R.id.tool_bar_Account ) ;
        CollapsingToolbarLayout appBarLayout=(CollapsingToolbarLayout) view.findViewById( R.id.colap2) ;


        RelativeLayout AccountWallet=(RelativeLayout)view.findViewById( R.id.acc_wallet ) ;
        AccountWallet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( getActivity(), VibaCash.class );
                intent.putExtra( "value","AccountWallet" );
                startActivity( intent );
            }
        } );


        RelativeLayout AccountMyOrder=(RelativeLayout)view.findViewById( R.id.account_my_order ) ;
        AccountMyOrder.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( getActivity(), VibaCash.class );
                intent.putExtra( "value","AccountMyOrder" );
                startActivity( intent );
            }
        } );


        LinearLayout AccountEdit=(LinearLayout) toolbar.findViewById( R.id.edit_layout ) ;
        AccountEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( getActivity(), VibaCash.class );
                intent.putExtra( "value","AccountEdit" );
                startActivity( intent );
            }
        } );

        RelativeLayout AccountNotification=(RelativeLayout) view.findViewById( R.id.acc_notification ) ;
        AccountNotification.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( getActivity(), Notifications.class );
                intent.putExtra( "value","AccountNotification" );
                startActivity( intent );
            }
        } );

        RelativeLayout ShareAndEarn=(RelativeLayout) view.findViewById( R.id.shareandearn ) ;
        ShareAndEarn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( getActivity(), VibaCash.class );
                intent.putExtra( "value","ShareAndEarn" );
                startActivity( intent );
            }
        } );
        return view;
    }

}
