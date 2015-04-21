package akilliyazilim.justhoy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.model.ConversationInfo;
import akilliyazilim.justhoy.views.HalvelticaTextViewLight;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class MessagesListAdapter extends BaseAdapter {

    ArrayList<ConversationInfo> conversations;
    LayoutInflater inflater;
    Context context;

    public MessagesListAdapter(Context context, ArrayList<ConversationInfo> conversations) {
        this.conversations = conversations;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    public void setConversations(ArrayList<ConversationInfo> conversations)
    {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Object getItem(int position) {
        return conversations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonViewHolder viewholder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_messages_person,parent,false);
            viewholder = new PersonViewHolder();
            viewholder.imageview_profil = (CircleImageView) convertView.findViewById(R.id.imageview_messages_profile);
            viewholder.textview_title = (HalvelticaTextViewLight) convertView.findViewById(R.id.messages_message_header);
            viewholder.textview_subtitle = (HalvelticaTextViewLight) convertView.findViewById(R.id.message_message_subtitle);
            viewholder.textview_new_unread = (ShimmerTextView) convertView.findViewById(R.id.textview_new_message_alert);
            viewholder.shimmer = new Shimmer();
            viewholder.shimmer.start(viewholder.textview_new_unread);
            convertView.setTag(viewholder);
        }
        else
            viewholder = (PersonViewHolder) convertView.getTag();

        ConversationInfo converstion = conversations.get(position);

        if(converstion != null)
        {
            Picasso.with(context).load(converstion.getStranger_image_url()).into(viewholder.imageview_profil);
            viewholder.textview_title.setText(converstion.getStranger_name() + " (" + "22" + ")");
            viewholder.textview_subtitle.setText("Say Hoy!");
            if(converstion.getIsUnread().equals("unread"))
                viewholder.textview_new_unread.setVisibility(View.VISIBLE);
            else
                viewholder.textview_new_unread.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }

    private class PersonViewHolder
    {
        CircleImageView imageview_profil;
        HalvelticaTextViewLight textview_title;
        HalvelticaTextViewLight textview_subtitle;
        ShimmerTextView textview_new_unread;
        Shimmer shimmer;
    }
}
