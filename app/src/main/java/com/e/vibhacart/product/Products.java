package com.e.vibhacart.product;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.e.vibhacart.AppUtils.ProductInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.e.vibhacart.AppUtils.AppUtil.getMobileNumber;

public class Products {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public void addProduct(Products products, final ProductInterface productInterface) {
        firebaseFirestore.collection("Users").document(getMobileNumber()).collection("My_Cart").add(products).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                productInterface.onSuccess("Product Added successfully !!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                productInterface.onSuccess("error while adding product !!");
            }
        });
    }

    public void updateProductQuantity(String pId, String pQty, final ProductInterface productInterface) {
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("quantity", pQty);
        firebaseFirestore.collection("Users").document(getMobileNumber()).collection("My_Cart")
                .document(pId).update(productMap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                productInterface.onSuccess("error while adding product !!");
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                productInterface.onSuccess("Product Updated successfully !!");
            }
        });
    }

    public void deleteProduct(String pId, final ProductInterface productInterface) {
        firebaseFirestore.collection("Users")
                .document(getMobileNumber())
                .collection("My_Cart")
                .document(pId)
                .delete().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                productInterface.onSuccess("error while deleting  product !!");
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                productInterface.onSuccess("Product deleted successfully !!");
            }
        });
    }


}
