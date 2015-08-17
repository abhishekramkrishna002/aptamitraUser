package globalclass;


<<<<<<< HEAD
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import custom_objects.Speciality;
import custom_objects.StringDecoder;
import in.aptamitra.R;
import in.aptamitra.activities.AboutusActivity;
import in.aptamitra.activities.ComplaintsListActivity;
import in.aptamitra.activities.LandingPageActivity;
import in.aptamitra.activities.MainActivity;
import in.aptamitra.activities.NotificationsListActivity;
import in.aptamitra.activities.PrivacyActivity;
import in.aptamitra.activities.ProfileActivity;
import in.aptamitra.activities.TermsActivity;
=======
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.drawable.Drawable;
        import android.net.Uri;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ImageView;

        import com.mikepenz.materialdrawer.Drawer;
        import com.mikepenz.materialdrawer.DrawerBuilder;
        import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
        import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
        import com.mikepenz.materialdrawer.model.DividerDrawerItem;
        import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
        import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
        import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
        import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
        import com.mikepenz.materialdrawer.model.interfaces.IProfile;
        import com.mikepenz.materialdrawer.util.DrawerImageLoader;
        import com.squareup.picasso.Picasso;

        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;

        import custom_objects.Speciality;
        import custom_objects.StringDecoder;
        import in.aptamitra.R;
        import in.aptamitra.activities.AboutusActivity;
        import in.aptamitra.activities.ComplaintsListActivity;
        import in.aptamitra.activities.LandingPageActivity;
        import in.aptamitra.activities.MainActivity;
        import in.aptamitra.activities.NotificationsListActivity;
        import in.aptamitra.activities.ProfileActivity;
>>>>>>> 2cf5a12ec48dcc2e2d178b08987784d957d1eaf5

/**
 * Created by abhishek on 20-07-2015.
 */
public class GlobalClass extends com.orm.SugarApp {
    public String mainMenu;
    public String subMenu;

    @Override
    public void onCreate() {
        super.onCreate();

    }


    public static Drawer navigationDrawer(final Activity activity) {
        /*
        get data from shared prefs::start
         */
        //SharedPreferences sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
        /*
        get data from shared prefs::end
         */
            /*
            readfrom shared prefs::start
             */
        Drawer result = null;
        SharedPreferences prefs = activity.getSharedPreferences("cache", Context.MODE_PRIVATE);
        String name = null;
        String email = null;
        try {
            JSONObject profile = new JSONObject(prefs.getString("profile", null));
            name = profile.getString("name");
            email = profile.getString("email");
            ProfileDrawerItem profileDrawerItem;
            if (!profile.getString("profile_image").trim().contentEquals("")) {
                String url = StringDecoder.decode(profile.getString("profile_image"));
                profileDrawerItem = new ProfileDrawerItem().withName(name).withEmail(email).withIcon(url);



                /*
                drawer profile icon::start
                 */
                DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
                    @Override
                    public void set(ImageView imageView, Uri uri, Drawable placeholder) {

                        Picasso.with(activity).load(uri).placeholder(R.drawable.lazy_loading).into(imageView);
                    }

                    @Override
                    public void cancel(ImageView imageView) {
                        //Picasso.with(imageView.getContext()).cancelRequest(imageView);
                    }

                    @Override
                    public Drawable placeholder(Context ctx) {
                        return null;
                    }
                });
                /*
                drawer profile icon::end
                 */

            } else {
                profileDrawerItem = new ProfileDrawerItem().withName(name).withEmail(email).
                        withIcon(activity.getResources().getDrawable(R.drawable.icon_profile));

            }


            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withHeaderBackground(R.drawable.material)
                    .addProfiles(
                            profileDrawerItem

                    )

                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {

                            Intent intent = new Intent(activity, ProfileActivity.class);
                            activity.startActivity(intent);
                            return true;
                        }
                    })
                    .build();


            result = new DrawerBuilder()
                    .withActivity(activity)
                            //.withToolbar(toolbar)
                    .withAccountHeader(headerResult)
                    .addDrawerItems(
                            new PrimaryDrawerItem().
                                    withName("Home").
                                    withIcon(activity.getResources().getDrawable(R.drawable.home)).
                                    withTextColor(activity.getResources().getColor(R.color.black))

                            ,
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("My Complaints").
                                    withIcon(activity.getResources().getDrawable(R.drawable.my_complaints)).
                                    withTextColor(activity.getResources().getColor(R.color.black))
                            ,
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("My Notifications").
                                    withIcon(activity.getResources().getDrawable(R.drawable.notification)).
                                    withTextColor(activity.getResources().getColor(R.color.black)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("Contact Us").
                                    withIcon(activity.getResources().getDrawable(R.drawable.call)).
                                    withTextColor(activity.getResources().getColor(R.color.black)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("Share").
                                    withIcon(activity.getResources().getDrawable(R.drawable.share)).
                                    withTextColor(activity.getResources().getColor(R.color.black)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("About Us").
                                    withIcon(activity.getResources().getDrawable(R.drawable.about_us)).
                                    withTextColor(activity.getResources().getColor(R.color.black)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("Privacy Policy").
                                    withIcon(activity.getResources().getDrawable(R.drawable.privacy)).
                                    withTextColor(activity.getResources().getColor(R.color.black)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("Terms and Conditions").
                                    withIcon(activity.getResources().getDrawable(R.drawable.terms_conditions)).
                                    withTextColor(activity.getResources().getColor(R.color.black)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("Logout").
                                    withIcon(activity.getResources().getDrawable(R.drawable.logout)).
                                    withTextColor(activity.getResources().getColor(R.color.black))

                            )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                            // do something with the clicked item :D
                            Intent intent;
                            switch (position) {
                                case 0:

                                    intent = new Intent(activity, LandingPageActivity.class);
                                    activity.startActivity(intent);
                                    break;
                                case 2:
                                    intent = new Intent(activity, ComplaintsListActivity.class);
                                    activity.startActivity(intent);
                                    break;
                                case 4:
                                    intent = new Intent(activity, NotificationsListActivity.class);
                                    activity.startActivity(intent);
                                    break;
                                case 6:
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:" + "08046665666"));
                                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    activity.startActivity(callIntent);
                                    break;
                                case 8:
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);

                                    shareIntent.setType("text/plain");
                                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Aptamitra");
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out Aptamitra. Make reservations, find products & more with just a " +
                                            "message. Chat with your local stores now - " +
                                            "http://www.genieline.com/app/org_hemorvichampaneria_quadvision_mayur_Aptamitra.apk");
                                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    activity.startActivity(Intent.createChooser(shareIntent, "Share Aptamitra"));
                                    break;
                                case 10:
                                    activity.getSharedPreferences("cache", MODE_PRIVATE).edit().clear();
                                    intent = new Intent(activity, AboutusActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;

                                case 12:
                                    activity.getSharedPreferences("cache", MODE_PRIVATE).edit().clear();
                                    intent = new Intent(activity, PrivacyActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                                case 14:
                                    activity.getSharedPreferences("cache", MODE_PRIVATE).edit().clear();
                                    intent = new Intent(activity, TermsActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                                case 16:
                                    activity.getSharedPreferences("cache", MODE_PRIVATE).edit().clear();
                                    intent = new Intent(activity, MainActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                                case 18:
                                    break;
                            }
                            return true;
                        }
                    })
                    .withDrawerGravity(Gravity.START)
                    .build();

            return result;


        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }


    }


}
