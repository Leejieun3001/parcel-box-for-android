package kr.ac.sungshin.parcelbox.model.response;

/**
 * Created by hyeona on 2018. 1. 9..
 */

public class User {
    private int type;  // 택배기사(0), 사용자(1)
    private String id;
    private String name;
    private String address;
    private String phone;
    private String company;
    private int idx;

    public User(int type, String id, String name, String address, String phone, String company, int idx) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.company = company;
    }

    // getter function
    public int getType() {
        return type;
    }

    public String getId() {
        return id;
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

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
