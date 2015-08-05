package globalclass;


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
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

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
import in.aptamitra.activities.RegisterComplaintActivity;
import views.CircleImageView;

/**
 * Created by abhishek on 20-07-2015.
 */
public class GlobalClass extends com.orm.SugarApp {
    public String mainMenu;
    public String subMenu;
    public ArrayList<String> searches;
    public HashMap<String, Speciality[]> services = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        setupuniversalLoader();
        init();

    }

    public void init() {
        Speciality[] bbmpService = {

                new Speciality("Road", false),
                new Speciality("footpath", false),
                new Speciality("drainage", false),
                new Speciality("dead bird", false),
                new Speciality("dead animal", false),
                new Speciality("mosquitoes", false),
                new Speciality("Road Damage", false),
                new Speciality("Storm water drainage", false),
                new Speciality("Potholes", false),
                new Speciality("Site Cleaning", false),
                new Speciality("Road asphalt", false),
                new Speciality("incomplete road repairs", false),
                new Speciality("Pavement stone slab", false),
                new Speciality("Debris of buildings", false),
                new Speciality("Storm water drainage", false),
                new Speciality("Site Cleaning", false),
                new Speciality("Pavement is broken", false),
                new Speciality("play ground maintenance", false),
                new Speciality("Food adulteration", false),
                new Speciality("Shops Licenses", false),
                new Speciality("Street Vendor Management", false),
                new Speciality("Food vendor on footpath", false),
                new Speciality("dog menace", false),
                new Speciality("stray animals", false),
                new Speciality("garbage", false),
                new Speciality("Remove tree litter", false),
                new Speciality("illegal banners", false),
                new Speciality("illegal advertisments", false),
                new Speciality("Property tax", false),
                new Speciality("voter id", false),
                new Speciality("khatha transfer", false),
                new Speciality("banner permissions", false),
                new Speciality("Park Maintainence", false),
                new Speciality("Divider plants maintainence", false),
                new Speciality("Street Light Maintainence", false),
                new Speciality("switch for street light", false),
                new Speciality("Timer for streetlight", false),
                new Speciality("Tree cutting", false),
                new Speciality("Tree pruning", false),
                new Speciality("Tree saplings plantation", false),
                new Speciality("Tree Maintainence", false),
                new Speciality("broken tree", false)
        };

        services.put("B B M P", bbmpService);
            /*
            load the bbmp specs::end
             */
            /*
            load the bwssb specs::start
             */
        Speciality[] bwssbService = {
                new Speciality("Blocked Storm Water drain", false),
                new Speciality("overflow of storm water drain", false),
                new Speciality("Water supply ", false),
                new Speciality("Under ground drainage", false),
                new Speciality("Meter", false),
                new Speciality("New connection", false),
                new Speciality("Billing complaints", false),
                new Speciality("Pipe damage", false),
                new Speciality("Sanitary manhole damage", false),
                new Speciality("sanitary pipe damage", false),
                new Speciality("water pipe damage", false),
                new Speciality("Sewerage", false),
                new Speciality("Other", false),
                new Speciality("Sanitary blocked", false),
                new Speciality("Sanitary manhole cover damage", false),
                new Speciality("Sanitary manholecover missing", false),
                new Speciality("Sanitary obstruction to traffic", false),
                new Speciality("Sanitary overflow", false),
                new Speciality("Sanitary road damage by BWSSB", false),
                new Speciality("Water", false),
                new Speciality("Contaminated water", false),
                new Speciality("Low water pressure", false),
                new Speciality("night time water release", false),
                new Speciality("No water", false),
                new Speciality("Request to change water timing", false),
                new Speciality("Water leakage", false)

        };
        services.put("B W S S B", bwssbService);
            /*
            load the bbmo specs::end
             */

         /*
            load the bescom specs::start
             */
        Speciality[] bescomService = {
                new Speciality("Failure of power supply", false),
                new Speciality("Fuse of call, Line Breakdown, Pole Broken", false),
                new Speciality("Voltage", false),
                new Speciality("Voltage variation where no expantion or enhancement of network is involved", false),
                new Speciality("Voltage variation where up-gradation of distribution systemis required", false),
                new Speciality("Opening of neutral", false),
                new Speciality("Meter complaints", false),
                new Speciality("Inspect and check correctness", false),
                new Speciality("Replace slow creeping or stuck meters", false),
                new Speciality("Replace Burnt meters if cause not attributable to consumer", false),
                new Speciality("Replace burnt meter in all other places", false),
                new Speciality("Billing issues", false),
                new Speciality("Where field report is not requires", false),
                new Speciality("allegations on staff", false),
                new Speciality("Where field report is required", false),
                new Speciality("Reconnection of supply following disconnection", false),
                new Speciality("Safety issue", false),
                new Speciality("Straighting of bent pole", false),
                new Speciality("Replacement of damaged pole", false),
                new Speciality("Shifting of poles", false),
                new Speciality("Tree trimming", false),
                new Speciality("TC Failure complaints", false),
                new Speciality("Water supply", false),
                new Speciality("domestic", false),
                new Speciality("industry", false),
                new Speciality("mixed load", false),
                new Speciality("irrigation", false),
                new Speciality("Theft", false),
                new Speciality("Hooking under niranthara jothiyojane", false),
                new Speciality("Allegations on staff", false),
                new Speciality("New connection/additional load", false),
                new Speciality("Release of supply where service is feasible from existing network", false),
                new Speciality("Release of supply where network expansion/enhancement required for providing connection", false),
                new Speciality("IP sets", false),
                new Speciality("Phase conversion", false),
                new Speciality("Conversion of LT single phase to LT 3 phase", false),
                new Speciality("Conversion from LT to HT", false),
                new Speciality("Transfer of ownership and conversion", false),
                new Speciality("Title transfer of ownership", false),
                new Speciality("Change of category", false),
                new Speciality("Refund/ issue of certificate", false),
                new Speciality("Refund of deposits", false),
                new Speciality("Issue of certificate", false),
                new Speciality("Additional TC / Enhancement", false),
                new Speciality("Additional TC", false),
                new Speciality("Enhancement of TC", false),
                new Speciality("General", false),
                new Speciality("Complaints that is not covered under the above category", false)

        };
        services.put("B E S C O M", bescomService);
        Speciality[] bmtcService = new Speciality[]{

                new Speciality("RESTART BMTC ROUTE ", false),
                new Speciality("Issues with a Bus Route", false),
                new Speciality("Bus Timing Issue", false),
                new Speciality("Bus Frequency", false),
                new Speciality("Lost and Found", false),
                new Speciality("Bus Staff Behaviour", false),
                new Speciality("Bus Condition", false),
                new Speciality("Bus Cleanliness", false),
                new Speciality("Bus Pass Issues", false)
        };
        services.put("B M T C", bmtcService);

        Speciality[] ambulanceServices = new Speciality[]{

                new Speciality("Request for An Ambulance service ", false)
        };
        services.put("Ambulance", ambulanceServices);
        Speciality[] fireServices = new Speciality[]{

                new Speciality("Report a Fire issue ", false)
        };
        services.put("Fire", fireServices);
        Speciality[] mortuaryServices = new Speciality[]{

                new Speciality("Need Mortuary Vehicle ", false),
                new Speciality("Need cremation centre address ", false),
                new Speciality("Book a time slot for nearest cremation centre ", false),
                new Speciality("Time extension for cremation", false)
        };
        services.put("Mortuary", mortuaryServices);
        Speciality[] lawAndOrderServices = new Speciality[]{

                new Speciality("Eve Teasing", false),
                new Speciality("Murder", false),
                new Speciality("theft", false),
                new Speciality("neighbour conflicts", false)
        };
        services.put("Law & Order", lawAndOrderServices);
        Speciality[] bloodServices = new Speciality[]{

                new Speciality("Need B+Ve", false),
                new Speciality("Need B-ve", false),
                new Speciality("Need O+ve", false),
                new Speciality("Need O-ve", false),
                new Speciality("Need AB+", false),
                new Speciality("Need AB-ve", false),
                new Speciality("Need A+ve", false),
                new Speciality("Need A-ve", false),
                new Speciality("Enrol Me as a Donor", false)
        };
        services.put("Blood", bloodServices);


            /*
            load the bescom specs::end
             */

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
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                                .cacheOnDisc(true).resetViewBeforeLoading(true).build();
                        imageLoader.displayImage(String.valueOf(uri), imageView, options);
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
                profileDrawerItem = new ProfileDrawerItem().withName(name).withEmail(email).withIcon(activity.getResources().getDrawable(R.drawable.icon_profile));

            }


            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(activity)
                    .withHeaderBackground(R.drawable.icon_profile_bg)
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
                                    withIcon(activity.getResources().getDrawable(R.drawable.icon_home)).
                                    withTextColor(activity.getResources().getColor(R.color.white))
                            ,
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("My Complaints").
                                    withIcon(activity.getResources().getDrawable(R.drawable.icon_post_cpmplaint)).
                                    withTextColor(activity.getResources().getColor(R.color.white))
                            ,
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("My Notifications").
                                    withIcon(activity.getResources().getDrawable(R.drawable.icon_notification)).
                                    withTextColor(activity.getResources().getColor(R.color.white)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("Contact Us").
                                    withIcon(activity.getResources().getDrawable(R.drawable.icon_contact_us)).
                                    withTextColor(activity.getResources().getColor(R.color.white)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("FAQ").
                                    withIcon(activity.getResources().getDrawable(R.drawable.icon_faq)).
                                    withTextColor(activity.getResources().getColor(R.color.white)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("About Us").
                                    withIcon(activity.getResources().getDrawable(R.drawable.icon_faq)).
                                    withTextColor(activity.getResources().getColor(R.color.white)),
                            new DividerDrawerItem(),
                            new PrimaryDrawerItem().
                                    withName("Logout").
                                    withIcon(activity.getResources().getDrawable(R.drawable.icon_logout)).
                                    withTextColor(activity.getResources().getColor(R.color.white)),
                            new DividerDrawerItem()

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
                                    break;
                                case 10:
                                    activity.getSharedPreferences("cache", MODE_PRIVATE).edit().clear();
                                    intent = new Intent(activity, AboutusActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                                case 12:
                                    activity.getSharedPreferences("cache", MODE_PRIVATE).edit().clear();
                                    intent = new Intent(activity, MainActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                                case 14:
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

    public void setupuniversalLoader() {
        // UNIVERSAL IMAGE LOADER SETUP::start
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // UNIVERSAL IMAGE LOADER SETUP::end

    }
}
