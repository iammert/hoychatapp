package akilliyazilim.justhoy.tasks;

import android.os.AsyncTask;

import akilliyazilim.justhoy.io.WebServices;
import akilliyazilim.justhoy.model.PersonModel;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class AddHoycuTask extends AsyncTask<Void,Void,Void> {

    PersonModel hoycu;

    public AddHoycuTask(PersonModel hoycu) {
        this.hoycu = hoycu;
    }

    @Override
    protected Void doInBackground(Void... params) {
        WebServices.getInstance().addHoycu(hoycu);
        return null;
    }
}
