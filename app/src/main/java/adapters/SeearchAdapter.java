package adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import globalclass.GlobalClass;
import in.aptamitra.R;
import in.aptamitra.activities.BookDeliveryActivity;
import in.aptamitra.activities.BookServiceActivity;
import in.aptamitra.activities.RegisterComplaintActivity;
import in.aptamitra.activities.ServiceListActivity;


/**
 * Created by abhishek on 02-07-2015.
 */
public class SeearchAdapter extends ArrayAdapter<String> implements Filterable {

    public List<String> searches = null;
    public List<String> cacheSearches = null;
    public SearchFilter specialityFilter = new SearchFilter();
    int itemLayout;
    Activity activity;

    public SeearchAdapter(Activity activity, int resource,
                          List<String> objects) {
        super(activity, resource, objects);
        this.searches = objects;
        this.cacheSearches = objects;
        this.itemLayout = resource;
        this.activity = activity;


        // TODO Auto-generated constructor stub
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return searches.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return searches.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = activity
                .getLayoutInflater();
        final View row = inflater.inflate(itemLayout, parent,
                false);
        try {
            String search = searches.get(position);

            String[] matches = search.split(">");

            ((TextView) row.findViewById(R.id.service_name))
                    .setText(matches[matches.length - 1]);
            /*
            set up appropriate click listener::start
             */
            final String[] results = search.trim().split(">");
            if (results[1].contains("civic_issues")) {
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GlobalClass globalClass = ((GlobalClass) activity.getApplicationContext());
                        globalClass.mainMenu = (results[2].toUpperCase());
                        globalClass.subMenu = (results[3]);
                        Intent intent = new Intent(activity, RegisterComplaintActivity.class);
                        intent.putExtra("data", "Civic Issues > " + results[2] + " > " + results[3]);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                });


            } else if (results[1].contains("services")) {
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((GlobalClass) activity.getApplicationContext()).mainMenu = ((TextView) row.findViewById(R.id.service_name)).getText().toString();
                        Intent intent = new Intent(activity, BookServiceActivity.class);
                        activity.startActivity(intent);
                        activity.finish();

                    }
                });

            } else if (results[1].contains("delivery")) {
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((GlobalClass) activity.getApplicationContext()).mainMenu = ((TextView) row.findViewById(R.id.service_name)).getText().toString();
                        Intent intent = new Intent(activity, BookDeliveryActivity.class);
                        activity.startActivity(intent);
                        activity.finish();

                    }
                });

            } else if (results[1].contains("my_aptamitra")) {
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((GlobalClass) activity.getApplicationContext()).mainMenu = ((TextView) row.findViewById(R.id.service_name)).getText().toString();
                        Intent intent = new Intent(activity, ServiceListActivity.class);
                        intent.putExtra("data",((GlobalClass) activity.getApplicationContext()).mainMenu.toLowerCase());
                        activity.startActivity(intent);
                        activity.finish();

                    }
                });

            }



            /*
            set up appropriate click listener::end
             */


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return row;
        }


    }

    private class SearchFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<String> values = new ArrayList<>();
            values.clear();
            for (int i = 0; i < cacheSearches.size(); i++) {
                String[] matches = cacheSearches.get(i).split(">");
                String data = matches[matches.length - 1];

                if (data.toLowerCase().contains(((constraint.toString()).toLowerCase()))) {
                    values.add(cacheSearches.get(i));
                    Log.d("search", cacheSearches.get(i));
                }
            }
            results.values = values;
            results.count = values.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (((String) constraint).trim().contentEquals("")) {
                searches = cacheSearches;
                notifyDataSetChanged();
            } else {
                if (results.count == 0) {
                    searches = new ArrayList<String>(0);
                    notifyDataSetChanged();
                } else if (results.count > 0) {
                    searches = (List<String>) results.values;
                    notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public Filter getFilter() {
        return (specialityFilter == null) ? new SearchFilter() : specialityFilter;
    }
}


