package in.aptamitra.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import async_tasks.RegisterAsyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aptamitra.R;


public class RegisterActivity extends ActionBarActivity {

    final Integer COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE = 0;
    final Integer COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY = 1;
    Activity activity;


    EditText name, email, mobile, address, pincode, doornumber, password;
    Button male, female, register;
    ImageView profileImage;
    String gender = new String("male");
    Bitmap profileImageBitmap;
    Spinner s1, s2;
    boolean flagMale = false, flagFemale = false;
    @Bind(R.id.app_bar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView titleTextView;


    private String[] state = {"Bellandur", "BrigadeRoad", "Brookefield", "Byatarayanapura", "C.V. Raman Nagar", "Domlur Layout" +
            "Dooravani Nagar", "HRBR Layout", "Indira Nagar", "ITPL Road", "Jayamahal Road", "Jeevan Bheema", "Kadugodi", "Kodihalli",
            "Krishnaraja Puram", "Mahadevapura", "Marathahalli", "Old Airport Road", "Ramamurthy",
            "Adugodi", "Austin Town", "Avenue Road", "Balepet", "Basavanagudi", "Benson Town", "Chickpet",
            "Chikpet", "Church Street", "Commercial Street", "Cox Town", "Crescent Road", "Cunningham High Grounds",
            "Infantry Road", "Kumara Krupa Road", "Lady Curzon Road", "Lavelle Road", "Magadi Road Palace",
            "R.T. Nagar", "Rajaji Nagar", "Richmond Road", "Richmond Town", "Seshadri Road", "Shivajinagar", "Sri Chatram Road", "Vasanth Nagar", "Vittal Mallya Road",
            "Banaswadi", "Hebbal", "Frazer Town", "Devanahalli", "Yeshwanthpur", "Mathikere", "Ganga Nagar", "Sanjay Nagar", "Jalahalli",
            "Hennur", "Yelahanka", "Mahatma Gandhi Road", "Electronics City", "Koramangala", "Bannerghatta Road", "Hosur Road", "Banashankari", "BTM Layout", "Ulsoor", "" +
            "Kumaraswamy Layout", "Jaya Nagar", "Kanakapura", "Basaveshwara Nagar", "Vidyaranyapura"};



    private String[] city = {"Bengaluru", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ButterKnife.bind(this);
        activity = this;
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(state));
        Collections.sort(myList, String.CASE_INSENSITIVE_ORDER);
        state=myList.toArray(new String[myList.size()]);
        setup();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Typeface typeface = Typeface.createFromAsset(getAssets(), "trajan_pro_bold.otf");
        titleTextView.setTypeface(typeface);


    }

