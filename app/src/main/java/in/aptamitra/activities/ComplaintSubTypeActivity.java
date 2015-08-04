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
import java.util.List;

import adapters.ComplaintSubTypeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import custom_objects.Speciality;
import globalclass.GlobalClass;
import in.aptamitra.R;


public class ComplaintSubTypeActivity extends ActionBarActivity {
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
        ButterKnife.bind(this);
        ((GlobalClass) getApplicationContext()).subMenu = null;
        header = (TextView) findViewById(R.id.header);

        //String data=((GlobalClass)getApplicationContext()).mainMenu+"";
        String data = getIntent().getStringExtra("data");
        //Toast.makeText(this, data, Toast.LENGTH_LONG).show();
        Log.d("specialities", data);
        Speciality[] probs = ((GlobalClass) getApplicationContext()).services.get(data + "");
//
//        Log.d("specialities",probs.length+"");
        final ComplaintSubTypeAdapter complaintSubTypeAdapter = new ComplaintSubTypeAdapter(this, probs);

        listView.setAdapter(complaintSubTypeAdapter);
        header.setText("Civic Issues > " + data + " > ");
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

    @OnClick(R.id.back_button)
    void goToHomePage() {
        finish();
    }


    @OnClick(R.id.continue_button)
    void showRegisterComplaintPage(View view) {
        ProgressDialog progress;

        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
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
