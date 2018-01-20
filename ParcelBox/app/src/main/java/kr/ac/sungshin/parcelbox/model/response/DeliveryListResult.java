package kr.ac.sungshin.parcelbox.model.response;

import java.util.ArrayList;

import kr.ac.sungshin.parcelbox.delivery.DeliveryItem;

/**
 * Created by gominju on 2018. 1. 20..
 */

public class DeliveryListResult {
    private String message;
    private String detail;
    public DeliveryList result;

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

    public class DeliveryList {
        private int listSize;
        private ArrayList<DeliveryItem> list;

        public int getListSize() {
            return listSize;
        }

        public void setListSize(int listSize) {
            this.listSize = listSize;
        }

        public ArrayList<DeliveryItem> getList() {
            return list;
        }

        public void setList(ArrayList<DeliveryItem> list) {
            this.list = list;
        }
    }
}
