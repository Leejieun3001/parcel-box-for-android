package kr.ac.sungshin.parcelbox.home;

import android.widget.TextView;

import butterknife.BindView;
import kr.ac.sungshin.parcelbox.R;

/**
 * Created by hyeona on 2018. 1. 14..
 */

public class RecyclerViewData {
    String parcel_info;
    String courier_name;
    String parcel_num;

    public RecyclerViewData(String parcel_info, String courier_name, String parcel_num) {
        this.parcel_info = parcel_info;
        this.courier_name = courier_name;
        this.parcel_num = parcel_num;
    }
}
