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
                add(new HorizontalScrollItem.Item(R.drawable.bbmp, "B B M P"));
                add(new HorizontalScrollItem.Item(R.drawable.bwssb, "B W S S B"));
                add(new HorizontalScrollItem.Item(R.drawable.bescom, "B E S C O M"));
                add(new HorizontalScrollItem.Item(R.drawable.bmtc, "B M T C"));
//                add(new HorizontalScrollItem.Item(R.drawable.bda, "B D A"));
                add(new HorizontalScrollItem.Item(R.drawable.ambulance, "Ambulance"));
                add(new HorizontalScrollItem.Item(R.drawable.blood, "Blood"));
                add(new HorizontalScrollItem.Item(R.drawable.fire, "Fire"));
                add(new HorizontalScrollItem.Item(R.drawable.law_and_order, "Law & Order"));
                add(new HorizontalScrollItem.Item(R.drawable.mortuary, "Mortuary"));


            }}));
            add(new HorizontalScrollItem("My Booking", new ArrayList<HorizontalScrollItem.Item>() {{

                add(new HorizontalScrollItem.Item(R.drawable.house_keeping, "House Keeping"));
                add(new HorizontalScrollItem.Item(R.drawable.beautician, "Beautician"));
                add(new HorizontalScrollItem.Item(R.drawable.laundry, "Laundry"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_electrician, "Electrician"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_plumbing, "Plumber"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_carpenter, "Carpenter"));
                add(new HorizontalScrollItem.Item(R.drawable.mobile_repair, "Mobile repair"));
                add(new HorizontalScrollItem.Item(R.drawable.pc_repair, "PC repair"));
                add(new HorizontalScrollItem.Item(R.drawable.chartered_accountant, "Accountant"));
                add(new HorizontalScrollItem.Item(R.drawable.company_secretery, "Secretary"));
                add(new HorizontalScrollItem.Item(R.drawable.trademark_copyright, "Trademark"));
                add(new HorizontalScrollItem.Item(R.drawable.wedding_phptpgraphy, "Wedding Photography"));
                add(new HorizontalScrollItem.Item(R.drawable.flute, "Flute Classes"));
                add(new HorizontalScrollItem.Item(R.drawable.guitar, "Guitar Classes"));
                add(new HorizontalScrollItem.Item(R.drawable.yoga, "Yoga Classes "));
                add(new HorizontalScrollItem.Item(R.drawable.driver, "Driver"));
                add(new HorizontalScrollItem.Item(R.drawable.appliances, "Appliances Repair"));
                add(new HorizontalScrollItem.Item(R.drawable.architecture, "Architecture"));
                add(new HorizontalScrollItem.Item(R.drawable.chef, "Chef"));
                add(new HorizontalScrollItem.Item(R.drawable.civil_work, "Civil work"));
                add(new HorizontalScrollItem.Item(R.drawable.dance_classes, "Dance class"));
                add(new HorizontalScrollItem.Item(R.drawable.driving_school, "Driving School"));
                add(new HorizontalScrollItem.Item(R.drawable.financial_services, "Financial Service"));
                add(new HorizontalScrollItem.Item(R.drawable.gardening, "Gardening"));
                add(new HorizontalScrollItem.Item(R.drawable.leagal_services, "Legal Service"));
                add(new HorizontalScrollItem.Item(R.drawable.tank_cleaning, "Tank Cleaning"));
                add(new HorizontalScrollItem.Item(R.drawable.tutor, "Tutor"));
                add(new HorizontalScrollItem.Item(R.drawable.web_desingner, "Web Designer"));

            }}));
            add(new HorizontalScrollItem("My Delivery", new ArrayList<HorizontalScrollItem.Item>() {{

                add(new HorizontalScrollItem.Item(R.drawable.icon_food, "Food"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_groceries, "Groceries"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_fruits, "Fruits"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_flowers, "Flowers"));
                add(new HorizontalScrollItem.Item(R.drawable.bakery, "Bakery"));
                add(new HorizontalScrollItem.Item(R.drawable.bill_payment, "Bill Payment"));
                add(new HorizontalScrollItem.Item(R.drawable.insta_courier, "Insta Courier"));
                add(new HorizontalScrollItem.Item(R.drawable.medicine, "Medicine"));
            }}));

            add(new HorizontalScrollItem("My Aptamitra", new ArrayList<HorizontalScrollItem.Item>() {{
                add(new HorizontalScrollItem.Item(R.drawable.gym, "gym"));
                add(new HorizontalScrollItem.Item(R.drawable.bank, "bank"));
                add(new HorizontalScrollItem.Item(R.drawable.atm, "atm"));
                add(new HorizontalScrollItem.Item(R.drawable.music_classes, "music class"));
                add(new HorizontalScrollItem.Item(R.drawable.textiles, "textile"));
                add(new HorizontalScrollItem.Item(R.drawable.photo_studio, "photo studio"));
                add(new HorizontalScrollItem.Item(R.drawable.driving_school, "driving class"));
                add(new HorizontalScrollItem.Item(R.drawable.medical_shop, "medical store"));
                add(new HorizontalScrollItem.Item(R.drawable.home_appliances, "home appliance"));
                add(new HorizontalScrollItem.Item(R.drawable.automobile, "automobile"));
                add(new HorizontalScrollItem.Item(R.drawable.ayurveda, "ayurveda"));
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