package akilliyazilim.justhoy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import akilliyazilim.justhoy.model.PersonModel;

/**
 * Created by mertsimsek on 16.08.2014.
 */
public class ApplicationPreferences {


    public static final String SHARED_MY_ID= "my_id";
    public static final String SHARED_MY_IMAGE_URL= "my_image_url";
    public static final String SHARED_MY_NAME= "my_name";
    public static final String SHARED_SHARE_DIALOG= "dialog_isshown";


    private static ApplicationPreferences instance = null;
    SharedPreferences local_shared_preferences;
    SharedPreferences.Editor local_editor;
    public Context context;

    private ApplicationPreferences(Context context) {
        this.context=context;
        local_shared_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        local_editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static ApplicationPreferences getInstance(Context context)
    {
        if(instance == null)
            instance = new ApplicationPreferences(context);
        return instance;
    }

    public void saveMeToPrefs(PersonModel me)
    {
        local_editor.putString(SHARED_MY_ID, me.getUser_id());
        local_editor.putString(SHARED_MY_IMAGE_URL, me.getImage_url());
        local_editor.putString(SHARED_MY_NAME, me.getName());
        local_editor.commit();
    }

    public PersonModel getMeFromPrefs()
    {
        PersonModel person = new PersonModel();
        person.setName(local_shared_preferences.getString(SHARED_MY_NAME, ""));
        person.setImage_url(local_shared_preferences.getString(SHARED_MY_IMAGE_URL, ""));
        person.setUser_id(local_shared_preferences.getString(SHARED_MY_ID, ""));
        return person;
    }

    public void shareDialogShowed(boolean isshowed)
    {
        local_editor.putBoolean(SHARED_SHARE_DIALOG,isshowed);
        local_editor.commit();
    }

    public boolean isDialogShown()
    {
        return local_shared_preferences.getBoolean(SHARED_SHARE_DIALOG,false);
    }
}
