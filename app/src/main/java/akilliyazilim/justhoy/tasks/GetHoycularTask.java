package akilliyazilim.justhoy.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;

import akilliyazilim.justhoy.io.WebServices;
import akilliyazilim.justhoy.model.PersonModel;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class GetHoycularTask extends AsyncTask<Void,Void,ArrayList<PersonModel>>{

    String user_id;

    public GetHoycularTask(String user_id) {
        this.user_id = user_id;
    }

    @Override
    protected ArrayList<PersonModel> doInBackground(Void... params) {
        ArrayList<PersonModel> hoycular = new ArrayList<PersonModel>();
        hoycular = WebServices.getInstance().getHoycular(user_id);
        return hoycular;
    }
}
