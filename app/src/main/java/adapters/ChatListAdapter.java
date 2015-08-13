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

import java.util.ArrayList;

import custom_objects.ChatMessage;
import custom_objects.Speciality;
import globalclass.GlobalClass;
import in.aptamitra.R;
import in.aptamitra.activities.ComplaintSubTypeActivity;

/**
 * Created by Abhishek on 6/21/15.
 */
public class ChatListAdapter extends BaseAdapter {
    Activity activity;
    public ArrayList<ChatMessage> messages;

    public ChatListAdapter(Activity activity, ArrayList<ChatMessage> messages) {
        this.activity = activity;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view;
        if (messages.get(position).userPosted) {
            view = LayoutInflater.from(activity).inflate(R.layout.chat_list_item_1, parent, false);
        } else {
            view = LayoutInflater.from(activity).inflate(R.layout.chat_list_item_2, parent, false);
            ;
        }


        ((TextView) view.findViewById(R.id.message)).setText(messages.get(position).message);
        if (messages.get(position).sent) {
            ((TextView) view.findViewById(R.id.message_status)).setText("sent");
        }
        // Log.d("messages",messages[position].toString());


        return view;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyDataSetChanged();
    }


}