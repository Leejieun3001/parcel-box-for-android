package kr.ac.sungshin.parcelbox.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.ac.sungshin.parcelbox.R;

/**
 * Created by hyeona on 2018. 1. 14..
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.userhome_textview_parcelinfo)
    TextView textViewParcelInfo;
    @BindView(R.id.userhome_textview_delivername)
    TextView textViewDeliverName;
    @BindView(R.id.userhome_textview_parcelnum)
    TextView textViewParcelNum;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }
}
