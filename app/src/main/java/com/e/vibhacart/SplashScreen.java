package com.e.vibhacart;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashScreen extends AwesomeSplash {
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    public void initSplash(ConfigSplash configSplash) {
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.blue_background); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX( Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.ic_launcher_background); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique( Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
       // configSplash.setPathSplash( Cons ); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorAccent); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(android.R.color.white); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("VibaMart");
        configSplash.setTitleTextColor(android.R.color.white);
        configSplash.setTitleTextSize(50f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);
        configSplash.setTitleFont("fonts/walkway_oblique_bold.ttf"); //provide string to your font located in assets/fonts/
    }

    
    @Override
    public void animationsFinished() {

        mAuth = FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        if (currentUser==null)
        {
            startActivity( new Intent( SplashScreen.this,LoginActivity.class ) );
            finish();
        }
       else {
            startActivity( new Intent( SplashScreen.this,Home.class ) );
            finish();
        }
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

       *//* EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(HomeFragment.class)
                .withSplashTimeOut(4000)
                .withBackgroundResource(R.color.blue_background)
                .withHeaderText("Welcome")
                .withFooterText("Copyright 2019")
                .withBeforeLogoText("VibhaMart")
                .withLogo(R.drawable.ic_launcher_background)
                .withAfterLogoText("Some more details with custom font");
        //add custom font
        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "fonts/playfairdisplay-boldItalic.otf");
        config.getAfterLogoTextView().setTypeface(pacificoFont);
        config.getHeaderTextView().setTypeface(pacificoFont);
        config.getBeforeLogoTextView().setTypeface(pacificoFont);

        //change text color
        config.getHeaderTextView().setTextColor( Color.WHITE);
        config.getBeforeLogoTextView().setTextColor( Color.WHITE);

        //finally create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);*//*

*/

 //  }
}
