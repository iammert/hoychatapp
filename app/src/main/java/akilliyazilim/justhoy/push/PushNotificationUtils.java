package akilliyazilim.justhoy.push;

import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

import akilliyazilim.justhoy.model.MessageText;

/**
 * Created by mertsimsek on 16.08.2014.
 */
public class PushNotificationUtils {

    private static PushNotificationUtils instance;

    private PushNotificationUtils() {
    }

    public static PushNotificationUtils getInstance()
    {
        if(instance == null)
            instance = new PushNotificationUtils();
        return instance;
    }

    public void sendMessage(MessageText message)
    {
        JSONObject data = null;
        try {
            data = new JSONObject("{\"action\": \"akilliyazilim.justhoy.receiver.UPDATE_STATUS\"," +
                    "\"stranger_id\": \"" + message.getStranger_id()+ "\"," +
                    "\"my_id\": \"" + message.getMy_id()+ "\"," +
                    "\"stranger_name\": \"" + message.getStranger_name()+ "\"," +
                    "\"my_name\": \"" + message.getMy_name()+ "\"," +
                    "\"stranger_image_url\": \"" + message.getStranger_image_url()+ "\"," +
                    "\"my_image_url\": \"" + message.getMy_image_url()+ "\"," +
                    "\"message_text\": \"" + message.getMessage_text()+ "\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ParsePush push = new ParsePush();
        push.setChannel("hoykanali"+message.getStranger_id());
        push.setData(data);
        push.sendInBackground();

    }
}
