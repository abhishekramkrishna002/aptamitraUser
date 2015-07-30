package adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import custom_objects.Speciality;
import globalclass.GlobalClass;
import in.aptamitra.R;
import in.aptamitra.activities.ComplaintSubTypeActivity;

/**
 * Created by Abhishek on 6/21/15.
 */
public class ComplaintSubTypeAdapter extends BaseAdapter implements Filterable {
    Activity activity;
    Speciality[] problems;
    Speciality[] cacheProblems;
    boolean[] problemsBoolean;
    String staticHeader;

    public ComplaintSubTypeAdapter(Activity activity, Speciality[] problems) {
        this.activity = activity;
        this.problems = problems;
        this.cacheProblems = problems;
        problemsBoolean = new boolean[problems.length];
        staticHeader = ComplaintSubTypeActivity.header.getText().toString();
    }

    @Override
    public int getCount() {
        if(problems!=null)
        {
            return problems.length;
        }
        return  0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        view = LayoutInflater.from(activity).inflate(R.layout.complaints_sub_type_item, parent, false);
        if (problemsBoolean[position]) {
            view.setBackgroundColor(activity.getResources().getColor(R.color.gray));
        } else {
            view.setBackgroundColor(activity.getResources().getColor(R.color.white));
        }
        ((TextView) view.findViewById(R.id.sub_type_text_view)).setText(problems[position].name.toLowerCase());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!problemsBoolean[position]) {
                    for (int j = 0; j < problems.length; j++) {

                        problemsBoolean[j] = false;
                        ComplaintSubTypeActivity.header.setText(staticHeader);
                        notifyDataSetChanged();
                    }
                    v.setBackgroundColor(activity.getResources().getColor(R.color.gray));
                    ComplaintSubTypeActivity.header.setText(ComplaintSubTypeActivity.header.getText() + problems[position].name);
                    ((GlobalClass) activity.getApplicationContext()).subMenu = problems[position].name;
                    problemsBoolean[position] = !problemsBoolean[position];
                }
            }
        });
        return view;

    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Speciality[] results= new Speciality[0];
                int j = 0;
                if (constraint.toString().trim().contentEquals("")) {
                    results = new Speciality[cacheProblems.length];
                    results = cacheProblems;
                    j=cacheProblems.length;
                } else {
                    results = new Speciality[cacheProblems.length];
                    for (int i = 0; i < cacheProblems.length; i++) {
                        if (cacheProblems[i].getName().toLowerCase().contains(constraint.toString().trim().toLowerCase())) {
                            results[j++] = cacheProblems[i];
                        }
                    }
                }
                if (j == 0) {
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = null;
                    Log.e("value::zero", results.length + "");
                    filterResults.count = 0;
                    return filterResults;
                } else {
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = results;
                    Log.e("value", results.length + "");
                    filterResults.count = results.length;
                    return filterResults;
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null && results.count > 0) {
                    Log.e("publish results",""+results.values);
                    problems = (Speciality[]) results.values;
                    notifyDataSetChanged();
                } else {
                    Log.e("publish results::zero",""+results.values);
                    problems = null;
                    notifyDataSetChanged();
                }
               // Log.e("problem", problems.length + "");
            }

        };
        return filter;
    }
}