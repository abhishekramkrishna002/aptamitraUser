package async_tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.aptamitra.R;
import in.aptamitra.activities.ChatActivity;
import in.aptamitra.activities.ChatActivityService;

/**
 * Created by abhishek on 25-07-2015.
 */
public class ChatAsyncTask extends AsyncTask<String, Integer, String> {

    Activity activity;
    String userMessage;
    ProgressDialog progress = null;


    public ChatAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        publishProgress(0);
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(activity.getResources().getString(R.string.chat_link));
        try {
            JSONObject requesJsonObject = new JSONObject();
            SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
            String profileJson = prefs.getString("profile", null);
            JSONObject profile = new JSONObject(profileJson);
            requesJsonObject.put("from_id", profile.getString("profile_id"));
            requesJsonObject.put("profile_user", profile);
            JSONObject message = new JSONObject();
            message.put("data", params[0]);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            message.put("date_tiime", dateFormat.format(date));
            requesJsonObject.put("message", message);
            userMessage = requesJsonObject.toString();
            httppost.setEntity(new StringEntity(requesJsonObject.toString()));
            HttpResponse result = httpclient.execute(httppost);
            HttpEntity resultEntity = result.getEntity();
            String resultFromChat = EntityUtils.toString(resultEntity);

            return resultFromChat;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
//        progress = new ProgressDialog(activity);
//        progress.setMessage("sending... ");
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setIndeterminate(true);
//        progress.setCancelable(false);
//        progress.show();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);
        Log.d("chat", s);
        try {
//            View view;
//
//            view=ChatActivity.chatListView.getChildAt(ChatActivity.chatListView.getChildCount());
//            ((TextView) view.findViewById(R.id.complaint_status)).
//                    setText("sent");
            addMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMessage() throws Exception {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.chat_list_item_1, ChatActivityService.chatListView, false);
        ((TextView) view.findViewById(R.id.message)).setText(new JSONObject(userMessage).getJSONObject("message").getString("data"));
        ChatActivityService.chatListView.addView(view);

    }
}
