package akilliyazilim.justhoy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.model.MessageText;
import akilliyazilim.justhoy.views.HalvelticaTextView;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class MessagingAdapter extends BaseAdapter {

    ArrayList<MessageText> messages;
    LayoutInflater inflater;
    Context context;

    public MessagingAdapter(ArrayList<MessageText> messages, Context context) {
        this.messages = messages;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMessages(ArrayList<MessageText> messages)
    {
        this.messages = messages;
        notifyDataSetChanged();
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int isMe = Integer.parseInt(messages.get(position).getWhois());

        if(isMe == 1)
        {
            if(convertView == null)
                convertView = inflater.inflate(R.layout.item_messaging_me,parent,false);
            else if(convertView.getTag()=="1")
                convertView = inflater.inflate(R.layout.item_messaging_me,parent,false);

            HalvelticaTextView textview_message_me = (HalvelticaTextView)convertView.findViewById(R.id.textview_messaging_me);
            textview_message_me.setText(messages.get(position).getMessage_text());

            convertView.setTag("2");
            return convertView;
        }
        else
        {
            if(convertView == null)
                convertView = inflater.inflate(R.layout.item_messaging_stanger,parent,false);
            else if(convertView.getTag()=="2")
                convertView = inflater.inflate(R.layout.item_messaging_stanger,parent,false);

            HalvelticaTextView textview_message_stranger = (HalvelticaTextView)convertView.findViewById(R.id.textview_messaging_stranger);
            textview_message_stranger.setText(messages.get(position).getMessage_text());

            convertView.setTag("1");
            return convertView;
        }
    }

}
