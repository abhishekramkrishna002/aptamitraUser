package in.aptamitra.activities;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import adapters.SeearchAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aptamitra.R;


public class ServiceListActivity extends ActionBarActivity {

    @Bind(R.id.service_list)
    ListView serviceListView;
    @Bind(R.id.title)
    TextView titleTextView;
    @Bind(R.id.icon_back)
    ImageView backButton;
    JSONObject json;
    private ArrayList<String> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_list);
        ButterKnife.bind(this);
        String data = getIntent().getStringExtra("data");
        makeJsonDataForSearch();
        titleTextView.setText(data.toUpperCase());
        makeList(json, new String(), data);
//        for (int j = 0; j < listItems.size(); j++) {
//            Log.d("business_item", listItems.get(j));
//        }
        serviceListView.setAdapter(new BusinessListingAdapter(this, R.layout.business_list_item, listItems));


    }

    @OnClick(R.id.icon_back)
    void kill(View v) {
        finish();
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
                    Iterator<String> keys = obj.keys();
                    while (keys.hasNext()) {
                        tree += ">" + obj.getString(keys.next());
                    }
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

    public void makeJsonDataForSearch() {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        String jsonData = new String();
        try {
            inputStream = assetManager.open("business_listing.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                jsonData += readLine.trim();
            }
            Log.d("json-file", jsonData);
            json = new JSONObject(jsonData);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
