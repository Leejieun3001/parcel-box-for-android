package kr.ac.sungshin.parcelbox.model.request;

/**
 * Created by LG on 2018-01-12.
 */

public class Join {
    String id;
    String password;
    int type;
    String name;
    String phone;
    String address;

    public Join(String id, String password, int type, String name, String phone, String address){
        this.id =id;
        this.password = password;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

}
