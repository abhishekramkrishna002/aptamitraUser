package adapters;

import android.app.Activity;
import android.content.Intent;
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
import in.aptamitra.activities.BookServiceActivity;
import in.aptamitra.activities.ChatActivityService;
import in.aptamitra.activities.RegisterComplaintActivity;


/**
 * Created by abhishek on 02-07-2015.
 */
public class BusinessListingAdapter extends ArrayAdapter<String> implements Filterable {

    public List<String> searches = null;
    public List<String> cacheSearches = null;
    public SearchFilter specialityFilter = new SearchFilter();
    int itemLayout;
    Activity activity;

    public BusinessListingAdapter(Activity activity, int resource,
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
            final String[] results = search.trim().split(">");
            Log.d("search-result-spilt", search);
            Log.d("search-result-spilt", results[1]);

            ((TextView) row.findViewById(R.id.service_name))
                    .setText(results[results.length - 3]);
            ((TextView) row.findViewById(R.id.service_address))
                    .setText(results[results.length - 2]);
            ((TextView) row.findViewById(R.id.service_phone))
                    .setText(results[results.length - 1]);


            /*
            set up appropriate click listener::start
             */
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(activity, ChatActivityService.class);
                    intent.putExtra("data", ((TextView) row.findViewById(R.id.service_name)).getText());
                    activity.startActivity(intent);

                }
            });


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
            for (int i = 0; i < cacheSearches.size(); i++) {
                String data = cacheSearches.get(i);
                if (data.toLowerCase().contains(((String) constraint).toLowerCase()) ||
                        ((String) constraint).toLowerCase().contains(data.toLowerCase())) {
                    values.add(data);
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
                if (results.count == 0)
                    notifyDataSetInvalidated();
                else if (results.count > 0) {
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


