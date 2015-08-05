package in.aptamitra.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import adapters.SeearchAdapter;
import async_tasks.LoginAsyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import globalclass.GlobalClass;
import in.aptamitra.R;


public class SearchActivity extends ActionBarActivity {


    private JSONObject searchJson;
    ListView listView = null;
    EditText searchText = null;
    SeearchAdapter searchAdapter;
    private ArrayList<String> searches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        searches = new ArrayList<>();
        makeJsonDataForSearch();
        makeList(searchJson, new String());
        listView = (ListView) findViewById(R.id.search_list);
        searchAdapter = new SeearchAdapter(this, R.layout.search_list_item, searches);
        listView.setAdapter(searchAdapter);
        searchText = (EditText) findViewById(R.id.search_bar);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.getFilter().filter(s.toString().toLowerCase());
                searchAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        ((ImageView) findViewById(R.id.icon_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void makeList(Object object, String tree) {
        try {
        /*
        base case ::start
         */
            if (object instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) object;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    //Log.i("childs",tree+" > "+obj.getString("key"));
                    searches.add(tree + ">" + obj.getString("key"));
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

                if (jsonObject.length() == 0) {
                    //Log.i("childs",tree);
                    searches.add(tree);
                }
                while (keys.hasNext()) {
                    String key = keys.next();
                    makeList(jsonObject.get(key), tree + ">" + key);
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
            inputStream = assetManager.open("sample.json");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                jsonData += readLine.trim();
            }
            Log.d("json-file", jsonData);
            searchJson = new JSONObject(jsonData);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
