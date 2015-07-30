package in.aptamitra.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import entities.MyNotification;
import globalclass.GlobalClass;
import in.aptamitra.R;


public class NotificationsListActivity extends ActionBarActivity {

    @Bind(R.id.notifications_container)
    LinearLayout notificationsContainer;
    private LayoutInflater layoutInflater;
    Drawer drawer;
    @Bind(R.id.app_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_list);
        ButterKnife.bind(this);
        drawer = ((GlobalClass) getApplicationContext()).navigationDrawer(this);
        layoutInflater = LayoutInflater.from(this);

        /*
        read db and update::start
         */
        try {
            List<MyNotification> notificationList = MyNotification.listAll(MyNotification.class);
            for (int position = 0; position < notificationList.size(); position++) {
                MyNotification myNotification = notificationList.get(position);
                JSONObject complaintJsonObject=new JSONObject(myNotification.json);
                View row = layoutInflater.inflate(R.layout.notification_item, notificationsContainer, false);
                ((TextView) row.findViewById(R.id.title)).setText(complaintJsonObject.getString("complaint_title"));
                ((TextView) row.findViewById(R.id.description)).setText(complaintJsonObject.getString("description"));
                Log.d("notification", myNotification.json);
                notificationsContainer.addView(row);
            }
        }
        catch(Exception e)
        {
         e.printStackTrace();
        }

        /*
        read db and update::end
         */

        Log.d("notifications", MyNotification.listAll(MyNotification.class).size() + "");
        ImageView imageView = (ImageView) toolbar.findViewById(R.id.icon_navigation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
