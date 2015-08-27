package in.aptamitra.activities;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import adapters.BusinessListingAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aptamitra.R;


public class ServiceListActivity extends ActionBarActivity {

    @Bind(R.id.service_list)
    ListView serviceListView;


    JSONArray json;
    private ArrayList<String> listItems = new ArrayList<>();

    @Bind(R.id.app_bar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_list);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        Typeface typeface = Typeface.createFromAsset(getAssets(), "trajan_pro_bold.otf");
        titleTextView.setTypeface(typeface);


        String data = getIntent().getStringExtra("data");
        makeJsonDataForSearch(data.toLowerCase().trim() + ".json");
        titleTextView.setText(data.toUpperCase());
        makeList(json, new String(), data);
        serviceListView.setAdapter(new BusinessListingAdapter(this, R.layout.business_list_item, listItems));

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void makeList(Object object, String tree, String criteria) {
        try {
        /*
        base case ::start
         */
            if (object instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) object;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
//                    Iterator<String> keys = obj.keys();
                    tree += ">" + obj.getString("name") + ">" + obj.getString("address") + ">" + obj.getString("contact");
//                    while (keys.hasNext()) {
//                        tree += ">" + obj.getString(keys.next());
//                    }
                    listItems.add(tree);
                }
                return;
            }

        /*
        base case::end
         */

        /*
        recursive case::start
         */
            if (object instanceof JSONObject) {
                JSONObject jsonObject = ((JSONObject) object);
                Iterator<String> keys = jsonObject.keys();
/*
applicable only for seacrh::start
 */
//                if (jsonObject.length() == 0) {
//                    //Log.i("childs",tree);
//                    listItems.add(tree);
//                }
                /*
applicable only for seacrh::end
 */
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (key.toLowerCase().trim().contentEquals(criteria.toLowerCase().trim())) {
                        makeList(jsonObject.get(key), tree + ">" + key, criteria);
                    }
                }
            }
        /*
        recursive case::end
         */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeJsonDataForSearch(String filename) {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        String jsonData = new String();
        try {
            //inputStream = assetManager.open("business_listing.json");
            inputStream = assetManager.open(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                jsonData += readLine.trim();
            }
            Log.d("json-file", jsonData);
            json = new JSONArray(jsonData);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
