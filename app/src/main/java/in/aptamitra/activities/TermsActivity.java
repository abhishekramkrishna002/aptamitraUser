package in.aptamitra.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

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
public class TermsActivity extends ActionBarActivity {

    TermsListAdapter mAdapter;
    List<String> _listDataHeader;
    HashMap<String, List<String>> _listDataChild;
    ExpandableListView lv;
    Context con;

    @Bind(R.id.app_bar)
    Toolbar toolbar;
    Drawer drawer;
    private LayoutInflater layoutInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms);

        drawer=((GlobalClass)getApplicationContext()).navigationDrawer(this);
        layoutInflater = LayoutInflater.from(this);
        ImageView back = (ImageView) findViewById(R.id.icon_navigation);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer();
            }
        });

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
        _listDataHeader.add("Terms and Conditions");
        _listDataHeader.add("Services overview");
        _listDataHeader.add("License & Site access");
        _listDataHeader.add("Account & Registration Obligations");
        _listDataHeader.add("Pricing");
        _listDataHeader.add("Cancellation by Site / Customer");
        _listDataHeader.add("Return & Refunds");
        _listDataHeader.add("Colours");
        _listDataHeader.add("Modification of Terms & Conditions of Service");
        _listDataHeader.add("Governing Law and Jurisdiction");
        _listDataHeader.add("Reviews, Feedback, Submissions");
        _listDataHeader.add("Copyright & Trademark");
        _listDataHeader.add("Objectionable Material");
        _listDataHeader.add("Indemnity");
        _listDataHeader.add("Termination");



        // Adding child data
        List<String> terms = new ArrayList<String>();
        terms.add("Aptamitra Consulting Pvt Ltd (\"APCL\") is the licensed owner of the brand aptamitra.in and the website aptamitra.in (\"The Site\"). ACPL respects your privacy. This Privacy Policy provides succinctly the manner your data is collected and used by ACPL on the Site. As a visitor to the Site/ Customer you are advised to please read the Privacy Policy carefully. By accessing the services provided by the Site you agree to the collection and use of your data by ACPL in the manner provided in this Privacy Policy. ");

        List<String> services = new ArrayList<String>();
        services.add("As part of the registration process on the Site, ACPL may collect the following personally identifiable information about you: Name including first and last name, alternate email address, mobile phone number and contact details, Postal code, Demographic profile (like your age, gender, occupation, education, address etc.) and information about the pages on the site you visit/access, the links you click on the site, the number of times you access the page and any such browsing information.\n\n" +
                "Eligibility Services of the Site would be available to only select geographies in India. Persons who are \"incompetent to contract\" within the meaning of the Indian Contract Act, 1872 including un-discharged insolvents etc. are not eligible to use the Site. If you are a minor i.e. under the age of 18 years but at least 13 years of age you may use the Site only under the supervision of a parent or legal guardian who agrees to be bound by these Terms of Use. If your age is below 18 years your parents or legal guardians can transact on behalf of you if they are registered users. You are prohibited from purchasing any material which is for adult consumption and the sale of which to minors is prohibited");

        List<String> license = new ArrayList<String>();
        license.add("ACPL grants you a limited sub-license to access and make personal use of this site and not to download (other than page caching) or modify it, or any portion of it, except with express written consent of ACPL. This license does not include any resale or commercial use of this site or its contents; any collection and use of any product listings, descriptions, or prices; any derivative use of this site or its contents; any downloading or copying of account information for the benefit of another merchant; or any use of data mining, robots, or similar data gathering and extraction tools. This site or any portion of this site may not be reproduced, duplicated, copied, sold, resold, visited, or otherwise exploited for any commercial purpose without express written consent of ACPL. You may not frame or utilize framing techniques to enclose any trademark, logo, or other proprietary information (including images, text, page layout, or form) of the Site or of ACPL and its affiliates without express written consent. You may not use any meta tags or any other \"hidden text\" utilizing the Sites or ACPLs name or trademarks without the express written consent of ACPL. Any unauthorized use terminates the permission or license granted by ACPL.");

        List<String> account = new ArrayList<String>();
        account.add("All shoppers have to register and login for placing orders on the Site. You have to keep your account and registration details current and correct for communications related to your purchases from the site. By agreeing to the terms and conditions, the shopper agrees to receive promotional communication and newsletters upon registration. The customer can opt out either by unsubscribing in \"My Account\" or by contacting the customer service.");

        List<String> pricing = new ArrayList<String>();
        pricing.add("All the products listed on the Site will be sold at MRP unless otherwise specified. The prices mentioned at the time of ordering will be the prices charged on the date of the delivery. Although prices of most of the products do not fluctuate on a daily basis but some of the commodities and fresh food prices do change on a daily basis. In case the prices are higher or lower on the date of delivery not additional charges will be collected or refunded as the case may be at the time of the delivery of the order.");

        List<String> cancellation = new ArrayList<String>();
        cancellation.add("You as a customer can cancel your order anytime up to the cut-off time of the slot for which you have placed an order by calling our customer service. In such a case we will refund any payments already made by you for the order. If we suspect any fraudulent transaction by any customer or any transaction which defies the terms & conditions of using the website, we at our sole discretion could cancel such orders. We will maintain a negative list of all fraudulent transactions and customers and would deny access to them or cancel any orders placed by them.");

        List<String> returnandrefund = new ArrayList<String>();
        returnandrefund.add("We have a \"no questions asked return and refund policy\" which entitles all our members to return the product at the time of delivery if due to some reason they are not satisfied with the quality or freshness of the product. We will take the returned product back with us and issue a credit note for the value of the return products which will be credited to your account on the Site. This can be used to pay your subsequent shopping bills. You Agree and Confirm:\n\n" +
                "1). That in the event that a non-delivery occurs on account of a mistake by you (i.e. wrong name or address or any other wrong information) any extra cost incurred by ACPL for redelivery shall be claimed from you.\n\n" +
                "2). That you will use the services provided by the Site, its affiliates, consultants and contracted companies, for lawful purposes only and comply with all applicable laws and regulations while using and transacting on the Site.\n\n" +
                "3). You will provide authentic and true information in all instances where such information is requested of you. ACPL reserves the right to confirm and validate the information and other details provided by you at any point of time. If upon confirmation your details are found not to be true (wholly or partly), it has the right in its sole discretion to reject the registration and debar you from using the Services and / or other affiliated websites without prior intimation whatsoever.\n\n" +
                "4). That you are accessing the services available on this Site and transacting at your sole risk and are using your best and prudent judgment before entering into any transaction through this Site.\n\n" +
                "5). That the address at which delivery of the product ordered by you is to be made will be correct and proper in all respects.\n\n" +
                "6). That before placing an order you will check the product description carefully. By placing an order for a product you agree to be bound by the conditions of sale included in the item's description.\n\n\n" +
                "You may not use the Site for any of the following purposes:\n\n\n" +
                "1). Disseminating any unlawful, harassing, libellous, abusive, threatening, harmful, vulgar, obscene, or otherwise objectionable material.\n\n" +
                "2). Transmitting material that encourages conduct that constitutes a criminal offence or results in civil liability or otherwise breaches any relevant laws, regulations or code of practice.\n\n" +
                "3). Gaining unauthorized access to other computer systems.\n\n" +
                "4). Interfering with any other person's use or enjoyment of the Site.\n\n" +
                "5). Breaching any applicable laws.\n\n" +
                "6). Interfering or disrupting networks or web sites connected to the Site.\n\n" +
                "7). Making, transmitting or storing electronic copies of materials protected by copyright without the permission of the owner.");

        List<String> colours = new ArrayList<String>();
        colours.add("We have made every effort to display the colours of our products that appear on the Web site as accurately as possible. However, as the actual colours you see will depend on your monitor, we cannot guarantee that your monitor's display of any colour will be accurate.");

        List<String> modification = new ArrayList<String>();
        modification.add("ACPL may at any time modify the Terms & Conditions of Use of the Website without any prior notification to you. You can access the latest version of these Terms & Conditions at any given time on the Site. You should regularly review the Terms & Conditions on the Site. In the event the modified Terms & Conditions is not acceptable to you, you should discontinue using the Service. However, if you continue to use the Service you shall be deemed to have agreed to accept and abide by the modified Terms & Conditions of Use of this Site.");

        List<String> governing = new ArrayList<String>();
        governing.add("This User Agreement shall be construed in accordance with the applicable laws of India. The Courts at Bangalore shall have exclusive jurisdiction in any proceedings arising out of this agreement. Any dispute or difference either in interpretation or otherwise, of any terms of this User Agreement between the parties hereto, the same shall be referred to an independent arbitrator who will be appointed by ACPL and his decision shall be final and binding on the parties hereto. The above arbitration shall be in accordance with the Arbitration and Conciliation Act, 1996 as amended from time to time. The arbitration shall be held in Bangalore. The High Court of judicature at Bangalore alone shall have the jurisdiction and the Laws of India shall apply.");

        List<String> review = new ArrayList<String>();
        review.add("All reviews, comments, feedback, postcards, suggestions, ideas, and other submissions disclosed, submitted or offered to the Site on or by this Site or otherwise disclosed, submitted or offered in connection with your use of this Site (collectively, the \"Comments\") shall be and remain the property of ACPL. Such disclosure, submission or offer of any Comments shall constitute an assignment to ACPL of all worldwide rights, titles and interests in all copyrights and other intellectual properties in the Comments. Thus, ACPL owns exclusively all such rights, titles and interests and shall not be limited in any way in its use, commercial or otherwise, of any Comments. ACPL will be entitled to use, reproduce, disclose, modify, adapt, create derivative works from, publish, display and distribute any Comments you submit for any purpose whatsoever, without restriction and without compensating you in any way. ACPL is and shall be under no obligation (1) to maintain any Comments in confidence; (2) to pay you any compensation for any Comments; or (3) to respond to any Comments. You agree that any Comments submitted by you to the Site will not violate this policy or any right of any third party, including copyright, trademark, privacy or other personal or proprietary right(s), and will not cause injury to any person or entity. You further agree that no Comments submitted by you to the Website will be or contain libellous or otherwise unlawful, threatening, abusive or obscene material, or contain software viruses, political campaigning, commercial solicitation, chain letters, mass mailings or any form of \"spam\". ACPL does not regularly review posted Comments, but does reserve the right (but not the obligation) to monitor and edit or remove any Comments submitted to the Site. You grant ACPL the right to use the name that you submit in connection with any Comments. You agree not to use a false email address, impersonate any person or entity, or otherwise mislead as to the origin of any Comments you submit. You are and shall remain solely responsible for the content of any Comments you make and you agree to indemnify ACPL and its affiliates for all claims resulting from any Comments you submit. ACPL and its affiliates take no responsibility and assume no liability for any Comments submitted by you or any third party.");

        List<String> copyright = new ArrayList<String>();
        copyright.add("ACPL, its suppliers and licensors expressly reserve all intellectual property rights in all text, programs, products, processes, technology, content and other materials, which appear on this Site. Access to this Website does not confer and shall not be considered as conferring upon anyone any license under any of ACPL or any third party's intellectual property rights. All rights, including copyright, in this website are owned by or licensed to ACPL. Any use of this website or its contents, including copying or storing it or them in whole or part, other than for your own personal, non-commercial use is prohibited without the permission of ACPL. You may not modify, distribute or re-post anything on this website for any purpose. The names and logos and all related product and service names, design marks and slogans are the trademarks or service marks ACPL, its affiliates, its partners or its suppliers. All other marks are the property of their respective companies. No trademark or service mark license is granted in connection with the materials contained on this Site. Access to this Site does not authorize anyone to use any name, logo or mark in any manner. References on this Site to any names, marks, products or services of third parties or hypertext links to third party sites or information are provided solely as a convenience to you and do not in any way constitute or imply ACPL endorsement, sponsorship or recommendation of the third party, information, product or service. ACPL is not responsible for the content of any third party sites and does not make any representations regarding the content or accuracy of material on such sites. If you decide to link to any such third party websites, you do so entirely at your own risk. All materials, including images, text, illustrations, designs, icons, photographs, programs, music clips or downloads, video clips and written and other materials that are part of this Website (collectively, the \"Contents\") are intended solely for personal, non-commercial use. You may download or copy the Contents and other downloadable materials displayed on the Website for your personal use only. No right, title or interest in any downloaded materials or software is transferred to you as a result of any such downloading or copying. You may not reproduce (except as noted above), publish, transmit, distribute, display, modify, create derivative works from, sell or participate in any sale of or exploit in any way, in whole or in part, any of the Contents, the Website or any related software. All software used on this Website is the property of ACPL or its licensees and suppliers and protected by Indian and international copyright laws. The Contents and software on this Website may be used only as a shopping resource. Any other use, including the reproduction, modification, distribution, transmission, republication, display, or performance, of the Contents on this Website is strictly prohibited. unless otherwise noted, all Contents are copyrights, trademarks, trade dress and/or other intellectual property owned, controlled or licensed by ACPL, one of its affiliates or by third parties who have licensed their materials to ACPL and are protected by Indian and international copyright laws. The compilation (meaning the collection, arrangement, and assembly) of all Contents on this Website is the exclusive property of ACPL and is also protected by Indian and international copyright laws.");

        List<String> objectionable = new ArrayList<String>();
        objectionable.add("You understand that by using this Site or any services provided on the Site, you may encounter Content that may be deemed by some to be offensive, indecent, or objectionable, which Content may or may not be identified as such. You agree to use the Site and any service at your sole risk and that to the fullest extent permitted under applicable law, ACPL and its affiliates shall have no liability to you for Content that may be deemed offensive, indecent, or objectionable to you.");

        List<String> indemnity = new ArrayList<String>();
        indemnity.add("You agree to defend, indemnify and hold harmless ACPL, its employees, directors, officers, agents and their successors and assigns from and against any and all claims, liabilities, damages, losses, costs and expenses, including attorney's fees, caused by or arising out of claims based upon your actions or inactions, which may result in any loss or liability to ACPL or any third party including but not limited to breach of any warranties, representations or undertakings or in relation to the non-fulfilment of any of your obligations under this User Agreement or arising out of the your violation of any applicable laws, regulations including but not limited to Intellectual Property Rights, payment of statutory dues and taxes, claim of libel, defamation, violation of rights of privacy or publicity, loss of service by other subscribers and infringement of intellectual property or other rights. This clause shall survive the expiry or termination of this User Agreement.");

        List<String> termination = new ArrayList<String>();
        termination.add("This User Agreement is effective unless and until terminated by either you or ACPL. You may terminate this User Agreement at any time, provided that you discontinue any further use of this Site. ACPL may terminate this User Agreement at any time and may do so immediately without notice, and accordingly deny you access to the Site, Such termination will be without any liability to ACPL. Upon any termination of the User Agreement by either you or ACPL, you must promptly destroy all materials downloaded or otherwise obtained from this Site, as well as all copies of such materials, whether made under the User Agreement or otherwise. ACPL's right to any Comments shall survive any termination of this User Agreement. Any such termination of the User Agreement shall not cancel your obligation to pay for the product already ordered from the Website or affect any liability that may have arisen under the User Agreement.");


        //set to adoptor
        _listDataChild.put(_listDataHeader.get(0), terms); // Header, Child data
        _listDataChild.put(_listDataHeader.get(1), services);
        _listDataChild.put(_listDataHeader.get(2), license);
        _listDataChild.put(_listDataHeader.get(3), account);
        _listDataChild.put(_listDataHeader.get(4), pricing);
        _listDataChild.put(_listDataHeader.get(5), cancellation);
        _listDataChild.put(_listDataHeader.get(6), returnandrefund);
        _listDataChild.put(_listDataHeader.get(7), colours);
        _listDataChild.put(_listDataHeader.get(8), modification);
        _listDataChild.put(_listDataHeader.get(9), governing);
        _listDataChild.put(_listDataHeader.get(10), review);
        _listDataChild.put(_listDataHeader.get(11), copyright);
        _listDataChild.put(_listDataHeader.get(12), objectionable);
        _listDataChild.put(_listDataHeader.get(13), indemnity);
        _listDataChild.put(_listDataHeader.get(14), termination);
    }
}