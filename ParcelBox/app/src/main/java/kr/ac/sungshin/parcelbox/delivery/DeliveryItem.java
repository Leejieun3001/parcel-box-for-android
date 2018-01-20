package kr.ac.sungshin.parcelbox.delivery;

/**
 * Created by gominju on 2018. 1. 16..
 */

public class DeliveryItem {
    private int num;
    private String parcel_info;
    private String address;
    private String name;
    private int status;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getParcel_info() {
        return parcel_info;
    }

    public void setParcel_info(String parcel_info) {
        this.parcel_info = parcel_info;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
