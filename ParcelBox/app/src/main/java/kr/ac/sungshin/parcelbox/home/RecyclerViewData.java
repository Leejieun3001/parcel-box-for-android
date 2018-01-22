package kr.ac.sungshin.parcelbox.home;

/**
 * Created by hyeona on 2018. 1. 14..
 */

public class RecyclerViewData {
    private String parcel_info;
    private String courier_name;
    private String parcel_num;
    private int state;
    private String qr_code;

    public RecyclerViewData() { }

    public RecyclerViewData(String parcel_info, String courier_name, String parcel_num, int state, String qr_code) {
        this.parcel_info = parcel_info;
        this.courier_name = courier_name;
        this.parcel_num = parcel_num;
        this.state = state;
        this.qr_code = qr_code;
    }

    public String getParcel_info() {
        return parcel_info;
    }

    public void setParcel_info(String parcel_info) {
        this.parcel_info = parcel_info;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

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

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }
}
