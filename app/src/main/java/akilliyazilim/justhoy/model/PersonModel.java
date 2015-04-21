package akilliyazilim.justhoy.model;

/**
 * Created by mertsimsek on 14.08.2014.
 */
public class PersonModel {

    int image;
    String name;
    String age;
    String status;
    String image_url;
    String mac_adress;
    String user_id;

    public PersonModel() {
    }

    public PersonModel(int image, String name, String age) {
        this.image = image;
        this.name = name;
        this.age = age;
    }

    public PersonModel(int image, String name, String age,String status) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMac_adress() {
        return mac_adress;
    }

    public void setMac_adress(String mac_adress) {
        this.mac_adress = mac_adress;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
