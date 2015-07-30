package in.aptamitra.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import async_tasks.ServiceBookingAyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import globalclass.GlobalClass;
import in.aptamitra.R;
import me.tittojose.www.timerangepicker_library.TimeRangePickerDialog;

/**
 * Created by abhishek on 25-07-2015.
 */
public class BookServiceActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener, TimeRangePickerDialog.OnTimeRangeSelectedListener {

    @Bind(R.id.continue_button)
    Button continueButton;

    TextView timeTextView, dateTextView;
    Button btDate, btTime;
    EditText etdate, ettime,etService,etName,etNumber,etAddress;
    ImageView back;
    Activity activity;
    public static final String TIMERANGEPICKER_TAG = "timerangepicker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_service);
        ButterKnife.bind(this);
        activity = this;
        etName = (EditText) findViewById(R.id.etName);
        etNumber = (EditText) findViewById(R.id.etNumber);
        etAddress = (EditText) findViewById(R.id.etAddress);

        try {
        SharedPreferences sharedPreferences = getSharedPreferences("cache", Context.MODE_PRIVATE);
        JSONObject profileJsonObject = new JSONObject(sharedPreferences.getString("profile", null));
        //JSONObject profile= null;
        Log.e("pro",profileJsonObject.toString());


            etName.setText(profileJsonObject.getString("name"));
            etNumber.setText( profileJsonObject .getString("mobile"));
            etAddress.setText( profileJsonObject .getString("address"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        back = (ImageView) findViewById(R.id.icon_navigation);
        etdate = (EditText) findViewById(R.id.date);
        ettime = (EditText) findViewById(R.id.time);
        etService = (EditText) findViewById(R.id.etService);

        etService.setText((((GlobalClass) getApplicationContext())).mainMenu);



        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        BookServiceActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );

                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        if (savedInstanceState != null) {
            TimeRangePickerDialog tpd = (TimeRangePickerDialog) getSupportFragmentManager()
                    .findFragmentByTag(TIMERANGEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeRangeSetListener(this);
            }
        }
        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimeRangePickerDialog timePickerDialog = TimeRangePickerDialog.newInstance(
                        BookServiceActivity.this, false);
                timePickerDialog.show(getSupportFragmentManager(), TIMERANGEPICKER_TAG);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        etdate.setText(date);
    }

    public void onTimeRangeSelected(int startHour, int startMin, int endHour, int endMin) {
        String startTime = startHour + " : " + startMin;
        String endTime = endHour + " : " + endMin;
        ettime.setText(startTime + "-->" + endTime);
    }

    @OnClick(R.id.continue_button)
    void bookService(View view)
    {
        try {
            SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
            String profileJson = prefs.getString("profile", null);
            JSONObject profile=new JSONObject(profileJson);
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("type", etService.getText().toString());
            params.put("profile_id", profile.getString("profile_id"));
            params.put("profile_user", profileJson);
            params.put("mobile",((EditText)findViewById(R.id.etNumber)).getText().toString());
            params.put("time",ettime.getText().toString());
            params.put("date",etdate.getText().toString());
            if(etService.getText().toString().trim().contentEquals("") ||
                    ((EditText)findViewById(R.id.etNumber)).getText().toString().trim().contentEquals("") ||
                    ettime.getText().toString().trim().contentEquals("") ||
                    etdate.getText().toString().trim().contentEquals("")
                    )
            {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                        this);
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
            }
            else
            {
                new ServiceBookingAyncTask(this).execute(params);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
