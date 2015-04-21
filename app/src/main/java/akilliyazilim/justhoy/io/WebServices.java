package akilliyazilim.justhoy.io;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import akilliyazilim.justhoy.model.PersonModel;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class WebServices {

    public static WebServices instance = null;

    private WebServices() {
    }

    public static WebServices getInstance()
    {
        if(instance == null)
            instance = new WebServices();
        return instance;
    }

    public void addHoycu(PersonModel person)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ServerConstant.URL_ADD_NEW);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair(ServerConstant.NAME_SURNAME, person.getName()));
            nameValuePairs.add(new BasicNameValuePair(ServerConstant.USER_ID, person.getUser_id()));
            nameValuePairs.add(new BasicNameValuePair(ServerConstant.AGE, person.getAge()));
            nameValuePairs.add(new BasicNameValuePair(ServerConstant.IMAGE_URL, person.getImage_url()));
            nameValuePairs.add(new BasicNameValuePair(ServerConstant.MAC_ADRESS, person.getMac_adress()));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            System.out.println(response);

            Log.v("onPostExecute()", "Done");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public ArrayList<PersonModel> getHoycular(String user_id)
    {
        ArrayList<PersonModel> persons_array_list = new ArrayList<PersonModel>();
        InputStream input_stream;
        JSONArray persons_json_array = null;
        JSONObject JSON_object = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(ServerConstant.URL_GET_ALL);


        try {
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            input_stream = response.getEntity().getContent();
            System.out.println(response.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    input_stream, "iso-8859-1"));
            StringBuilder json = new StringBuilder();
            json.append(reader.readLine() + "\n");

            String read = "";

            while ((read = reader.readLine()) != null) {
                json.append(read + "\n");
            }

            JSONTokener tokener = new JSONTokener(json.toString());
            Object json_helper = tokener.nextValue();
            if (json_helper != null && json_helper instanceof JSONObject)
                JSON_object = (JSONObject) json_helper;

            if(JSON_object!=null)
            {
                persons_json_array = JSON_object.getJSONArray("user");

                for (int i = 0; i < persons_json_array.length(); i++) {
                    JSONObject obj = persons_json_array.getJSONObject(i);
                    PersonModel p = new PersonModel();
                    p.setName(obj.getString(ServerConstant.NAME_SURNAME));
                    p.setUser_id(obj.getString(ServerConstant.USER_ID));
                    p.setMac_adress(obj.getString(ServerConstant.MAC_ADRESS));
                    p.setImage_url(obj.getString(ServerConstant.IMAGE_URL));
                    p.setAge(obj.getString(ServerConstant.AGE));
                    if(!obj.getString(ServerConstant.USER_ID).equals(user_id))
                        persons_array_list.add(p);
                }
            }

            // httpclient.getConnectionManager().shutdown();
            input_stream.close();
            reader.close();

        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return persons_array_list;
    }
}
