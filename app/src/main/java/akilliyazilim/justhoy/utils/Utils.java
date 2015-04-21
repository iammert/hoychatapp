package akilliyazilim.justhoy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mertsimsek on 16.08.2014.
 */
public class Utils {

    public static boolean checkConnection(Context context)
    {
        ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMgr!=null)
        {
            NetworkInfo info = conMgr.getActiveNetworkInfo();
            if(info==null)
                return false;
            if(!info.isConnected())
                return false;
            if(!info.isAvailable())
                return false;
            return true;
        }
        else
            return false;

    }
}
