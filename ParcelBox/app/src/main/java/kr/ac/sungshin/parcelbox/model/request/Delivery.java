package kr.ac.sungshin.parcelbox.model.request;

/**
 * Created by gominju on 2018. 1. 14..
 */

public class Delivery {
    private String parcel_num;
    private int state;  // 배송준비중(0), 배송 중(1), 배송 완료(2)

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
}
