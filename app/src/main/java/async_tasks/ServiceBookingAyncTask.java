package async_tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.aptamitra.R;
import in.aptamitra.activities.LandingPageActivity;
import in.aptamitra.activities.RegisterActivity;

public class ServiceBookingAyncTask extends AsyncTask<HashMap<String, String>, Integer, String> {
    ProgressDialog progress = null;

    Activity activity;

    public ServiceBookingAyncTask(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("uploading complaint..... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }

    @Override
    protected String doInBackground(HashMap<String, String>... params) {
        publishProgress(0);
        try {
            HttpClient client = new DefaultHttpClient();

            String url = activity.getResources().getString(R.string.book_service) + "?profile_id=" + params[0].get("profile_id");
            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.addTextBody("type", params[0].get("type"));
            entityBuilder.addTextBody("profile_id", params[0].get("profile_id"));
            entityBuilder.addTextBody("profile_user", params[0].get("profile_user"));
            entityBuilder.addTextBody("mobile", params[0].get("mobile"));
            HttpEntity entity = entityBuilder.build();
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            Log.d("service upload", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        progress.hide();
        //Toast.makeText(activity, "uploaded complaint", Toast.LENGTH_LONG).show();
        JSONObject obj;
        try {
            obj = new JSONObject(result);
            String status = obj.getString("code");
            Log.e("object", obj.toString());

            if (status.contentEquals("200")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("service booked sucessfully!");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                Intent intent = new Intent(activity, LandingPageActivity.class);
                                activity.startActivity(intent);
                                        ((ActionBarActivity) activity).getSupportFragmentManager().popBackStack();

                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("Couldn't book service.Try again!");
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