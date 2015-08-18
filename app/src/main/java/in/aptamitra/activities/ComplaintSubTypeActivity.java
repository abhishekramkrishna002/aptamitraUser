package in.aptamitra.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import adapters.ComplaintSubTypeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import custom_objects.Speciality;
import globalclass.GlobalClass;
import in.aptamitra.R;


public class ComplaintSubTypeActivity extends ActionBarActivity {
    public HashMap<String, Speciality[]> services;
    @Bind(R.id.sub_types_list_view)
    ListView listView;
    @Bind(R.id.continue_button)
    Button continueButton;

    @Bind(R.id.search_problems)
    EditText searchEditText;

    @Bind(R.id.back_button)
    ImageView backButton;

    public static TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaints_sub_type);
        init();
        ButterKnife.bind(this);
        ((GlobalClass) getApplicationContext()).subMenu = null;
        header = (TextView) findViewById(R.id.header);

        //String data=((GlobalClass)getApplicationContext()).mainMenu+"";
        String data = getIntent().getStringExtra("data");
        //Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        Log.d("specialities", data);
//        Speciality[] probs = ((GlobalClass) getApplicationContext()).services.get(data + "");
        Speciality[] probs = services.get(data.toLowerCase());
        header.setText("My City > " + data + " > ");
        if (probs != null && probs.length > 0) {
            final ComplaintSubTypeAdapter complaintSubTypeAdapter = new ComplaintSubTypeAdapter(this, probs);

            listView.setAdapter(complaintSubTypeAdapter);
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    complaintSubTypeAdapter.getFilter().filter(s);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


    }

    public void init() {
        services = new HashMap<>();
        Speciality[] bbmpService = {

                new Speciality("Road", false),
                new Speciality("footpath", false),
                new Speciality("drainage", false),
                new Speciality("dead bird", false),
                new Speciality("dead animal", false),
                new Speciality("mosquitoes", false),
                new Speciality("Road Damage", false),
                new Speciality("Storm water drainage", false),
                new Speciality("Potholes", false),
                new Speciality("Site Cleaning", false),
                new Speciality("Road asphalt", false),
                new Speciality("incomplete road repairs", false),
                new Speciality("Pavement stone slab", false),
                new Speciality("Debris of buildings", false),
                new Speciality("Storm water drainage", false),
                new Speciality("Site Cleaning", false),
                new Speciality("Pavement is broken", false),
                new Speciality("play ground maintenance", false),
                new Speciality("Food adulteration", false),
                new Speciality("Shops Licenses", false),
                new Speciality("Street Vendor Management", false),
                new Speciality("Food vendor on footpath", false),
                new Speciality("dog menace", false),
                new Speciality("stray animals", false),
                new Speciality("garbage", false),
                new Speciality("Remove tree litter", false),
                new Speciality("illegal banners", false),
                new Speciality("illegal advertisments", false),
                new Speciality("Property tax", false),
                new Speciality("voter id", false),
                new Speciality("khatha transfer", false),
                new Speciality("banner permissions", false),
                new Speciality("Park Maintainence", false),
                new Speciality("Divider plants maintainence", false),
                new Speciality("Street Light Maintainence", false),
                new Speciality("switch for street light", false),
                new Speciality("Timer for streetlight", false),
                new Speciality("Tree cutting", false),
                new Speciality("Tree pruning", false),
                new Speciality("Tree saplings plantation", false),
                new Speciality("Tree Maintainence", false),
                new Speciality("broken tree", false)
        };

        services.put("B B M P".toLowerCase(), bbmpService);
            /*
            load the bbmp specs::end
             */
            /*
            load the bwssb specs::start
             */
        Speciality[] bwssbService = {
                new Speciality("Water supply ", false),
                new Speciality("Under ground drainage", false),
                new Speciality("Meter", false),
                new Speciality("New connection", false),
                new Speciality("Billing complaints", false),
                new Speciality("Pipe damage", false),
                new Speciality("Sanitary manhole damage", false),
                new Speciality("sanitary pipe damage", false),
                new Speciality("water pipe damage", false),
                new Speciality("Sewerage", false),
                new Speciality("Other", false),
                new Speciality("Sanitary blocked", false),
                new Speciality("Sanitary manhole cover damage", false),
                new Speciality("Sanitary manholecover missing", false),
                new Speciality("Sanitary obstruction to traffic", false),
                new Speciality("Sanitary overflow", false),
                new Speciality("Sanitary road damage by BWSSB", false),
                new Speciality("Water", false),
                new Speciality("Contaminated water", false),
                new Speciality("Low water pressure", false),
                new Speciality("night time water release", false),
                new Speciality("No water", false),
                new Speciality("Request to change water timing", false),
                new Speciality("Water leakage", false)

        };
        services.put("B W S S B".toLowerCase(), bwssbService);
            /*
            load the bbmo specs::end
             */
         /*
            load the bescom specs::start
             */
        Speciality[] bescomService = {
                new Speciality("Fuse of call, Line Breakdown, Pole Broken", false),
                new Speciality("Voltage variation where no expantion or enhancement of network is involved", false),
                new Speciality("Voltage variation where up-gradation of distribution systemis required", false),
                new Speciality("Meter complaints", false),
                new Speciality("Inspect and check correctness", false),
                new Speciality("Replace slow creeping or stuck meters", false),
                new Speciality("Replace Burnt meters if cause not attributable to consumer", false),
                new Speciality("Replace burnt meter in all other places", false),
                new Speciality("Billing issues", false),
                new Speciality("Where field report is not requires", false),
                new Speciality("allegations on staff", false),
                new Speciality("Where field report is required", false),
                new Speciality("Reconnection of supply following disconnection", false),
                new Speciality("Safety issue", false),
                new Speciality("Straighting of bent pole", false),
                new Speciality("Replacement of damaged pole", false),
                new Speciality("Shifting of poles", false),
                new Speciality("Tree trimming", false),
                new Speciality("TC Failure complaints", false),
                new Speciality("Water supply", false),
                new Speciality("domestic", false),
                new Speciality("industry", false),
                new Speciality("mixed load", false),
                new Speciality("irrigation", false),
                new Speciality("Theft", false),
                new Speciality("Hooking under niranthara jothiyojane", false),
                new Speciality("Allegations on staff", false),
                new Speciality("New connection/additional load", false),
                new Speciality("Release of supply where service is feasible from existing network", false),
                new Speciality("Release of supply where network expansion/enhancement required for providing connection", false),
                new Speciality("IP sets", false),
                new Speciality("Phase conversion", false),
                new Speciality("Conversion of LT single phase to LT 3 phase", false),
                new Speciality("Conversion from LT to HT", false),
                new Speciality("Transfer of ownership and conversion", false),
                new Speciality("Title transfer of ownership", false),
                new Speciality("Change of category", false),
                new Speciality("Refund of deposits", false),
                new Speciality("Issue of certificate", false),
                new Speciality("Additional TC / Enhancement", false),
                new Speciality("Additional TC", false),
                new Speciality("Enhancement of TC", false),
                new Speciality("General", false),
                new Speciality("Complaints that is not covered under the above category", false)

        };
        services.put("B E S C O M".toLowerCase(), bescomService);
        Speciality[] bmtcService = new Speciality[]{

                new Speciality("RESTART BMTC ROUTE ", false),
                new Speciality("Issues with a Bus Route", false),
                new Speciality("Bus Timing Issue", false),
                new Speciality("Bus Frequency", false),
                new Speciality("Lost and Found", false),
                new Speciality("Bus Staff Behaviour", false),
                new Speciality("Bus Condition", false),
                new Speciality("Bus Cleanliness", false),
                new Speciality("Bus Pass Issues", false)
        };
        services.put("B M T C".toLowerCase(), bmtcService);

        Speciality[] ambulanceServices = new Speciality[]{

                new Speciality("Request for An Ambulance service ", false)
        };
        services.put("Ambulance".toLowerCase(), ambulanceServices);
        Speciality[] fireServices = new Speciality[]{

                new Speciality("Report a Fire issue ", false)
        };
        services.put("Fire".toLowerCase(), fireServices);
        Speciality[] mortuaryServices = new Speciality[]{

                new Speciality("Need Mortuary Vehicle ", false),
                new Speciality("Need cremation centre address ", false),
                new Speciality("Book a time slot for nearest cremation centre ", false),
                new Speciality("Time extension for cremation", false),
<<<<<<< HEAD
                new Speciality("Mortuary Box",false)
=======
                new Speciality("Mortuary Box", false)
>>>>>>> 9882bb8d884f798e8ae9fc468d583a07d3a2d5f9
        };
        services.put("Mortuary".toLowerCase(), mortuaryServices);
        Speciality[] lawAndOrderServices = new Speciality[]{

                new Speciality("Eve Teasing", false),
                new Speciality("Murder", false),
                new Speciality("theft", false),
<<<<<<< HEAD
                new Speciality("neighbour conflicts", false),

=======
                new Speciality("neighbour conflicts", false)
>>>>>>> 9882bb8d884f798e8ae9fc468d583a07d3a2d5f9
        };
        services.put("Law & Order".toLowerCase(), lawAndOrderServices);
        Speciality[] bloodServices = new Speciality[]{

                new Speciality("Need B+Ve", false),
                new Speciality("Need B-ve", false),
                new Speciality("Need O+ve", false),
                new Speciality("Need O-ve", false),
                new Speciality("Need AB+", false),
                new Speciality("Need AB-ve", false),
                new Speciality("Need A+ve", false),
                new Speciality("Need A-ve", false),
                new Speciality("Enrol Me as a Donor", false)
        };
        services.put("Blood".toLowerCase(), bloodServices);


            /*
            load the bescom specs::end
             */

    }

    @OnClick(R.id.back_button)
    void goToHomePage() {
        finish();
    }


    @OnClick(R.id.continue_button)
    void showRegisterComplaintPage(View view) {
        ProgressDialog progress;

        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Getting your current location");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        if (((GlobalClass) getApplicationContext()).subMenu != null) {

            Intent intent = new Intent(this, RegisterComplaintActivity.class);
            intent.putExtra("data", header.getText());
            startActivity(intent);
            finish();
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(
                    ComplaintSubTypeActivity.this);
            builder1.setMessage("Please select a type");
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


}
