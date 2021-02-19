package com.e.vibhacart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        if (currentUser==null)
        {
            updateUI( currentUser );
            return;
        }
    }
    private void updateUI(FirebaseUser currentUser) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser==null)
        {

            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.PhoneBuilder().build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTosAndPrivacyPolicyUrls(
                                    "https://example.com/terms.html",
                                    "https://example.com/privacy.html")
                            .setLogo(R.drawable.ic_launcher_background)      // Set logo drawable
                            .setTheme(R.style.AppThemeNo)
                            .build(),
                    10);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null)
                {
                    // saveDataToFireBase( user.getUid(),user.getPhoneNumber() );
                    Toast.makeText( this, "Sign in Successfully "+user.getPhoneNumber(), Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent( LoginActivity.this,Home.class ) );
                    finish();
                }

            } else {

                Toast.makeText( this, "Sign in failed", Toast.LENGTH_SHORT ).show();
                updateUI( currentUser );
            }
        }
    }

}
