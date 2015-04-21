package akilliyazilim.justhoy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.activity.MessagingActivity;
import akilliyazilim.justhoy.adapters.MessagesListAdapter;
import akilliyazilim.justhoy.listeners.ConversationListener;
import akilliyazilim.justhoy.model.ConversationInfo;
import akilliyazilim.justhoy.sqlite.ConversationsTable;
import akilliyazilim.justhoy.sqlite.DBHelper;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class MessagesFragment extends SherlockFragment implements AdapterView.OnItemClickListener, ConversationListener {

    ListView listview;
    MessagesListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_messages,container,false);
        listview = (ListView) v.findViewById(R.id.listview_messages);
        listview.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DBHelper helper = new DBHelper(getActivity().getApplicationContext());
        ConversationsTable table = helper.getConversationTableGateway();
        table.setConversationAddListener(this);
        ArrayList<ConversationInfo> conversations = table.getAllConversations();
        adapter = new MessagesListAdapter(getActivity().getApplicationContext(),conversations);
        listview.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), MessagingActivity.class);
        ConversationInfo conversation = (ConversationInfo)parent.getAdapter().getItem(position);
        i.putExtra("stranger_name",conversation.getStranger_name());
        i.putExtra("stranger_image_url",conversation.getStranger_image_url());
        i.putExtra("stranger_id",conversation.getStranger_id());
        i.putExtra("message_unread","read");
        startActivity(i);
    }

    @Override
    public void onConversationHappened() {
        if(getActivity()!=null){
            DBHelper helper = new DBHelper(getActivity().getApplicationContext());
            ConversationsTable table = helper.getConversationTableGateway();
            ArrayList<ConversationInfo> conversations = table.getAllConversations();
            adapter.setConversations(conversations);
        }
    }
}
