package async_tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by fasal on 15-07-2015.
 */
public class AddCommentsAsyncTask extends AsyncTask<HashMap<String,String>,Integer, String> {
    JSONObject obj;
    Activity activity;
    ProgressDialog progress = null;
    Bitmap bp;
    public AddCommentsAsyncTask(Activity activity) {
        this.activity = activity;

    }

    @Override
    protected String doInBackground(HashMap<String, String>... params) {
        publishProgress(0);
        String result = null;
        HttpClient httpClient = new DefaultHttpClient();
        Set<String> keys = params[0].keySet();
        Iterator<String> keyIterator = keys.iterator();
        String queryString = new String();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            String value = params[0].get(key);
            if (keyIterator.hasNext()) {
                queryString += key + "=" + value + "&";
            } else {
                queryString += key + "=" + value;
            }
        }
        Log.d("comments debug", queryString);
        HttpPost httpPost = new HttpPost("http://aptamitra.in/service_app/comments.php?"+queryString);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity);
            Log.d("comment details", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("please wait.. ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progress.hide();
        Toast.makeText(activity.getApplicationContext(), "Comment added successfully", Toast.LENGTH_LONG).show();
        //new AnnotationAsyncTask(activity).execute(((GlobalClass) activity.getApplicationContext()).getVideoId());
       ((ActionBarActivity) activity).getSupportFragmentManager().popBackStack();
//        ((ActionBarActivity) activity).getSupportFragmentManager().popBackStack();
        activity.finish();

    }
}
