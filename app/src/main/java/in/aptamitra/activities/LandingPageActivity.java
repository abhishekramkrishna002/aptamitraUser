package in.aptamitra.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import adapters.LandingPageAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import custom_objects.HorizontalScrollItem;
import globalclass.GlobalClass;
import in.aptamitra.R;
import services.QuickstartPreferences;
import services.RegistrationIntentService;

public class LandingPageActivity extends ActionBarActivity {
    final String TAG = "adapter";


    public static Activity activity;
    @Bind(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.search_bar)
    EditText searchBar;
    @Bind(R.id.app_bar)
    Toolbar toolbar;
    ProgressDialog progress;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    ArrayList<HorizontalScrollItem> items;
    public static Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        activity = this;
        ButterKnife.bind(this);
        initilaiseMenuSystem();



        drawer = ((GlobalClass) getApplicationContext()).navigationDrawer(this);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    progress.hide();
                } else {
                    progress.hide();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            activity);
                    builder1.setMessage("Couldn't register with GCM.Try Again");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(activity, RegistrationIntentService.class);
            activity.startService(intent);

            progress = new ProgressDialog(activity);
            progress.setMessage("connecting to GCM ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }

        //drawer = navigationDrawer(this);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer != null) {
                    drawer.openDrawer();
                }
            }
        });

        searchBar.setClickable(true);

    }

    @OnClick(R.id.search_bar)
    void showSearchPage(View view) {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }
        Intent intent = new Intent(LandingPageActivity.activity, SearchActivity.class);
        startActivity(intent);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }

    public void initilaiseMenuSystem() {
        items = new ArrayList<HorizontalScrollItem>() {{
            add(new HorizontalScrollItem("My City", new ArrayList<HorizontalScrollItem.Item>() {{
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bbmp.jpg", "B B M P"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bwssb.jpg", "B W S S B"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bescom.jpg", "B E S C O M"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bmtc.jpg", "B M T C"));
//                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bda, "B D A"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/ambulance.jpg", "Ambulance"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/blood.jpg", "Blood"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/fire.jpg", "Fire"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/law_and_order.jpg", "Law & Order"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/mortuary.jpg", "Mortuary"));


            }}));
            add(new HorizontalScrollItem("My Booking", new ArrayList<HorizontalScrollItem.Item>() {{

                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/house_keeping.jpg", "House Keeping"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/beautician.jpg", "Beautician"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/laundry.jpg", "Laundry"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/icon_electrician.png", "Electrician"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/icon_plumbing.png", "Plumber"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/icon_carpenter.png", "Carpenter"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/mobile_repair.jpg", "Mobile repair"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/pc_repair.jpg", "PC repair"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/chartered_accountant.jpg", "Accountant"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/company_secretery.jpg", "Secretary"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/trademark_copyright.jpg", "Trademark"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/wedding_phptpgraphy.jpg", "Wedding Photography"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/flute.jpg", "Flute Classes"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/guitar.jpg", "Guitar Classes"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/yoga.jpg", "Yoga Classes "));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/driver.jpg", "Driver"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/appliances.jpg", "Appliances Repair"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/architecture.jpg", "Architecture"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/chef.jpg", "Chef"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/civil_work.jpg", "Civil work"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/dance_classes.jpg", "Dance class"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/driving_school.jpg", "Driving School"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/financial_services.jpg", "Financial Service"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/gardening.jpg", "Gardening"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/leagal_services.jpg", "Legal Service"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/tank_cleaning.jpg", "Tank Cleaning"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/tutor.jpg", "Tutor"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/web_desingner.jpg", "Web Designer"));

            }}));
            add(new HorizontalScrollItem("My Delivery", new ArrayList<HorizontalScrollItem.Item>() {{

                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/icon_food.png", "Food"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/icon_groceries.png", "Groceries"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/icon_fruits.png", "Fruits"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/icon_flowers.png", "Flowers"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bakery.jpg", "Bakery"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bill_payment.jpg", "Bill Payment"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/insta_courier.jpg", "Insta Courier"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/medicine.jpg", "Medicine"));

            }}));

            add(new HorizontalScrollItem("My Aptamitra", new ArrayList<HorizontalScrollItem.Item>() {{
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/gym.jpg", "gym"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/bank.jpg", "bank"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/atm.jpg", "atm"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/music_classes.jpg", "music class"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/textiles.jpg", "textile"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/photo_studio.jpg", "photo studio"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/driving_school.jpg", "driving class"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/medical_shop.jpg", "medical store"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/home_appliances.jpg", "home appliance"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/automobile.jpg", "automobile"));
                add(new HorizontalScrollItem.Item("http://aptamitra.in/service_app/drawable/ayurveda.jpg", "ayurveda"));
            }}));

        }};

        LandingPageAdapter landingPageAdapter = new LandingPageAdapter(this, items);
        recyclerView.setAdapter(landingPageAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("Home tab fargment", "This device is not supported.");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("This device is not supported to connect to GCM");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(activity).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));


    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(activity).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}