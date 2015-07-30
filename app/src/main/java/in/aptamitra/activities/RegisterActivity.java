package in.aptamitra.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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



import java.util.HashMap;

import async_tasks.RegisterAsyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aptamitra.R;


public class RegisterActivity extends ActionBarActivity {

    Activity activity;
    EditText name, email, mobile, address,pincode,doornumber,  password;
    Button male, female, register;
    ImageView profileImage;
    String gender = new String("male");
    Bitmap profileImageBitmap;
    Spinner s1, s2;
    boolean flagMale = false, flagFemale = false;

    private String[] state = { "Bellandur", "BrigadeRoad","Brookefield", "Byatarayanapura", "C.V. Raman Nagar","Domlur Layout" +
            "Dooravani Nagar","HRBR Layout","Indira Nagar","ITPL Road","Jayamahal Road","Jeevan Bheema", "Kadugodi", "Kodihalli",
            "Krishnaraja Puram","Mahadevapura", "Marathahalli", "Old Airport Road" ,"Ramamurthy",
            "Adugodi", "Austin Town","Avenue Road", "Balepet", "Basavanagudi", "Benson Town", "Chickpet",
            "Chikpet", "Church Street", "Commercial Street", "Cox Town" ,"Crescent Road", "Cunningham High Grounds",
            "Infantry Road", "Kumara Krupa Road", "Lady Curzon Road", "Lavelle Road", "Magadi Road Palace",
            "R.T. Nagar", "Rajaji Nagar", "Richmond Road" ,"Richmond Town", "Seshadri Road", "Shivajinagar", "Sri Chatram Road" ,"Vasanth Nagar", "Vittal Mallya Road",
            "Banaswadi","Hebbal","Frazer Town","Devanahalli","Yeshwanthpur","Mathikere","Ganga Nagar","Sanjay Nagar","Jalahalli",
            "Hennur","Yelahanka","Mahatma Gandhi Road","Electronics City","Koramangala","Bannerghatta Road","Hosur Road","Banashankari","BTM Layout","Ulsoor","" +
            "Kumaraswamy Layout","Jaya Nagar","Kanakapura","Basaveshwara Nagar"};
    private String[] city = {"Bangalore","Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setup();




    }

    private void setup() {
//        final AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
//        mAwesomeValidation.setContext(this);
        name = (EditText) findViewById(R.id.editTextName);
        email = (EditText) findViewById(R.id.editTextEmail);
        pincode = (EditText)findViewById(R.id.editPin);
        doornumber = (EditText)findViewById(R.id.editDoor);
        male = (Button)findViewById(R.id.gender_male);
        female = (Button) findViewById(R.id.gender_female);
        profileImage = (ImageView) findViewById(R.id.register_profile_image);
//        mAwesomeValidation.addValidation(name, "[a-zA-Z\\s]+", "Type a human name");
//        mAwesomeValidation.addValidation(pincode, "[0-9]{6}", "Enter proper pincode");


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
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


        s1 = (Spinner)findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        mobile = (EditText)findViewById(R.id.editMobile);
       // mAwesomeValidation.addValidation(pincode, "[0-9]{10}", "Enter proper mobile number");
        address = (EditText)findViewById(R.id.editAddress);

        password = (EditText)findViewById(R.id.editTextPassword);
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



        register = (Button)findViewById(R.id.register_submit_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameText, emailText, mobileText, addressText,doorNumber, cityText, pincodeText, passwordText, confirmPasswordText,zoneText,localityText;
                nameText = name.getText().toString().trim();
                emailText =null;
                if(isValidEmailAddress(email.getText().toString().trim())){
                    emailText = email.getText().toString().trim();
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter a valid email id",Toast.LENGTH_LONG).show();
                }

                mobileText = mobile.getText().toString().trim();
                addressText = address.getText().toString().trim();

                cityText = (String)s1.getSelectedItem();
                pincodeText = pincode.getText().toString().trim();
                doorNumber = doornumber.getText().toString().trim();
                passwordText = password.getText().toString().trim();

                localityText=(String)s2.getSelectedItem();

                Log.e("name",nameText +mobileText+" "+ addressText+" "+ cityText+" "+doorNumber);


                /*
                check if all the fields are filled
                 */
//                if (nameText.contentEquals("") ||
//                        emailText.contentEquals("") ||
//                        mobileText.contentEquals("") ||
//                        addressText.contentEquals("") ||
//                        cityText.contentEquals("") ||
//                        passwordText.contentEquals("")
//                        ) {
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
//                    RegisterActivity.this);
//                    builder1.setMessage("Please fill all the fields!");
//                    builder1.setCancelable(true);
//                    builder1.setPositiveButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
//                }

                /*
                check for password and confirm password
                 */
//                else if (!passwordText.contentEquals(confirmPasswordText)) {
//                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
//                            activity);
//                    builder1.setMessage("Passwords do not match");
//                    builder1.setCancelable(true);
//                    builder1.setPositiveButton("Ok",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,
//                                                    int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder1.create();
//                    alert11.show();
//                }

                /*
                call the async task
                 */

                    /*
                    build the hashmap
                     */

                    HashMap<String, String> data = new HashMap<String, String>();
                    data.put("name", nameText);
                    data.put("password", passwordText);
                    data.put("address",doorNumber+addressText+pincodeText);
                    //data.put("zone",zoneText);
                    data.put("locality",localityText);
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
                    new RegisterAsyncTask(RegisterActivity.this, profileImageBitmap).execute(data);

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }

    public void open() {
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        profileImageBitmap = (Bitmap) data.getExtras().get("data");
        profileImage.setBackground(new BitmapDrawable(profileImageBitmap));
        profileImage.setImageBitmap(profileImageBitmap);
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
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


}
