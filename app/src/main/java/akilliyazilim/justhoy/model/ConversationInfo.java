package akilliyazilim.justhoy.model;

/**
 * Created by mertsimsek on 16.08.2014.
 */
public class ConversationInfo {

    String stranger_id;
    String stranger_name;
    String stranger_image_url;
    String isUnread;

    public ConversationInfo() {
    }

    public String getStranger_id() {
        return stranger_id;
    }

    public void setStranger_id(String stranger_id) {
        this.stranger_id = stranger_id;
    }

    public String getStranger_name() {
        return stranger_name;
    }

    public void setStranger_name(String stranger_name) {
        this.stranger_name = stranger_name;
    }

    public String getStranger_image_url() {
        return stranger_image_url;
    }

    public void setStranger_image_url(String stranger_image_url) {
        this.stranger_image_url = stranger_image_url;
    }

    public String getIsUnread() {
        return isUnread;
    }

    public void setIsUnread(String isUnread) {
        this.isUnread = isUnread;
    }
}
