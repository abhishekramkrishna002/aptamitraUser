package in.aptamitra.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.Drawer;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import globalclass.GlobalClass;
import in.aptamitra.R;

public class ProfileActivity extends ActionBarActivity {
    Drawer drawer;
    @Bind(R.id.app_bar)
    Toolbar toolbar;

    @Bind(R.id.name)
    EditText nameEditText;
    @Bind(R.id.email)
    EditText emailEditText;
    @Bind(R.id.mobile)
    EditText mobileEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);
        ButterKnife.bind(this);
        drawer = ((GlobalClass) getApplicationContext()).navigationDrawer(this);
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer();
            }
        });
        SharedPreferences prefs = getSharedPreferences("cache", Context.MODE_PRIVATE);
        String profileJson = prefs.getString("profile", null);
        try {
            JSONObject profile = new JSONObject(profileJson);
            nameEditText.setText(profile.getString("name"));
            emailEditText.setText(profile.getString("email"));
            mobileEditText.setText(profile.getString("mobile"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

}
