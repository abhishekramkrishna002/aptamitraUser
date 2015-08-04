package in.aptamitra.activities;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;

import async_tasks.ComplaintUploadAyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import custom_objects.GPSTracker;
import globalclass.GlobalClass;
import in.aptamitra.R;


public class RegisterComplaintActivity extends ActionBarActivity {
    final Integer COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE = 0;
    final Integer COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY = 1;
    final Integer COMPLAINT_IMAGE_TWO_REQUEST_CODE_IMAGE = 2;
    final Integer COMPLAINT_IMAGE_TWO_REQUEST_CODE_GALLERY = 3;
    Activity activity;
    private GoogleMap mMap;
    HashMap<String, String> userData = new HashMap<String, String>();
    ImageView backButton;
    Drawable imageOne, imageTwo;
    Bitmap imageOneBitmap, imageTwoBitmap;
    @Bind(R.id.header)
    TextView header;
    @Bind(R.id.complaint_image_button_1)
    Button complaintImageOne;
    @Bind(R.id.complaint_image_button_2)
    Button complaintImageTwo;
    @Bind(R.id.etComplaintAddress)
    EditText complaintAddress;
    @Bind(R.id.reister_in_complaint_view_button)
    Button complaintRegisterButton;

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            // mMap=((SupportMapFragment)(CommunityActivity.fm.findFragmentById(R.id.map))).getMap();
            mMap = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map))).getMap();
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {

                }
            });
            if (mMap != null) {
                //mMap
                //setUpMap();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_register_layout);
        ButterKnife.bind(this);
        activity = this;
        ((EditText) activity
                .findViewById(R.id.etComplaintTitle)).setText(((GlobalClass) getApplicationContext()).subMenu);
        backButton = (ImageView) findViewById(R.id.bt_back);

        setUpMapIfNeeded();
        header.setText(getIntent().getStringExtra("data"));
        complaintImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Choose Image Source");
                builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        if (Build.VERSION.SDK_INT < 19) {
                                            Intent intent = new Intent();
                                            intent.setType("image/*");
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            // startActivityForResult(Intent.createChooser(intent, "Select Picture",COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY));
                                            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                                                    COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                                            intent.setType("image/*");
                                            //startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
                                            startActivityForResult(intent, COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY);
                                        }
                                        break;
                                    case 1:
                                       Intent  intent = new Intent(
                                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE);
                                        break;
                                }
                            }
                        });
                builder.show();

            }
        });
        complaintImageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Choose Image Source");
                builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
