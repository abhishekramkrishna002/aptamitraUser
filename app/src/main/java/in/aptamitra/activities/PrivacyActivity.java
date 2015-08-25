package in.aptamitra.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.TermsListAdapter;
import butterknife.Bind;
import globalclass.GlobalClass;
import in.aptamitra.R;

/**
 * Created by Hemorvi Champaneria on 31-07-2015.
 */
public class PrivacyActivity extends ActionBarActivity {

    TermsListAdapter mAdapter;
    List<String> _listDataHeader;
    HashMap<String, List<String>> _listDataChild;
    ExpandableListView lv;
    Context con;

    @Bind(R.id.app_bar)
    Toolbar toolbar;
    Drawer drawer;
    private LayoutInflater layoutInflater;
    TextView titletextview;
    Typeface face, face2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy);
        drawer=((GlobalClass)getApplicationContext()).navigationDrawer(this);
        layoutInflater = LayoutInflater.from(this);
        ImageView back = (ImageView) findViewById(R.id.icon_navigation);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titletextview=(TextView)findViewById(R.id.title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "trajan_pro_bold.otf");
        titletextview.setTypeface(typeface);

        lv = (ExpandableListView) findViewById(R.id.lvExp);

        //here setting all the values to Parent and child classes
        //      setDataValues();
        prepareListData();//here get the values and set this values to adoptor and set it visible
        con=getApplicationContext();

        mAdapter=new TermsListAdapter(con,_listDataHeader, _listDataChild) ; //here i didnt set list values to this adoptor

        // mAdapter = new ExpandableListAdapter(this, _listDataHeader, _listDataChild);

        // setting list adapter
        lv.setAdapter(mAdapter);

    }

    public void prepareListData()
    {
        // testing purpose
        _listDataHeader = new ArrayList<String>();
        _listDataChild = new HashMap<String, List<String>>();


        // Adding child data
        _listDataHeader.add("Your personal information");
        _listDataHeader.add("What information is/may be collected from you?");
        _listDataHeader.add("How do we collect the your information?");
        _listDataHeader.add("How do we use your information?");
        _listDataHeader.add("With whom will your information be shared");
        _listDataHeader.add("How do we safeguard your information?");
        _listDataHeader.add("How can you update/correct inaccuracies in your information?");
        _listDataHeader.add("Policy updates");



        // Adding child data
        List<String> pinfo = new ArrayList<String>();
        pinfo.add("Aptamitra Consulting Pvt. Ltd. (henceforth referred to as ACPL) is the licensed owner of the brand" +
                " Aptamitra and the website Aptamitra.in (henceforth referred to as 'our site' or 'the site'). " +
                "ACPL respects your privacy. This Privacy Policy succinctly outlines the manner in which your data is " +
                "collected and used by ACPL. As a visitor to the site or when you get in touch with our call center, you are " +
                "advised to please read the Privacy Policy carefully. By accessing the services provided by our site you agree " +
                "to the collection and use of your data by ACPL in the manner provided in this Privacy Policy.");

        List<String> infocollected = new ArrayList<String>();
        infocollected.add("As part of the registration process on the site, ACPL may collect the following personally identifiable " +
                "information about you: Name including first and last name, email address, mobile phone number and contact details, " +
                "Postal code, Demographic profile (like your age, gender, occupation, education, address etc.) and information about " +
                "the pages on the site you visit/access, your clicks on the site, the number of times you access the page and any such " +
                "browsing information.");

        List<String> yourinfo = new ArrayList<String>();
        yourinfo.add("ACPL will collect personally identifiable information about you only as part of a voluntary registration process, " +
                "on-line survey or any combination thereof. The site may contain links to other Web sites. ACPL is not responsible for the " +
                "privacy practices of such Web sites which it does not own, manage or control. The site and third-party vendors, including " +
                "Google, use first-party cookies (such as the Google Analytics cookie) and third-party cookies (such as the DoubleClick cookie) " +
                "together to inform, optimize, and serve ads based on someone's past visits to the Site.");

        List<String> useyourinfo = new ArrayList<String>();
        useyourinfo.add("ACPL will use your personal information to provide personalized features to you on the Site and to provide " +
                "for promotional offers to you through the Site and other channels. ACPL will also provide this information to its " +
                "business associates and partners to get in touch with you when necessary to provide the services requested by you. " +
                "ACPL will use this information to preserve transaction history as governed by existing law or policy. ACPL may also " +
                "use contact information internally to direct its efforts for product improvement, to contact you as a survey respondent," +
                " to notify you if you win any contest; and to send you promotional materials from its contest sponsors or advertisers. " +
                "ACPL will also use this information to serve various promotional and advertising materials to you via display advertisements " +
                "through the Google Ad network on third party websites. You can opt out of Google Analytics for Display Advertising and customize " +
                "Google Display network ads using the Ads Preferences Manager. Information about Customers on an aggregate (excluding any " +
                "information that may identify you specifically) covering Customer transaction data and Customer demographic and location " +
                "data may be provided to partners of ACPL for the purpose of creating additional features on the website, creating appropriate " +
                "merchandising or creating new products and services and conducting marketing research and statistical analysis of customer " +
                "behavior and transactions.");

        List<String> yourinfoshared = new ArrayList<String>();
        yourinfoshared.add("ACPL will not use your information for any financial purpose other than to complete a transaction with you. " +
                "ACPL does not rent, sell or share your personal information and will not disclose any of your personally identifiable " +
                "information to third parties. In cases where it has your permission to provide products or services you've requested and " +
                "such information is necessary to provide these products or services the information may be shared with ACPLs business " +
                "associates and partners. ACPL may, however, share consumer information on an aggregate with its partners or third parties " +
                "where it deems necessary. In addition, ACPL may use this information for promotional offers, to help investigate, prevent " +
                "or take action regarding unlawful and illegal activities, suspected fraud, potential threat to the safety or security of any " +
                "person, violations of the sites terms of use or to defend against legal claims; special circumstances such as compliance with " +
                "subpoenas, court orders, requests/order from legal authorities or law enforcement agencies requiring such disclosure.");

        List<String> safeguardyourinfo = new ArrayList<String>();
        safeguardyourinfo.add("To protect against the loss, misuse and alteration of the information under its control, ACPL has in place " +
                "appropriate physical, electronic and managerial procedures. For example, ACPL servers are accessible only to authorized " +
                "personnel and your information is shared with employees and authorized personnel on a need to know basis to complete the " +
                "transaction and to provide the services requested by you. Although ACPL will endeavor to safeguard the confidentiality of " +
                "your personally identifiable information, transmissions made by means of the Internet cannot be made absolutely secure. By " +
                "using this site, you agree that ACPL will have no liability for disclosure of your information due to errors in transmission " +
                "or unauthorized acts of third parties.");

        List<String> updateyourinfo = new ArrayList<String>();
        updateyourinfo.add("To correct or update the information you have provided, login to our site and change the details as required in " +
                "'My Profile' under the 'My Account' tab. In the event of loss of access details you can send an e-mail to: support@aptamitra.in");

        List<String> policyupdate = new ArrayList<String>();
        policyupdate.add("ACPL reserves the right to change or update this policy at any time. Such changes shall be effective immediately " +
                "upon posting to the site.");



        //set to adoptor
        _listDataChild.put(_listDataHeader.get(0), pinfo); // Header, Child data
        _listDataChild.put(_listDataHeader.get(1), infocollected);
        _listDataChild.put(_listDataHeader.get(2), yourinfo);
        _listDataChild.put(_listDataHeader.get(3), useyourinfo);
        _listDataChild.put(_listDataHeader.get(4), yourinfoshared);
        _listDataChild.put(_listDataHeader.get(5), safeguardyourinfo);
        _listDataChild.put(_listDataHeader.get(6), updateyourinfo);
        _listDataChild.put(_listDataHeader.get(7), policyupdate);

    }
}