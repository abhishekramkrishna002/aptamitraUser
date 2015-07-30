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
import android.widget.ImageView;

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
        drawer=((GlobalClass)getApplicationContext()).navigationDrawer(this);
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

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }

    public void initilaiseMenuSystem() {
        items = new ArrayList<HorizontalScrollItem>() {{
            add(new HorizontalScrollItem("My City", new ArrayList<HorizontalScrollItem.Item>() {{
                add(new HorizontalScrollItem.Item(R.drawable.icon_bbmp, "B B M P", "Bruhat Bangalore Mahanagara Palike"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_bwssb, "B W S S B", "Bangalore Water Supply and Sewerage Board"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_bescom, "B E S C O M", "Bangalore Electricity Supply Company "));
            }}));
            add(new HorizontalScrollItem("My Booking", new ArrayList<HorizontalScrollItem.Item>() {{
                add(new HorizontalScrollItem.Item(R.drawable.icon_beauty, "Beautician"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_electrician, "Electrician"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_plumbing, "Plumber"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_carpenter, "Carpenter"));
                add(new HorizontalScrollItem.Item(R.drawable.laundry, "Laundry"));
                add(new HorizontalScrollItem.Item(R.drawable.medicine, "Medicine"));
                add(new HorizontalScrollItem.Item(R.drawable.mobile_repair, "Mobile-PC repair"));
                add(new HorizontalScrollItem.Item(R.drawable.chartered_accountant, "Accountant"));
                add(new HorizontalScrollItem.Item(R.drawable.company_secretery, "Secretery"));
                add(new HorizontalScrollItem.Item(R.drawable.trademark_copyright, "Trademark"));
                add(new HorizontalScrollItem.Item(R.drawable.wedding_phptpgraphy, "Wedding"));
                add(new HorizontalScrollItem.Item(R.drawable.driver, "Driver"));
                add(new HorizontalScrollItem.Item(R.drawable.flute, "Flute"));
                add(new HorizontalScrollItem.Item(R.drawable.guitar, "Guitar"));
                add(new HorizontalScrollItem.Item(R.drawable.house_keeping, "House Keeping"));
            }}));
            add(new HorizontalScrollItem("My Delivery", new ArrayList<HorizontalScrollItem.Item>() {{

                add(new HorizontalScrollItem.Item(R.drawable.icon_food, "Food"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_groceries, "Groceries"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_fruits, "Fruits"));
                add(new HorizontalScrollItem.Item(R.drawable.icon_flowers, "Flowers"));
            }}));

        }};
//        HomeHorizontalScrollAdapter homeHorizontalScrollAdapter = new HomeHorizontalScrollAdapter(this, items);
//        listView.setAdapter(homeHorizontalScrollAdapter);
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