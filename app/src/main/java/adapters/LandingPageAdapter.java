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

import java.util.ArrayList;

import custom_objects.HorizontalScrollItem;
import globalclass.GlobalClass;
import in.aptamitra.R;
import in.aptamitra.activities.BookServiceActivity;
import in.aptamitra.activities.ChatActivity;
import in.aptamitra.activities.ComplaintSubTypeActivity;
import in.aptamitra.activities.RegisterActivity;

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
        if (position == 0) {
            for (int i = 0; i < items.size(); i++) {
//                View cardView = layoutInflater.inflate(
//                        R.layout.landing_page_horizontal_list_item_1,
//                        viewHolder.horizontalListContainer,
//                        false);
//                HorizontalScrollItem.Item item = items.get(i);
//                ((ImageView) cardView.findViewById(R.id.image)).setImageResource(item.image);
//                ((TextView) cardView.findViewById(R.id.description)).setText(item.description);
//                ((TextView) cardView.findViewById(R.id.shorthand)).setText(item.name);
//                viewHolder.horizontalListContainer.addView(cardView);
//                cardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String data = ((TextView) v.findViewById(R.id.shorthand)).getText().toString();
//                        //Toast.makeText(activity,data,Toast.LENGTH_LONG).show();
//                        startActivity(data);
//                    }
//                });
                View cardView = layoutInflater.inflate(R.layout.landing_page_horizontal_list_item_2, viewHolder.horizontalListContainer, false);
                HorizontalScrollItem.Item item = items.get(i);
                ((ImageView) cardView.findViewById(R.id.image)).setImageResource(item.image);
                ((TextView) cardView.findViewById(R.id.title)).setText(item.name);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // String data= ((TextView) v.findViewById(R.id.shorthand)).getText().toString();
                        //Toast.makeText(activity,data,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, ComplaintSubTypeActivity.class);
                        String data = ((TextView) v.findViewById(R.id.title)).getText().toString();
                        startActivity(data);
                    }
                });
                viewHolder.horizontalListContainer.addView(cardView);
            }
        } else if (position == 2) {
            for (int i = 0; i < items.size(); i++) {
                View cardView = layoutInflater.inflate(R.layout.landing_page_horizontal_list_item_2, viewHolder.horizontalListContainer, false);
                HorizontalScrollItem.Item item = items.get(i);
                ((ImageView) cardView.findViewById(R.id.image)).setImageResource(item.image);
                ((TextView) cardView.findViewById(R.id.title)).setText(item.name);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // String data= ((TextView) v.findViewById(R.id.shorthand)).getText().toString();
                        //Toast.makeText(activity,data,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity, ChatActivity.class);
                        activity.startActivity(intent);
                    }
                });
                viewHolder.horizontalListContainer.addView(cardView);
            }
        } else if (position == 1) {
            for (int i = 0; i < items.size(); i++) {
                View cardView = layoutInflater.inflate(R.layout.landing_page_horizontal_list_item_2, viewHolder.horizontalListContainer, false);
                HorizontalScrollItem.Item item = items.get(i);
                ((ImageView) cardView.findViewById(R.id.image)).setImageResource(item.image);
                ((TextView) cardView.findViewById(R.id.title)).setText(item.name);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((GlobalClass) activity.getApplicationContext()).mainMenu =
                                ((TextView) v.findViewById(R.id.title)).getText().toString();
                        Intent intent = new Intent(activity, BookServiceActivity.class);
                        activity.startActivity(intent);
                    }
                });
                viewHolder.horizontalListContainer.addView(cardView);
            }
        }


        /*
        add dynamic cards::end
         */
    }

    public void startActivity(String data) {
        Intent intent = new Intent(activity, ComplaintSubTypeActivity.class);
        ((GlobalClass) (activity.getApplicationContext())).mainMenu = data;
        intent.putExtra("data", data);
        activity.startActivity(intent);
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
