package in.aptamitra.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.ChatListAdapter;
import async_tasks.ChatAsyncTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import custom_objects.ChatMessage;
import in.aptamitra.R;

/**
 * Created by abhishek on 25-07-2015.
 */
public class ChatActivityService extends ActionBarActivity {
     //@Bind(R.id.chat_list_container)
    public static LinearLayout chatListView;
//
//
//    @Bind(R.id.message_edit_text)
//    EditText messageEditText;
//
//    @Bind(R.id.service_name)
//    TextView serviceName;
//    @Bind(R.id.send_message)
//    ImageView sendImage;
//
//    private String title;
    public static Activity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coming_soon_layout);


//        setContentView(R.layout.chat_layout);
//        ButterKnife.bind(this);
//        activity = this;
//        title=getIntent().getStringExtra("data");
//        serviceName.setText(title);
//        chatListView = (LinearLayout) findViewById(R.id.chat_list_container);
//        ImageView back = (ImageView) findViewById(R.id.icon_navigation);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

//    @OnClick(R.id.send_message)
//    void sendMessage(View view) {
//        if (messageEditText.getText().toString().equals("")) {
//            Toast.makeText(getApplicationContext(), "Enter some Text", Toast.LENGTH_SHORT).show();
//        } else {
//            new ChatAsyncTask(activity).execute(messageEditText.getText().toString());
//            messageEditText.setText("");
//        }
//
//    }


}
