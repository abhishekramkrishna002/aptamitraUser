package in.aptamitra.activities;

import java.util.List;
import java.util.Vector;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import adapters.PagerAdapter;
import fragments.Fragmentfifth;
import fragments.Fragmentfour;
import fragments.Fragmentone;
import fragments.Fragmentthree;
import fragments.Fragmenttwo;
import in.aptamitra.R;

/**
 * Created by Hemorvi Champaneria on 18-07-2015.
 */
public class ViewPagerFragmentActivity extends FragmentActivity {
    /** maintains the pager adapter*/
    private PagerAdapter mPagerAdapter;

    ImageView img1, img2, img3, img4, img5;
    Button getStarted;

    Typeface face;




    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.viewpager_layout);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        getStarted=(Button)findViewById(R.id.get_started);

        face = Typeface.createFromAsset(this.getAssets(), "TrajanPro_Regular.otf");
        getStarted.setTypeface(face);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewPagerFragmentActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





        //initialsie the pager
        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Fragmentone.class.getName()));
        //fragments.add(new Fragmentone());
        fragments.add(Fragment.instantiate(this, Fragmenttwo.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragmentthree.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragmentfour.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragmentfifth.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    img1.setImageResource(R.drawable.full_circle);
                    img2.setImageResource(R.drawable.empty_circel);
                    img3.setImageResource(R.drawable.empty_circel);
                    img4.setImageResource(R.drawable.empty_circel);
                    img5.setImageResource(R.drawable.empty_circel);
                }
                if (position == 1) {
                    img1.setImageResource(R.drawable.empty_circel);
                    img2.setImageResource(R.drawable.full_circle);
                    img3.setImageResource(R.drawable.empty_circel);
                    img4.setImageResource(R.drawable.empty_circel);
                    img5.setImageResource(R.drawable.empty_circel);
                }
                if (position == 2) {
                    img1.setImageResource(R.drawable.empty_circel);
                    img2.setImageResource(R.drawable.empty_circel);
                    img3.setImageResource(R.drawable.full_circle);
                    img4.setImageResource(R.drawable.empty_circel);
                    img5.setImageResource(R.drawable.empty_circel);
                }
                if (position == 3) {
                    img1.setImageResource(R.drawable.empty_circel);
                    img2.setImageResource(R.drawable.empty_circel);
                    img3.setImageResource(R.drawable.empty_circel);
                    img4.setImageResource(R.drawable.full_circle);
                    img5.setImageResource(R.drawable.empty_circel);
                }
                if (position == 4) {
                    img1.setImageResource(R.drawable.empty_circel);
                    img2.setImageResource(R.drawable.empty_circel);
                    img3.setImageResource(R.drawable.empty_circel);
                    img4.setImageResource(R.drawable.empty_circel);
                    img5.setImageResource(R.drawable.full_circle);
                }



            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

                //Toast.makeText(getApplication(),"Page Swiped", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

                //Toast.makeText(getApplication(),"Page Scrollstatus Changed", Toast.LENGTH_SHORT).show();

            }
        });
    }

}