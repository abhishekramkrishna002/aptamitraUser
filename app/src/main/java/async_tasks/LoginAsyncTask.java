package async_tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.aptamitra.R;
import in.aptamitra.activities.LandingPageActivity;


/**
 * Created by abhishek on 13-06-2015.
 */
public class LoginAsyncTask extends AsyncTask<String, Integer, String> {
    ProgressDialog progress = null;
    Activity activity;

    public LoginAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(0);
        // Creating HTTP client
        HttpClient httpClient = new DefaultHttpClient();
        // Creating HTTP Post
        HttpPost httpPost = new HttpPost(activity.getResources().getString(R.string.login_user_url));
        // Building post parameters, key and value pair
        try {
            JSONObject loginObject = new JSONObject();
            loginObject.put("email", params[0]);
            loginObject.put("password", params[1]);
            httpPost.setEntity(new StringEntity(loginObject.toString()));
            HttpResponse response = null;
            String result = null;
            response = httpClient.execute(httpPost);

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
        progress.setMessage("logging in..... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        //Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG).show();
        Log.d("login", result);
        JSONObject obj;
        try {
            obj = new JSONObject(result);
            String status = obj.getString("status");

            if (status.contentEquals("200")) {
                SharedPreferences.Editor editor = activity.getSharedPreferences("cache", Context.MODE_PRIVATE).edit();

                editor.putString("profile", obj.getJSONObject("profile").toString());
                editor.commit();

                Intent intent = new Intent(activity, LandingPageActivity.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                activity.finish();
            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("You entered wrong credentials.Try Again");
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


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            progress.hide();
        }
    }
}