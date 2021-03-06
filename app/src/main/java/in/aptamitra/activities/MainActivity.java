package in.aptamitra.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import async_tasks.LoginAsyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.aptamitra.R;


public class MainActivity extends ActionBarActivity {


    /*
    get all the view componnets:start
     */
    @Bind(R.id.register_text_view)
    TextView registerTextView;
    @Bind(R.id.forgot_password_text_view)
    TextView forgotPasswordTextView;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.login_email)
    EditText email;
    @Bind(R.id.login_password)
    EditText password;

    TextView titletext;

    Button forgot;

    Typeface face, face2;


    /*
    get all the view componnets:end
     */

    @OnClick(R.id.forgot_password_text_view)
    public void forgotPassword(View v)
    {
        Intent i=new Intent(MainActivity.this,ForgotPasswordActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.login);

        /*face = Typeface.createFromAsset(this.getAssets(), "TrajanPro_Regular.otf");
        loginButton.setTypeface(face);
        registerTextView.setTypeface(face);*/

        ButterKnife.bind(this);

//        titletext = (TextView) findViewById(R.id.titletext);
        //forgot = (Button) findViewById(R.id.forgot);
        face = Typeface.createFromAsset(this.getAssets(), "TrajanPro_Regular.otf");
        loginButton.setTypeface(face);
        registerTextView.setTypeface(face);
//        titletext.setTypeface(face);
        //forgot.setTypeface(face);
        face2 = Typeface.createFromAsset(this.getAssets(), "Narkisim.ttf");
        email.setTypeface(face2);
        password.setTypeface(face2);

 }


    @OnClick(R.id.register_text_view)
    void showRegistrationActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.login_button)
    void loginUser(View view) {
        /*
        check for username and password::start
         */
        Log.d("login", email.getText().toString());
        Log.d("login", password.getText().toString());

        if (email.getText().toString().trim().contentEquals("") ||
                password.getText().toString().trim().contentEquals("")) {
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
        } else {
            new LoginAsyncTask(this).execute(new String[]{email.getText().toString().trim(), password.getText().toString().trim()});

        }
        /*
        check for username and password::end
         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
