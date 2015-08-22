package in.aptamitra.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView title;
    Typeface face,face2;
    private LayoutInflater layoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.aboutus);
        drawer = ((GlobalClass) getApplicationContext()).navigationDrawer(this);
        layoutInflater = LayoutInflater.from(this);

        title=(TextView)findViewById(R.id.aboutus_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "trajan_pro_bold.otf");
        title.setTypeface(typeface);

        ImageView back = (ImageView) findViewById(R.id.icon_navigation);
        back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        drawer.openDrawer();
    }
});


    }
}