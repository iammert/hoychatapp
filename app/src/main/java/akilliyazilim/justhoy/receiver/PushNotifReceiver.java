package akilliyazilim.justhoy.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.activity.HoyApp;
import akilliyazilim.justhoy.activity.MessagingActivity;
import akilliyazilim.justhoy.model.ConversationInfo;
import akilliyazilim.justhoy.model.MessageText;
import akilliyazilim.justhoy.sqlite.DBHelper;
import akilliyazilim.justhoy.sqlite.SeperateMessageTable;

/**
 * Created by mertsimsek on 16.08.2014.
 */
public class PushNotifReceiver extends BroadcastReceiver {
    private static final String TAG = "PushNotifReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.d(TAG, "got action " + action + " on channel " + channel + " with:");

            String my_id = json.getString("stranger_id");
            String stranger_id = json.getString("my_id");
            String my_image_url = json.getString("stranger_image_url");
            String stranger_image_url = json.getString("my_image_url");
            String my_name = json.getString("stranger_name");
            String stranger_name = json.getString("my_name");

            MessageText received_message = new MessageText();
            received_message.setMessage_text(json.getString("message_text"));
            received_message.setMy_name(my_name);
            received_message.setMy_id(my_id);
            received_message.setMy_image_url(my_image_url);
            received_message.setStranger_id(stranger_id);
            received_message.setStranger_image_url(stranger_image_url);
            received_message.setStranger_name(stranger_name);

            ConversationInfo conversation = new ConversationInfo();
            conversation.setStranger_name(received_message.getStranger_name());
            conversation.setStranger_image_url(received_message.getStranger_image_url());
            conversation.setStranger_id(received_message.getStranger_id());
            conversation.setIsUnread("unread");

            DBHelper helper =  new DBHelper(context);
            SeperateMessageTable messagingtable = helper.getSeperateMessageTableGateway(stranger_id);

            if(messagingtable.getAllMessagingMessages().size()>0)
                helper.getConversationTableGateway().updateConversation(conversation);
            else
                helper.getConversationTableGateway().insertConversation(conversation);

            helper.getSeperateMessageTableGateway(received_message.getStranger_id()).insertMessage(received_message,"2");
            if(SeperateMessageTable.listener != null)
                SeperateMessageTable.notifyListener();

            if(!HoyApp.isActivityVisible())
                displayNotification(context,received_message);

        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }

    private void displayNotification(Context context,MessageText message) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MessagingActivity.class);
        notificationIntent.putExtra("stranger_name",message.getStranger_name());
        notificationIntent.putExtra("stranger_image_url",message.getStranger_image_url());
        notificationIntent.putExtra("stranger_id",message.getStranger_id());
        notificationIntent.putExtra("message_unread","read");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] vibrate = { 0, 100, 200, 300 };
        Notification notification = new Notification.Builder(context).setContentTitle(message.getStranger_name()).setContentText(message.getMessage_text()).setSmallIcon(R.drawable.icon_hoy_purple)
                .setContentIntent(pendingIntent).setSound(sound).setVibrate(vibrate).setAutoCancel(true)
                .build();

        notificationManager.notify(Integer.parseInt(message.getStranger_id().substring(0,3)),notification);
    }
}