//                                        Intent intent = new Intent();
//                                        intent.setType("image/*");
//                                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                                        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
//                                                COMPLAINT_IMAGE_TWO_REQUEST_CODE_GALLERY);
                                        if (Build.VERSION.SDK_INT < 19) {
                                            Intent intent = new Intent();
                                            intent.setType("image/*");
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            // startActivityForResult(Intent.createChooser(intent, "Select Picture",COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY));
                                            startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                                                    COMPLAINT_IMAGE_TWO_REQUEST_CODE_GALLERY);
                                        } else {
                                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                                            intent.setType("image/*");
                                            //startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
                                            startActivityForResult(intent, COMPLAINT_IMAGE_TWO_REQUEST_CODE_GALLERY);
                                        }
                                        break;
                                    case 1:
                                        Intent intent = new Intent(
                                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, COMPLAINT_IMAGE_TWO_REQUEST_CODE_IMAGE);
                                        break;
                                }
                            }
                        });
                builder.show();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*
         * playing with te GPS
		 */
        String address = null;
        GPSTracker gps = new GPSTracker(
                activity);

        if (gps.canGetLocation()) {
            double lat = gps.getLatitude(); // returns latitude
            double lon = gps.getLongitude(); // returns longitude
            Location loc = gps.location;
            address = gps.getAddress(lat, lon);
            complaintAddress.setText(address);
            gps.stopUsingGPS();
            Log.d("google maps", "lat::" + lat + " lon::" + lon);

            setUpMap(lat, lon);


        } else {
            gps.showSettingsAlert();
            double lat = gps.getLatitude(); // returns latitude
            double lon = gps.getLongitude(); // returns longitude
            address = gps.getAddress(lat, lon);
            complaintAddress.setText(address);
            gps.stopUsingGPS();
            setUpMap(lat, lon);
        }

    }

    private void setUpMap(double lat, double lng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Marker"));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // Log.d("result",resultCode+"");
        if (imageOne != null) {
            complaintImageOne.setBackground(imageOne);
        }
        if (imageTwo != null) {
            complaintImageTwo.setBackground(imageTwo);
        }
        if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE) {
            imageOneBitmap = (Bitmap) data.getExtras().get("data");
            imageOne = new BitmapDrawable(getResources(), imageOneBitmap);
            complaintImageOne.setBackground(imageOne);
        }
        if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_TWO_REQUEST_CODE_IMAGE) {
            imageTwoBitmap = (Bitmap) data.getExtras().get("data");
            imageTwo = new BitmapDrawable(getResources(), imageTwoBitmap);
            complaintImageTwo.setBackground(imageTwo);
        }
        if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY) {
            Uri selectedImage = data.getData();
            Log.i("image_gallery", data.toString());

            /*
            start processing
             */
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(selectedImage);

// Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

// where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);

            String filePath = "";

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }

            cursor.close();

            /*
            end processing
             */

            imageOneBitmap = BitmapFactory.decodeFile(filePath);
            //bp = (Bitmap) data.getExtras().get("data");
            imageOne = new BitmapDrawable(getResources(), imageOneBitmap);
            //complaintImageContainer.removeAllViews();
            complaintImageOne.setBackground(imageOne);
            //complaintImage.setImageBitmap(bp);
        }
        if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_TWO_REQUEST_CODE_GALLERY) {
            Uri selectedImage = data.getData();

            Log.i("image_gallery", data.toString());

            /*
            start processing
             */
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(selectedImage);

// Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

// where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[]{id}, null);

            String filePath = "";

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }

            cursor.close();

            /*
            end processing
             */

            imageTwoBitmap = BitmapFactory.decodeFile(filePath);
            //bp = (Bitmap) data.getExtras().get("data");
            imageTwo = new BitmapDrawable(getResources(), imageTwoBitmap);
            //complaintImageContainer.removeAllViews();
            complaintImageTwo.setBackground(imageTwo);
            //complaintImage.setImageBitmap(bp);
        }
    }

    @OnClick(R.id.reister_in_complaint_view_button)
    void registerComplaint(View v) {
        SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
        String profileJson = prefs.getString("profile", null);
        try {
            JSONObject profile = new JSONObject(profileJson);

            userData.put("profile_user",
                    profileJson.replace("\"", "\\\""));
            userData.put("profile_id", profile.getString("profile_id"));
            // userData.put("zone", profile.getString("zone"));
            userData.put("locality", profile.getString("locality"));
//            ((TextView) activity.findViewById(R.id.etComplaintAddress))
//                    .setText(profile.getString("address"));
//            userData.put("address", profile.getString("address"));
            userData.put("address", complaintAddress.getText().toString());

            userData.put("name", profile.getString("name"));

            userData.put("email", profile.getString("email"));

            userData.put("mobile", profile.getString("mobile"));

            GlobalClass globalClass = (GlobalClass) activity.getApplicationContext();
//                    userData.put("agency", (String)spinnerAgency.getSelectedItem());
            userData.put("agency", globalClass.mainMenu);
//                    userData.put("problem_type", (String)spinnerProblems.getSelectedItem());
            userData.put("problem_type", globalClass.subMenu);
            String title = ((EditText) activity
                    .findViewById(R.id.etComplaintTitle)).getText()
                    .toString();
            userData.put("complaint_title", title);

            String description = ((EditText) activity
                    .findViewById(R.id.etComplaintProblem)).getText()
                    .toString();
            userData.put("complaint_decription", description);
            String landmark = ((EditText) activity
                    .findViewById(R.id.etComplaintLandmark)).getText()
                    .toString();
            userData.put("complaint_landmark", landmark);
            String address = ((EditText) activity
                    .findViewById(R.id.etComplaintAddress)).getText()
                    .toString();
            userData.put("complaint_address", address);
            /*
            validation begin
             */

            if (title.trim().contentEquals("") ||
                    description.contentEquals("") ||
                    address.contentEquals("") ||
                    landmark.contentEquals("")
                    ) {
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(
                        activity);
                builder1.setMessage("Fill all the details");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                android.app.AlertDialog alert11 = builder1.create();
                alert11.show();
            }
            /*
            validation end
             */


            else {
                new ComplaintUploadAyncTask(activity, imageOneBitmap, imageTwoBitmap).execute(userData);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}