package in.aptamitra.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import custom_objects.StringDecoder;
import globalclass.GlobalClass;
import in.aptamitra.R;
import views.CircleImageView;

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
    @Bind(R.id.icon_profile)
    CircleImageView profileImageView;

    TextView title;
    Typeface face, face2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);
        ButterKnife.bind(this);
        drawer = ((GlobalClass) getApplicationContext()).navigationDrawer(this);

        title=(TextView)findViewById(R.id.profile_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "trajan_pro_bold.otf");
        title.setTypeface(typeface);



        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences prefs = getSharedPreferences("cache", Context.MODE_PRIVATE);
        String profileJson = prefs.getString("profile", null);
        try {
            JSONObject profile = new JSONObject(profileJson);
            nameEditText.setText(profile.getString("name"));
            emailEditText.setText(profile.getString("email"));
            mobileEditText.setText(profile.getString("mobile"));
            String url = StringDecoder.decode(profile.getString("profile_image"));
            if(!url.trim().contentEquals(""))
            {

//                ImageView imageView = (ImageView) row.findViewById(R.id.complaint_image_1);
                Picasso.with(ProfileActivity.this).load(url).into(profileImageView);
//                ImageLoader imageLoader = ImageLoader.getInstance();
//                DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                        .cacheOnDisc(true).resetViewBeforeLoading(true).build();
//                imageLoader.displayImage(url, profileImageView, options);
            }
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
