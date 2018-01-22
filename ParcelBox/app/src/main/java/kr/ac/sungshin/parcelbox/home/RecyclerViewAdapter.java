package kr.ac.sungshin.parcelbox.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.ac.sungshin.parcelbox.R;

/**
 * Created by hyeona on 2018. 1. 14..
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final String TAG = "RecyclerViewAdapter";
    private ArrayList<RecyclerViewData> itemDatas;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<RecyclerViewData> itemDatas) {
        this.itemDatas = itemDatas;
        this.context = context;
    }

    public void updateList(ArrayList<RecyclerViewData> itemDatas) {
        this.itemDatas.addAll(itemDatas);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder : " + viewType);
        View view = LayoutInflater.from(this.context).inflate(R.layout.user_home_recyclerview_item, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ((RecyclerViewHolder) holder).textViewDeliverName.setText(itemDatas.get(position).getCourier_name());
        ((RecyclerViewHolder) holder).textViewParcelInfo.setText(itemDatas.get(position).getParcel_info());
        ((RecyclerViewHolder) holder).textViewParcelNum.setText(itemDatas.get(position).getParcel_num());
    }

    @Override
    public int getItemCount() {
        return itemDatas != null ? itemDatas.size() : 0;
    }
}


