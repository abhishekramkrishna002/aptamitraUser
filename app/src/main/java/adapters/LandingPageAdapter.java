package adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import custom_objects.HorizontalScrollItem;
import globalclass.GlobalClass;
import in.aptamitra.R;
import in.aptamitra.activities.BookDeliveryActivity;
import in.aptamitra.activities.BookServiceActivity;
import in.aptamitra.activities.ChatActivity;
import in.aptamitra.activities.ComplaintSubTypeActivity;
import in.aptamitra.activities.RegisterActivity;
import in.aptamitra.activities.ServiceListActivity;

/**
 * Created by abhishek on 18-07-2015.
 */
public class LandingPageAdapter
        extends RecyclerView.Adapter<LandingPageAdapter.MyViewHolder> {

    final String TAG = "adapter";
    ArrayList<HorizontalScrollItem> menuItems;
    Activity activity;

    public LandingPageAdapter(Activity activity, ArrayList<HorizontalScrollItem> menuItems) {
        this.activity = activity;
        this.menuItems = new ArrayList<>(menuItems.size());
        this.menuItems = menuItems;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.landing_page_item, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        viewHolder.menuNameTextView.setText(menuItems.get(position).title);
        /*
        add dynamic cards::start
         */

        ArrayList<HorizontalScrollItem.Item> items = menuItems.get(position).items;
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        int i;
        for (i = 0; i < items.size(); i++) {

            View cardView = layoutInflater.inflate(R.layout.landing_page_horizontal_list_item_2, viewHolder.horizontalListContainer, false);
            HorizontalScrollItem.Item item = items.get(i);
            /*
            load from server:start
             */
            Picasso.with(activity).load(item.image).placeholder(R.drawable.lazy_loading).into(((ImageView) cardView.findViewById(R.id.image)));
            // ((ImageView) cardView.findViewById(R.id.image)).setImageResource(item.image);
            /*
            load from server:end
             */
            ((TextView) cardView.findViewById(R.id.title)).setText(item.name);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (position == 0) {
                        intent = new Intent(activity, ComplaintSubTypeActivity.class);
                        String data = ((TextView) v.findViewById(R.id.title)).getText().toString().toLowerCase();
                        ((GlobalClass) (activity.getApplicationContext())).mainMenu = data;
                        intent.putExtra("data", data);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else if (position == 1) {
                        ((GlobalClass) activity.getApplicationContext()).mainMenu =
                                ((TextView) v.findViewById(R.id.title)).getText().toString();
                        intent = new Intent(activity, BookServiceActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else if (position == 2) {
//                        intent = new Intent(activity, ChatActivity.class);
//                        activity.startActivity(intent);
                        ((GlobalClass) activity.getApplicationContext()).mainMenu =
                                ((TextView) v.findViewById(R.id.title)).getText().toString();
                        intent = new Intent(activity, BookDeliveryActivity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else if (position == 3) {
                        intent = new Intent(activity, ServiceListActivity.class);
                        String data = ((TextView) v.findViewById(R.id.title)).getText().toString();
                        ((GlobalClass) (activity.getApplicationContext())).mainMenu = data;
                        intent.putExtra("data", data);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                    }


                }
            });
            viewHolder.horizontalListContainer.addView(cardView);
        }

        /*
        add dynamic cards::end
         */
    }



    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuNameTextView;
        LinearLayout horizontalListContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            menuNameTextView = (TextView) itemView.findViewById(R.id.menu_title);
            horizontalListContainer = (LinearLayout) itemView.findViewById(R.id.horizontal_items_container);
        }
    }
}
