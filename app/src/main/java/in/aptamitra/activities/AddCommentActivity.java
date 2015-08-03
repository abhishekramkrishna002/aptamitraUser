package in.aptamitra.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import async_tasks.AddCommentsAsyncTask;
import in.aptamitra.R;

/**
 * Created by fasal on 31-07-2015.
 */
public class AddCommentActivity extends ActionBarActivity {
    HashMap<String, String> params;
    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }
    private int  complaintId;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment);
        params = new HashMap<String, String>();
        Button add = (Button) findViewById(R.id.add_comment);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = ((EditText) findViewById(R.id.etMessage)).getText().toString();
                try {
                    text= URLEncoder.encode(text, "UTF-8");
                    params.put("text", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                params.put("complaint_id", complaintId+"");
                params.put("profile_id", ComplaintsListActivity.profile_id);


                Log.e("text", text);

                Log.e("profile_id", ComplaintsListActivity.profile_id);
                Log.e("complaint_id", complaintId+"");
                AddCommentsAsyncTask addAsyncTask = new AddCommentsAsyncTask(AddCommentActivity.this);

                addAsyncTask.execute(params);
            }
        });
    }
}