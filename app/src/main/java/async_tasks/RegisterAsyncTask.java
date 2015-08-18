package async_tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import in.aptamitra.R;
import in.aptamitra.activities.MainActivity;


/**
 * Created by abhishek on 25-06-2015.
 */
public class RegisterAsyncTask extends AsyncTask<HashMap<String, String>, Integer, String> {

    Bitmap image = null;
    ProgressDialog progress = null;
    Activity activity;

    public RegisterAsyncTask(Activity activity, Bitmap image) {
        this.image = image;
        this.activity = activity;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progress = new ProgressDialog(activity);
        progress.setMessage("registering..... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

    }

    @Override
    protected String doInBackground(HashMap<String, String>... parameters) {
        publishProgress(0);
        Log.d("register-hash-data", parameters[0].toString());
        try {
            HttpClient client = new DefaultHttpClient();

            HttpPost post = new HttpPost(activity.getResources().getString(R.string.register_user_url));

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addTextBody("name", parameters[0].get("name"));
            entityBuilder.addTextBody("password", parameters[0].get("password"));
            entityBuilder.addTextBody("address", parameters[0].get("address"));
            entityBuilder.addTextBody("gender", parameters[0].get("gender"));
            entityBuilder.addTextBody("city", parameters[0].get("city"));
            entityBuilder.addTextBody("mobile", parameters[0].get("mobile"));
            entityBuilder.addTextBody("email", parameters[0].get("email"));
            entityBuilder.addTextBody("locality", parameters[0].get("locality"));
            if (image != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                if (byteArray == null) {
                    Toast.makeText(activity, "image failed to be uploaded", Toast.LENGTH_LONG).show();
                    entityBuilder.addBinaryBody("profile_image", new byte[]{});
                } else {
                    Random r = new Random();
                    entityBuilder.addBinaryBody("profile_image", byteArray, ContentType.create("image/png"), "image_" + r.nextInt() + ".png");
                }
            } else {
                entityBuilder.addBinaryBody("profile_image", new byte[]{});
            }

            HttpEntity entity = entityBuilder.build();

            post.setEntity(entity);


            HttpResponse response = client.execute(post);

            HttpEntity httpEntity = response.getEntity();

            String result = EntityUtils.toString(httpEntity);

            Log.d("registration", result);
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


        try {
            JSONObject resultJson = new JSONObject(result);
            if (resultJson.getString("code").trim().contentEquals("200")) {
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                final View view = activity.getLayoutInflater().inflate(R.layout.verify_dialog, null, false);
                builder1.setView(view);
                builder1.setTitle("Mobile Verification Code");

                builder1.setCancelable(false);
                builder1.setPositiveButton("verify",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                String otpCode = ((EditText) view.findViewById(R.id.otp_code_edit_text)).getText().toString();
                                new VerifyUserAsyncTask(activity).execute(otpCode);

                                dialog.cancel();
                                //activity.finish();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();

                IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String msg = intent.getStringExtra("get_msg");

                        //Process the sms format and extract body &amp; phoneNumber
                        msg = msg.replace("\n", "");
                        String message = msg.substring(msg.lastIndexOf(":") + 1, msg.length());
                        Log.d("Received Message Body :", message.toString());

                        StringTokenizer tokens = new StringTokenizer(message, ".");
                        tokens.nextToken();
                        tokens.nextToken();
                        tokens.nextToken();
                        // this will contain "Your Aptamitra verification code is"
                        String code = tokens.nextToken();// this will contain "XXXX"
                        tokens.nextToken();// this will contain "for any query visit aptamitra.in"
                        ((EditText) view.findViewById(R.id.otp_code_edit_text)).setText(code);
                        Log.d("otp:", code);


                    }
                };

                activity.registerReceiver(receiver, intentFilter);


            } else if (resultJson.getString("code").trim().contentEquals("505")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("User already exists!");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                activity.finish();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            } else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        activity);
                builder1.setMessage("Registration failed.Try Again");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                                activity.finish();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();


    }


}
