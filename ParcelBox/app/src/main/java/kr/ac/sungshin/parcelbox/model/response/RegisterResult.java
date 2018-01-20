package kr.ac.sungshin.parcelbox.model.response;

/**
 * Created by gominju on 2018. 1. 16..
 */

public class RegisterResult {
    private String message;
    private String detail;
    private String courier_name;
    private String courier_phone;
    private String courier_company;
    private String delivery_num;
    private String delivery_name;
    private String delivery_state;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCourier_name() {
        return courier_name;
    }

    public void setCourier_name(String courier_name) {
        this.courier_name = courier_name;
    }

    public String getCourier_phone() {
        return courier_phone;
    }

    public void setCourier_phone(String courier_phone) {
        this.courier_phone = courier_phone;
    }

    public String getCourier_company() {
        return courier_company;
    }

    public void setCourier_company(String courier_company) {
        this.courier_company = courier_company;
    }

    public String getDelivery_num() {
        return delivery_num;
    }

    public void setDelivery_num(String delivery_num) {
        this.delivery_num = delivery_num;
    }

    public String getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(String delivery_name) {
        this.delivery_name = delivery_name;
    }

    public String getDelivery_state() {
        return delivery_state;
    }

    public void setDelivery_state(String delivery_state) {
        this.delivery_state = delivery_state;
    }
}
