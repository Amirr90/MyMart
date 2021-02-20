package com.e.vibhacart.AppUtils;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.e.vibhacart.Modals.Address;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

import static com.e.vibhacart.AppUtils.AppUtil.getMobileNumber;

public class AddressUtils {
    private static final String TAG = "AddressUtils";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference addressRef;
    List<Address> addresses;

    public AddressUtils() {
        addressRef = firebaseFirestore.collection("Users").document(getMobileNumber()).collection("Address");
    }

    public void addNewAddress(Address address, final AppInterface appInterface) {
        addressRef.document(address.getAddId())
                .set(address)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        appInterface.onSuccess("Address Added successfully !!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                appInterface.onSuccess("failed to add address, try again !!");
            }
        });
    }

    public void updateAddress(String addId, Map<String, Object> addMap, final AppInterface appInterface) {
        addressRef.document(addId).update(addMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                appInterface.onSuccess("Address updated successfully !!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                appInterface.onSuccess("failed to update address, try again !!");
            }
        });
    }

    public void initAddress(final ObjectInterface objectInterface) {
        addressRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (null == queryDocumentSnapshots)
                    return;

                objectInterface.onResponse(queryDocumentSnapshots.toObjects(Address.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailureGetAllAddress: " + e.getLocalizedMessage());
            }
        });
    }

    public void getAddressList(ObjectInterface objectInterface) {
        initAddress(objectInterface);
    }

    public Address getPrimaryAddress(ObjectInterface objectInterface) {
        Address address = null;
        if (null == addresses)
            initAddress(objectInterface);
        for (int a = 0; a < addresses.size(); a++) {
            if (addresses.get(a).isPrimaryAddress())
                address = addresses.get(a);
        }
        return address;
    }


    public void deleteAddress(String addId, final ObjectInterface objectInterface) {
        addressRef.document(addId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                objectInterface.onResponse("Address Deleted Successfully !!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                objectInterface.onResponse("unable to delete Address, try again  !!");
            }
        });
    }
}
