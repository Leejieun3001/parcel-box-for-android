package kr.ac.sungshin.parcelbox.model.request;

/**
 * Created by LG on 2018-01-14.
 */

public class Join {

    private String id;
    private String password;
    private int type;
    private String name;
    private String address;
    private String phone;

    public Join(String id, String password, int type, String name, String phone ,String address ) {
        this.id = id;
        this.password = password;
        this.type = type;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // getter function
    public String getId() {
        return id;
    }
    public  String getPassword() {return password;}
    public int getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
}
