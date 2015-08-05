package in.aptamitra.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.Drawer;

import butterknife.Bind;
import globalclass.GlobalClass;
import in.aptamitra.R;

/**
 * Created by Hemorvi Champaneria on 07-07-2015.
 */
public class AboutusActivity extends ActionBarActivity {
    @Bind(R.id.app_bar)
    Toolbar toolbar;
    Drawer drawer;
    private LayoutInflater layoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.aboutus);
        drawer = ((GlobalClass) getApplicationContext()).navigationDrawer(this);
        layoutInflater = LayoutInflater.from(this);
        ImageView back = (ImageView) findViewById(R.id.icon_navigation);
        back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        drawer.openDrawer();
    }
});


    }
}