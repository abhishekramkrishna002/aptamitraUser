package in.aptamitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import in.aptamitra.R;

/**
 * Created by Hemorvi Champaneria on 07-07-2015.
 */
public class AboutusActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        ImageView back = (ImageView) findViewById(R.id.icon_navigation);
        back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AboutusActivity.this,LandingPageActivity.class);
        startActivity(intent);
    }
});


    }
}