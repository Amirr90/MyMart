package com.e.vibhacart.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class MyViewePagerAdapter extends FragmentPagerAdapter {
    public MyViewePagerAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            /*case 0:
                return FragmentBooking_one.getInstance();
            case 1:
                return fragment_fragment_booking_two.getInstance();
            case 2:
                return Fragment_booking_three.getInstance();
            case 3:
                return Fragment_booking_four.getInstance();*/
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}