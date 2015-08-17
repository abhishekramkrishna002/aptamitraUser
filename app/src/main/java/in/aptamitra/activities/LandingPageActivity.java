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
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

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
    private JSONObject landingPageJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        activity = this;
        ButterKnife.bind(this);
        init();
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
            progress.setMessage("please wait... ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }


        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer != null) {
                    drawer.openDrawer();
                }
            }
        });


//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_apps_white));
//
//        toolbar.setTitle("Aptamitra");
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Aptamitra");



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

    public void init() {
        makeJsonDataForSearch();
        ArrayList<HorizontalScrollItem> horizontalScrollItems = new ArrayList<>();
        String[] verticals=getResources().getStringArray(R.array.landing_page);
        for(int i=0;i<verticals.length;i++)
        {
            horizontalScrollItems.add(makeAdapter(verticals[i]));
        }
        LandingPageAdapter landingPageAdapter = new LandingPageAdapter(this, horizontalScrollItems);
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


    public HorizontalScrollItem makeAdapter(String key) {
        try {
            JSONArray jsonArray = landingPageJson.getJSONArray(key);
            ArrayList<HorizontalScrollItem.Item> items = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HorizontalScrollItem.Item horItem = new HorizontalScrollItem.Item(
                        "http://aptamitra.in/service_app/drawable/" + jsonObject.getString("image_link"),
                        jsonObject.getString("name"));
                items.add(horItem);
            }
            HorizontalScrollItem item = new HorizontalScrollItem(key, items);
            return item;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void makeJsonDataForSearch() {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        String jsonData = new String();
        try {
            inputStream = assetManager.open("landing_page.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                jsonData += readLine.trim();
            }
            Log.d("json-file", jsonData);
            landingPageJson = new JSONObject(jsonData);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}