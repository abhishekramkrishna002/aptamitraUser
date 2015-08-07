package async_tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import adapters.SeearchAdapter;
import in.aptamitra.R;
import in.aptamitra.activities.LandingPageActivity;
import in.aptamitra.activities.SearchActivity;


/**
 * Created by abhishek on 13-06-2015.
 */
public class SearchAsyncTask extends android.os.AsyncTask<String, Integer, String> {
    ProgressDialog progress = null;
    Activity activity;
    private ArrayList<String> searches;//=new ArrayList<>();

    public SearchAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(0);
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpGet httpGet = new HttpGet(activity.getResources().getString(R.string.search_db));
        // Building post parameters, key and value pair
        try {
//            JSONObject loginObject = new JSONObject();
//            loginObject.put("email", params[0]);
//            loginObject.put("password", params[1]);
//            httpPost.setEntity(new StringEntity(loginObject.toString()));
            HttpResponse response = null;
            String result = null;
            response = httpClient.execute(httpGet);

            // writing response to log
            result = EntityUtils.toString(response.getEntity());
            Log.d("Http Response:", result);

            return result;
        } catch (Exception e) {
            // writing error to Log
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("please wait... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

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

                    if (obj.has("key")) {
                        tree += tree + " > " + obj.getString("key");
                        searches.add(tree);
                    }

//                    else  if (obj.has("name")) {
//                        Iterator<String> keys = obj.keys();
//                        while (keys.hasNext()) {
//                            tree += ">" + obj.getString("address") + ">" + obj.getString("contact") + ">" + obj.getString("name");
//                        }
//
//                    }

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


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        //Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
        Log.d("search", result);
        searches=new ArrayList<>();
        JSONObject obj;
        try {
            obj = new JSONObject(result);
            makeList(obj, new String());
            SearchActivity.listView.setAdapter(
                    new SeearchAdapter(activity, R.layout.search_list_item, searches));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            progress.hide();
        }
    }
}