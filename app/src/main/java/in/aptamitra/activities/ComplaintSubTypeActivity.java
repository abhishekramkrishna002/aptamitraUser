package in.aptamitra.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import adapters.ComplaintSubTypeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import custom_objects.Speciality;
import globalclass.GlobalClass;
import in.aptamitra.R;


public class ComplaintSubTypeActivity extends ActionBarActivity {
    public HashMap<String, Speciality[]> services;
    @Bind(R.id.sub_types_list_view)
    ListView listView;
    @Bind(R.id.continue_button)
    Button continueButton;

    @Bind(R.id.search_problems)
    EditText searchEditText;


    @Bind(R.id.app_bar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView titleTextView;

    public static TextView header;
    ArrayList<String> listItems = new ArrayList<>();
    ComplaintSubTypeAdapter complaintSubTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints_sub_type);
        //init();
        ButterKnife.bind(this);
        ((GlobalClass) getApplicationContext()).subMenu = null;
        header = (TextView) findViewById(R.id.header);
        String data = getIntent().getStringExtra("data");
        Log.d("specialities", data);
        header.setText("My City > " + data + " > ");


        /*
        new code::start
         */

        init(data.toLowerCase() + ".json");
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

        /*
        new code::end
         */


    }


    public void init(String fileName) {
        JSONObject jsonObject = makeJsonDataForSearch(fileName);
        makeList(jsonObject, new String());
        Collections.sort(listItems, String.CASE_INSENSITIVE_ORDER);
        Speciality[] service = new Speciality[listItems.size()];
        int i = 0;
        for (String string : listItems) {
            Log.d("sub_type_items", string);
            service[i++] = new Speciality(string, false);
        }

        complaintSubTypeAdapter = new ComplaintSubTypeAdapter(this, service);

        listView.setAdapter(complaintSubTypeAdapter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                complaintSubTypeAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick(R.id.continue_button)
    void showRegisterComplaintPage(View view) {
        ProgressDialog progress;

        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Getting your current location");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        if (((GlobalClass) getApplicationContext()).subMenu != null) {

            Intent intent = new Intent(this, RegisterComplaintActivity.class);
            intent.putExtra("data", header.getText());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(
                    ComplaintSubTypeActivity.this);
            builder1.setMessage("Please select a type");
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


    public void makeList(Object object, String tree) {
        try {
        /*
        base case ::start
         */
            if (object instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) object;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    listItems.add(obj.getString("key"));
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

    public JSONObject makeJsonDataForSearch(String filename) {
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
            inputStream.close();
            Log.d("json_file", jsonData);
            return new JSONObject(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
