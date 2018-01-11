package kr.ac.sungshin.parcelbox.model.response;

/**
 * Created by hyeona on 2018. 1. 9..
 */

public class User {
    private int type;
    private String email;
    private String name;
    private String address;
    private String phone;
    private String company;

    public User(int type, String email, String name, String address, String phone, String company) {
        this.type = type;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.company = company;
    }

    // getter function
    public int getType() {
        return type;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getCompany() {
        return company;
    }
}
