package in.aptamitra.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import globalclass.GlobalClass;
import in.aptamitra.R;


public class SplashActivity extends Activity implements AnimationListener {

    ImageView logo;

    // Animation
    Animation animZoomIn;

    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        SharedPreferences preferences = getSharedPreferences("cache", Context.MODE_PRIVATE);
        String profileString = preferences != null ? preferences.getString("profile", null) : null;

        if (profileString != null) {

            progress = new ProgressDialog(this);
            progress.setMessage("please wait..... ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
            super.onCreate(savedInstanceState);
            Intent intent = new Intent(this, LandingPageActivity.class);
            startActivity(intent);
            progress.hide();
            finish();

        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            logo = (ImageView) findViewById(R.id.logo);


            // load the animation
            animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.zoom_in);

            // set animation listener
            animZoomIn.setAnimationListener(this);

            logo.startAnimation(animZoomIn);
        }



//        setContentView(R.layout.activity_splash);
//
//        logo = (ImageView) findViewById(R.id.logo);
//
//        // load the animation
//        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.zoom_in);
//
//        // set animation listener
//        animZoomIn.setAnimationListener(this);
//
//        logo.startAnimation(animZoomIn);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for zoom in animation
        if (animation == animZoomIn) {

            Thread background = new Thread() {
                public void run() {

                    try {
                        // Thread will sleep for 5 seconds
                        sleep(1 * 1000);
                        // After 5 seconds redirect to another intent
                        //Intent i = new Intent(getBaseContext(), LoginActivity.class);
                        Intent i = new Intent(SplashActivity.this, ViewPagerFragmentActivity.class);
                        startActivity(i);
                        finish();
                    } catch (Exception e) {

                    }
                }
            };
            // start thread
            background.start();
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
