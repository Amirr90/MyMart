package com.e.vibhacart.AppUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AppUtil {

    public  static String getMobileNumber() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (null != currentUser) {
            return currentUser.getPhoneNumber();
        } else return "";
    }
}
