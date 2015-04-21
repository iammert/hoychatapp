package akilliyazilim.justhoy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.adapters.MessagingAdapter;
import akilliyazilim.justhoy.listeners.MessagingListener;
import akilliyazilim.justhoy.model.ConversationInfo;
import akilliyazilim.justhoy.model.MessageText;
import akilliyazilim.justhoy.model.PersonModel;
import akilliyazilim.justhoy.push.PushNotificationUtils;
import akilliyazilim.justhoy.sqlite.DBHelper;
import akilliyazilim.justhoy.sqlite.SeperateMessageTable;
import akilliyazilim.justhoy.utils.ApplicationPreferences;
import akilliyazilim.justhoy.views.HalvelticaEdittext;
import akilliyazilim.justhoy.views.HalvelticaTextView;
import akilliyazilim.justhoy.views.TypefaceSpan;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class MessagingActivity extends SherlockActivity implements View.OnClickListener, MessagingListener {

    ListView listview;
    HalvelticaEdittext edittext_message;
    ImageView image_send;

    int receiver_image;
    String stranger_name;
    String stranger_image_url;
    String stranger_id;
    String unread;
    PersonModel me;

    MessagingAdapter adapter;

    SeperateMessageTable seperate_table;

    CircleImageView circle_image_profile;
    HalvelticaTextView textview_stranger_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_message);

        Intent i = getIntent();
        stranger_name = i.getStringExtra("stranger_name");
        stranger_id = i.getStringExtra("stranger_id");
        stranger_image_url = i.getStringExtra("stranger_image_url");
        unread = i.getStringExtra("message_unread");

        View customNav = LayoutInflater.from(this).inflate(R.layout.layout_messaging_actionbar, null);
        circle_image_profile = (CircleImageView) customNav.findViewById(R.id.imageview_messaging_stranger_image);
        textview_stranger_name = (HalvelticaTextView) customNav.findViewById(R.id.textview_messaging_stranger_name);
        Picasso.with(getApplicationContext()).load(stranger_image_url).into(circle_image_profile);
        textview_stranger_name.setText(stranger_name);
        circle_image_profile.setOnClickListener(this);

        getSupportActionBar().setCustomView(customNav);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        DBHelper helperx = new DBHelper(getApplicationContext());
        SeperateMessageTable messagingtable = helperx.getSeperateMessageTableGateway(stranger_id);
        ConversationInfo con_info = new ConversationInfo();
        con_info.setIsUnread(unread);
        con_info.setStranger_image_url(stranger_image_url);
        con_info.setStranger_name(stranger_name);
        con_info.setStranger_id(stranger_id);

        if (messagingtable.getAllMessagingMessages().size() > 0)
            helperx.getConversationTableGateway().updateConversation(con_info);

        me = ApplicationPreferences.getInstance(getApplicationContext()).getMeFromPrefs();

        DBHelper helper = new DBHelper(getApplicationContext());
        seperate_table = helper.getSeperateMessageTableGateway(stranger_id);
        seperate_table.setMessagingListener(this);

        initializeActionbarText(stranger_name);

        listview = (ListView) findViewById(R.id.listview_messaging);
        edittext_message = (HalvelticaEdittext) findViewById(R.id.edittext_send);

        image_send = (ImageView) findViewById(R.id.imageview_send);
        image_send.setOnClickListener(this);

        adapter = new MessagingAdapter(getMessageList(), getApplicationContext());
        listview.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_send:
                if (edittext_message.getText().length() > 0) {
                    MessageText message = new MessageText();
                    message.setMessage_text(edittext_message.getText().toString());
                    message.setStranger_image_url(stranger_image_url);
                    message.setStranger_id(stranger_id);
                    message.setStranger_name(stranger_name);
                    message.setMy_id(me.getUser_id());
                    message.setMy_image_url(me.getImage_url());
                    message.setMy_name(me.getName());
                    sendMessage(message);
                    adapter.setMessages(getMessageList());
                    edittext_message.setText("");
                }
                break;
            case R.id.imageview_messaging_stranger_image:
                //TODO open image in big view
                break;
        }
    }

    public ArrayList<MessageText> getMessageList() {
        ArrayList<MessageText> messages = seperate_table.getAllMessagingMessages();
        return messages;
    }

    public SpannableString initializeActionbarText(String text) {
        SpannableString s = new SpannableString("     " + text);
        s.setSpan(new TypefaceSpan(this, "helvelticabold.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        return s;
    }

    public void sendMessage(MessageText message_send) {
        ConversationInfo coninfo = new ConversationInfo();
        coninfo.setStranger_id(stranger_id);
        coninfo.setStranger_name(stranger_name);
        coninfo.setStranger_image_url(stranger_image_url);
        coninfo.setIsUnread("read");

        DBHelper helper = new DBHelper(getApplicationContext());
        helper.getConversationTableGateway().insertConversation(coninfo);
        helper.getSeperateMessageTableGateway(stranger_id).insertMessage(message_send, "1");

        PushNotificationUtils.getInstance().sendMessage(message_send);
    }

    @Override
    public void onMessageInserted() {
        adapter.setMessages(getMessageList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        HoyApp.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        HoyApp.activityPaused();
    }

    public void onBackPressed() {
        Intent i = new Intent(MessagingActivity.this, HoyActivity.class);
        i.putExtra("id", ApplicationPreferences.getInstance(getApplicationContext()).getMeFromPrefs().getUser_id());
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        this.startActivity(i);
        this.finish();
        return;
    }
}
