package kr.ac.sungshin.parcelbox.model.response;

import java.util.ArrayList;

import kr.ac.sungshin.parcelbox.home.RecyclerViewData;

/**
 * Created by hyeona on 2018. 1. 14..
 */

public class DeliveryList {
    private ArrayList<RecyclerViewData> data;
    private String message;

    public DeliveryList(ArrayList<RecyclerViewData> data, String message) {
        this.data = data;
        this.message = message;
    }

    public ArrayList<RecyclerViewData> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