    private void setup() {
        name = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmail);
        pincode = (EditText) findViewById(R.id.editPin);
        doornumber = (EditText) findViewById(R.id.editDoor);
        male = (Button) findViewById(R.id.gender_male);
        female = (Button) findViewById(R.id.gender_female);
        profileImage = (ImageView) findViewById(R.id.register_profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open();

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
                builder.setTitle("Choose Image Source");
//                builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                builder.setItems(new CharSequence[]{"Camera"},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                switch (which) {
                                    case 1:

                                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent, COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY);
                                        break;
                                    case 0:
                                        intent = new Intent(
                                                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(intent, COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE);
                                        break;
                                }
                            }
                        });
                builder.show();


            }
        });

        /*
        deafault making male button active
         */
        toggleButton(male, flagMale);
        flagMale = !flagMale;
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagFemale) {
                    toggleButton(female, flagFemale);
                    flagFemale = !flagFemale;
                }
                toggleButton((Button) v, flagMale);
                flagMale = !flagMale;


            }
        });


        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagMale) {
                    toggleButton(male, flagMale);
                    flagMale = !flagFemale;
                }

                toggleButton((Button) v, flagFemale);
                flagFemale = !flagFemale;


            }
        });


        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        mobile = (EditText) findViewById(R.id.editMobile);
        // mAwesomeValidation.addValidation(pincode, "[0-9]{10}", "Enter proper mobile number");
        address = (EditText) findViewById(R.id.editAddress);

        password = (EditText) findViewById(R.id.editTextPassword);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter_state);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s2.setSelection(position);
                String selState = (String) s2.getSelectedItem();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, city);
        adapter_city
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter_city);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s1.setSelection(position);
                String selCity = (String) s1.getSelectedItem();

                if (selCity.contentEquals("Others")) {
                    Toast.makeText(getApplicationContext(), "AptaMitra will be soon in your city", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        register = (Button) findViewById(R.id.register_submit_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameText, emailText, mobileText, addressText, doorNumber, cityText, pincodeText, passwordText, confirmPasswordText, localityText;
                nameText = name.getText().toString().trim();
                emailText = email.getText().toString().trim();

                mobileText = mobile.getText().toString().trim();
                addressText = address.getText().toString().trim();

                cityText = (String) s1.getSelectedItem();
                pincodeText = pincode.getText().toString().trim();
                doorNumber = doornumber.getText().toString().trim();
                passwordText = password.getText().toString().trim();

                localityText = (String) s2.getSelectedItem();

                    /*
                    build the hashmap
                     */

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", nameText);
                data.put("password", passwordText);
                data.put("address", doorNumber + addressText + pincodeText);
                //data.put("zone",zoneText);
                data.put("locality", localityText);
                if (flagMale) {
                    data.put("gender", "male");
                } else {
                    data.put("gender", "female");
                }
                data.put("city", cityText);
                data.put("mobile", mobileText);
                data.put("email", emailText);

                    /*
                    call the registe async task
                     */
                if (
                        nameText.trim().toLowerCase().toString().contentEquals("") ||
                                passwordText.trim().toLowerCase().toString().contentEquals("") ||
                                doorNumber.trim().toLowerCase().toString().contentEquals("") ||
                                addressText.trim().toLowerCase().toString().contentEquals("") ||
                                pincodeText.trim().toLowerCase().toString().contentEquals("") ||
                                localityText.trim().toLowerCase().toString().contentEquals("") ||
                                cityText.trim().toLowerCase().toString().contentEquals("") ||
                                mobileText.trim().toLowerCase().toString().contentEquals("") ||
                                emailText.trim().toLowerCase().toString().contentEquals("")

                        ) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            RegisterActivity.this);
                    builder1.setMessage("Fill all the fields");
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

                } else if (mobileText.length() != 10) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            RegisterActivity.this);
                    builder1.setMessage("Mobile number is in appropriate");
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
                } else if (pincodeText.length() != 6) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            RegisterActivity.this);
                    builder1.setMessage("pincode is in appropriate");
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
                } else {

                    Drawable drawable = profileImage.getBackground();
                    if (drawable != null) {

                        new RegisterAsyncTask(RegisterActivity.this, drawableToBitmap(drawable)).execute(data);
                    } else {
                        new RegisterAsyncTask(RegisterActivity.this, null).execute(data);
                    }
                }


            }
        });




    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (profileImageBitmap != null) {
            profileImage.setBackground(new BitmapDrawable(profileImageBitmap));
        } else {
            try {
                if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_ONE_REQUEST_CODE_IMAGE) {
                    Log.d("image_camera", data.getData().toString());
                    profileImageBitmap = (Bitmap) data.getExtras().get("data");
                    profileImage.setBackground(new BitmapDrawable(profileImageBitmap));

                } else if (resultCode != 0 && requestCode == COMPLAINT_IMAGE_ONE_REQUEST_CODE_GALLERY) {


                    Uri selectedImage = data.getData();
                    Log.d("image_gallery", data.toString());
                    InputStream input = getContentResolver().openInputStream(selectedImage);
                    profileImageBitmap = BitmapFactory.decodeStream(input, null, null);
                    profileImage.setBackground(new BitmapDrawable(profileImageBitmap));


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void toggleButton(Button button, boolean flag) {
        if (flag) {
            button.setBackground(getResources().getDrawable(R.drawable.gray_border));
            button.setTextColor(getResources().getColor(R.color.orange));

        } else {
            button.setBackgroundColor(getResources().getColor(R.color.orange));
            button.setTextColor(Color.WHITE);

        }

    }


}
