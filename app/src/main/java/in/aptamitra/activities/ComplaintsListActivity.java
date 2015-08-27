package in.aptamitra.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import custom_objects.HorizontalScrollItem;
import custom_objects.StringDecoder;
import globalclass.GlobalClass;
import in.aptamitra.R;
import views.CircleImageView;


public class ComplaintsListActivity extends ActionBarActivity {


    @Bind(R.id.complaints_container)
    LinearLayout complaintsContainer;
    private LayoutInflater layoutInflater;
    Drawer drawer;
    @Bind(R.id.app_bar)
    Toolbar toolbar;
    TextView title;
    Typeface face, face2;


    Activity activity;
    public static String profile_id = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints_list);

        title=(TextView)findViewById(R.id.mycomplaints_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "trajan_pro_bold.otf");
        title.setTypeface(typeface);

        activity = this;
        ButterKnife.bind(this);
        drawer = ((GlobalClass) getApplicationContext()).navigationDrawer(this);
        layoutInflater = LayoutInflater.from(this);

        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        new ComplaintAsyncThread().execute(new Integer[]{});


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public class ComplaintAsyncThread extends AsyncTask<Integer, Integer, String> {

        ProgressDialog progress = null;

        public ComplaintAsyncThread() {
            // TODO Auto-generated constructor stub

        }

        @Override
        protected String doInBackground(Integer... params) {
            publishProgress(0);
            HttpResponse response = null;
            String result = null;
            try {
                // Create http client object to send request to server
                HttpClient client = new DefaultHttpClient();
                // Create URL string
                String URL = getResources().getString(R.string.get_complaints);
                // Create Request to server and get response
                HttpGet httpget = new HttpGet();
                httpget.setURI(new URI(URL));
                response = client.execute(httpget);
                result = EntityUtils.toString(response.getEntity());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            progress = new ProgressDialog(activity);
            progress.setMessage("fetching data..... ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected void onPostExecute(String results) {

            // TODO Auto-generated method stub
            super.onPostExecute(results);
            progress.hide();

            try {
                Log.d("my complaints", results);
                JSONObject result = new JSONObject(results);
                JSONArray complaints = result.getJSONArray("complaint");
                ArrayList<JSONObject> complaintList = new ArrayList<JSONObject>();
                SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
                JSONObject profile = new JSONObject(prefs.getString("profile", null));
                int profileId = Integer.parseInt(profile.getString("profile_id"));
                for (int i = 0; i < complaints.length(); i++) {

                    JSONObject complaint = complaints.getJSONObject(i);
                    Log.i("complaintid" + complaint.getString("complaint_id"), complaint.toString());
                    if (Integer.parseInt(complaint.getString("profile_id")) == profileId) {
                        complaintList.add(complaint);
                    }

                }
                /*
                set the complaints list::start
                 */
                // complaintsContainer.setAdapter(new ComplaintListAdapter(activity, R.layout.complaint_list_item, complaintList));
                displayAllComplaints(complaintList);
                /*
                set the complaints list::end
                 */


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    void displayAllComplaints(ArrayList<JSONObject> complaintObjects) {

        for (int position = 0; position < complaintObjects.size(); position++) {
            View row = layoutInflater.inflate(R.layout.complaint_list_item, complaintsContainer, false);
            /*
            parse the json and update the view::start
             */
            try {
                SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
                JSONObject profile = new JSONObject(prefs.getString("profile", null));


                final JSONObject data = complaintObjects.get(complaintObjects.size() - 1 - position);

                ((TextView) row.findViewById(R.id.complaint_title))
                        .setText(data.getString("complaint_title"));

                int statusColor;
                switch (data.getString("status")) {
                    case "open":
                        statusColor = R.color.red;
                        break;
                    case "on job":
                        statusColor = R.color.orange;
                        break;
                    case "escalated":
                        statusColor = R.color.blue;
                        break;
                    case "resolved":
                        statusColor = R.color.green;
                        break;
                    default:
                        statusColor = R.color.red;
                }

                ((TextView) row.findViewById(R.id.complaint_status)).setBackgroundColor(getResources().getColor(statusColor));
                ((TextView) row.findViewById(R.id.complaint_username))
                        .setText(profile.getString("name"));
                ((TextView) row.findViewById(R.id.complaint_place))
                        .setText(data.getString("location"));
                ((TextView) row.findViewById(R.id.complaint_company))
                        .setText(data.getString("agency"));
                ((TextView) row.findViewById(R.id.complaint_domain))
                        .setText(data.getString("problem_type"));
                ((TextView) row.findViewById(R.id.complaint_description))
                        .setText(data.getString("description"));

                if (!profile.getString("profile_image").trim().contentEquals("")) {
                    String url = StringDecoder.decode(profile.getString("profile_image"));
                    CircleImageView circleImageView = (CircleImageView) row.findViewById(R.id.profile_image);
                    Picasso.with(activity).load(url).into(circleImageView);

//                    ImageLoader imageLoader = ImageLoader.getInstance();
//                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                            .cacheOnDisc(true).resetViewBeforeLoading(true).build();
//                    CircleImageView circleImageView=(CircleImageView) row.findViewById(R.id.profile_image);
//                    imageLoader.displayImage(url, circleImageView, options);
                }


                if (!data.getString("complaint_image_1").trim().contentEquals("")) {
                    String url = StringDecoder.decode(data.getString("complaint_image_1"));
                    ImageView imageView = (ImageView) row.findViewById(R.id.complaint_image_1);
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.with(activity).load(url).placeholder(R.drawable.lazy_loading).into(imageView);
//
//                    ImageLoader imageLoader = ImageLoader.getInstance();
//                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                            .cacheOnDisc(true).resetViewBeforeLoading(true).build();
//                    ImageView imageView = (ImageView) row.findViewById(R.id.complaint_image_1);
//                    imageLoader.displayImage(url, imageView, options);
                } else {
                    ImageView imageView = (ImageView) row.findViewById(R.id.complaint_image_1);
                    imageView.setVisibility(View.GONE);
                    ((ViewGroup) row).removeView(imageView);
                }

                if (!data.getString("complaint_image_2").trim().contentEquals("")) {
                    String url = StringDecoder.decode(data.getString("complaint_image_2"));
                    ImageView imageView = (ImageView) row.findViewById(R.id.complaint_image_2);
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.with(activity).load(url).placeholder(R.drawable.lazy_loading).into(imageView);

//                    ImageLoader imageLoader = ImageLoader.getInstance();
//                    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                            .cacheOnDisc(true).resetViewBeforeLoading(true).build();
//                    ImageView imageView = (ImageView) row.findViewById(R.id.complaint_image_2);
//                    imageLoader.displayImage(url, imageView, options);
                } else {
                    ImageView imageView = (ImageView) row.findViewById(R.id.complaint_image_2);
                    imageView.setVisibility(View.GONE);
                    ((ViewGroup) row).removeView(imageView);
                }
                if (data.getString("complaint_image_1").trim().contentEquals("") && data.getString("complaint_image_2").trim().contentEquals("")) {
                    ((ViewGroup) row).removeView((HorizontalScrollView) row.findViewById(R.id.complaint_image_container));
                }
                TextView comment = (TextView) row.findViewById(R.id.textView5);
                comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            profile_id = data.getString("profile_id");

                            AddCommentActivity addComments = new AddCommentActivity();
                            //addComments.setComplaintId(Integer.parseInt(data.getString("complaint_id")));
                            Log.e("id", data.getString("complaint_id"));
                            Intent i = new Intent(ComplaintsListActivity.this, AddCommentActivity.class);
                            i.putExtra("complaint_id", data.getString("complaint_id"));
                            startActivity(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                complaintsContainer.addView(row);
            }

            /*
            parse the json and update the view::end
             */

        }
    }


}
