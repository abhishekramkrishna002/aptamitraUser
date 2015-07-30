/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package services;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.internal.zzhl;

import org.json.JSONObject;

import java.util.Random;

import entities.MyNotification;
import in.aptamitra.R;
import in.aptamitra.activities.ChatActivity;
import in.aptamitra.activities.LandingPageActivity;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d("data from gcm", data.toString());
        final String message = data.getString("message");
        String title = data.getString("title");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        try {
            final JSONObject gcmSentMessage = new JSONObject(message);
            if (gcmSentMessage.has("from_id")) {
                // return;
            } else if (gcmSentMessage.has("to_profile_id")) {
                /*
                update the adapter ::start
                 */
                ChatActivity.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LayoutInflater layoutInflater = LayoutInflater.from(ChatActivity.activity);
                            View view = layoutInflater.inflate(R.layout.chat_list_item_2, ChatActivity.chatListView, false);
                            ((TextView) view.findViewById(R.id.message)).setText(new JSONObject(message).getJSONObject("message").getString("data"));
                            ChatActivity.chatListView.addView(view);
                            Log.d("message", "a new message inside ui thread");
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                Log.d("message", "a new message");

                // return;

                /*
                update the adapter ::send
                 */
            } else {
                MyNotification myNotification = new MyNotification(message);
                myNotification.save();

                sendNotification(message, title);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */
        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */


    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message, String title) {
        Intent intent = new Intent(this, LandingPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.BigPictureStyle notiStyle = new
                NotificationCompat.BigPictureStyle();
        notiStyle.setBigContentTitle(title);
        notiStyle.setSummaryText(message);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setStyle(notiStyle)
                .setSmallIcon(R.drawable.icon_notification_aptamitra)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
//
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_view);
//        contentView.setTextViewText(R.id.title,title);
//        contentView.setTextViewText(R.id.description,message);
//
//
//
//
//        int icon = R.drawable.logo;
//        long when = System.currentTimeMillis();
//        Notification notification = new Notification(icon, title, when);
//
//        notification.bigContentView=contentView;
//
//        notification.contentIntent=pendingIntent;
//        notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
//        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
//        notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
//        notification.defaults |= Notification.DEFAULT_SOUND; // Sound


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        notificationManager.notify(new Random().nextInt(5000) /* ID of notification */, notificationBuilder.build());
    }
}
