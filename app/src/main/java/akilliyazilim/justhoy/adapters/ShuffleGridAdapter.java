package akilliyazilim.justhoy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import akilliyazilim.justhoy.model.PersonModel;
import akilliyazilim.justhoy.R;
import akilliyazilim.justhoy.views.HalvelticaTextViewLight;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class ShuffleGridAdapter extends BaseAdapter {

    ArrayList<PersonModel> person_list;
    LayoutInflater inflater;
    Context context;

    public ShuffleGridAdapter(Context context, ArrayList<PersonModel> person_list) {
        this.person_list = person_list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return person_list.size();
    }

    @Override
    public Object getItem(int position) {
        return person_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setAdapterList(ArrayList<PersonModel> person_list)
    {
        this.person_list = person_list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PersonViewHolder viewholder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_shuffle_person,parent,false);
            viewholder = new PersonViewHolder();
            viewholder.imageview_profil = (CircleImageView) convertView.findViewById(R.id.circle_image_shuffle);
            viewholder.textview_name_surname = (HalvelticaTextViewLight) convertView.findViewById(R.id.textview_name_shuffle);
            convertView.setTag(viewholder);
        }
        else
            viewholder = (PersonViewHolder) convertView.getTag();

        PersonModel person = person_list.get(position);

        if(person != null)
        {
            Picasso.with(context).load(person.getImage_url()).into(viewholder.imageview_profil);
            viewholder.textview_name_surname.setText(person.getName() + " (" + "22" + ")");
        }

        return convertView;
    }

    private class PersonViewHolder
    {
        CircleImageView imageview_profil;
        HalvelticaTextViewLight textview_name_surname;
    }
}
