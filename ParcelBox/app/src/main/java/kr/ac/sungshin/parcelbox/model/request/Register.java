package kr.ac.sungshin.parcelbox.model.request;

/**
 * Created by gominju on 2018. 1. 14..
 */

public class Register {
    private String parcel_num;
    private int state;  // 배송준비중(0), 배송 중(1), 배송 완료(2)
    private int parcel_idx;
    private int user_idx;
    private String courier_name;

    public String getParcel_num() {
        return parcel_num;
    }

    public void setParcel_num(String parcel_num) {
        this.parcel_num = parcel_num;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getParcel_idx() {
        return parcel_idx;
    }

    public void setParcel_idx(int parcel_idx) {
        this.parcel_idx = parcel_idx;
    }

    public int getUser_idx() {
        return user_idx;
    }

    public void setUser_idx(int user_idx) {
        this.user_idx = user_idx;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }
}
