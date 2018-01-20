package kr.ac.sungshin.parcelbox.delivery;

/**
 * Created by gominju on 2018. 1. 16..
 */

public class DeliveryItem {
    private int num;
    private String type;
    private String address;
    private String name;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
