package in.aptamitra.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aptamitra.R;


public class ForgotPasswordActivity extends ActionBarActivity {


    /*
    get all the view componnets:start
     */

    @Bind(R.id.email)
    EditText emailText;
    /*
    get all the view componnets:end
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.frgt_password);
        ButterKnife.bind(this);



    }
    @OnClick(R.id.button)
    public void sendEmail(View v)
    {
        String email=emailText.getText().toString();
        if(!email.trim().contentEquals("")) {
            new ForgotPasswordAsyncThread().execute(email);
        }
        else
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(
                    ForgotPasswordActivity.this);
            builder1.setMessage("Enter email");
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public class ForgotPasswordAsyncThread extends AsyncTask<String, Integer, String> {

        ProgressDialog progress = null;

        public ForgotPasswordAsyncThread() {
            // TODO Auto-generated constructor stub

        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress(0);
            HttpResponse response = null;
            String result = null;
            try {
                // Create http client object to send request to server
                HttpClient client = new DefaultHttpClient();
                // Create URL string
                String URL = getResources().getString(R.string.forgot_password) + params[0];
                // Create Request to server and get response
                HttpGet httpget = new HttpGet();
                httpget.setURI(new URI(URL));
                response = client.execute(httpget);
                result = EntityUtils.toString(response.getEntity());
                return result;
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
            progress = new ProgressDialog(ForgotPasswordActivity.this);
            progress.setMessage("sending password .. ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }

        @Override
        protected void onPostExecute(String results) {

            // TODO Auto-generated method stub
            super.onPostExecute(results);
            try {
                progress.hide();
                JSONObject jsonResult = new JSONObject(results);
                if (jsonResult.getInt("code") == 200) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            ForgotPasswordActivity.this);
                    builder1.setMessage("Mail has been sent.Please login !");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
