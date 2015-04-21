package akilliyazilim.justhoy.model;

/**
 * Created by mertsimsek on 15.08.2014.
 */
public class MessageText {

    String message_text;
    String stranger_id;
    String stranger_name;
    String my_name;
    String stranger_image_url;
    String my_image_url;
    String my_id;
    String whois;

    public MessageText() {
    }

    public MessageText(String message_text, String stranger_id, String my_id) {
        this.message_text = message_text;
        this.stranger_id = stranger_id;
        this.my_id = my_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getStranger_id() {
        return stranger_id;
    }

    public void setStranger_id(String stranger_id) {
        this.stranger_id = stranger_id;
    }

    public String getMy_id() {
        return my_id;
    }

    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }

    public String getWhois() {
        return whois;
    }

    public void setWhois(String whois) {
        this.whois = whois;
    }

    public String getStranger_name() {
        return stranger_name;
    }

    public void setStranger_name(String stranger_name) {
        this.stranger_name = stranger_name;
    }

    public String getMy_name() {
        return my_name;
    }

    public void setMy_name(String my_name) {
        this.my_name = my_name;
    }

    public String getStranger_image_url() {
        return stranger_image_url;
    }

    public void setStranger_image_url(String stranger_image_url) {
        this.stranger_image_url = stranger_image_url;
    }

    public String getMy_image_url() {
        return my_image_url;
    }

    public void setMy_image_url(String my_image_url) {
        this.my_image_url = my_image_url;
    }
}
