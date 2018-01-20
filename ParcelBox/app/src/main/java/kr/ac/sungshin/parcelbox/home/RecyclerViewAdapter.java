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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "RecyclerViewAdapter";
    private final int VIEW_TYPE_HEADER = 1;
    private final int VIEW_TYPE_ITEM = 2;
    private ArrayList<RecyclerViewData> itemDatas;

    public RecyclerViewAdapter(ArrayList<RecyclerViewData> itemDatas) {
        this.itemDatas = itemDatas;
    }

    public void setAdapter(ArrayList<RecyclerViewData> itemDatas) {
        this.itemDatas.addAll(itemDatas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder : " + viewType);
        if (viewType == VIEW_TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_home_recyclerview_header, parent, false);
            HeaderViewHolder viewHolder = new HeaderViewHolder(view);
            return viewHolder;
        } else {
            Log.d(TAG, "item일때");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_home_recyclerview_item, parent, false);
            RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "position: " + position);
        if (holder instanceof RecyclerViewHolder) {
            Log.d(TAG, "position: " + position + ", " + itemDatas.get(position - 1).getCourier_name());
            ((RecyclerViewHolder) holder).textViewDeliverName.setText(itemDatas.get(position - 1).getCourier_name());
            ((RecyclerViewHolder) holder).textViewParcelInfo.setText(itemDatas.get(position - 1).getParcel_info());
            ((RecyclerViewHolder) holder).textViewParcelNum.setText(itemDatas.get(position - 1).getParcel_num());
        }
    }

    @Override
    public int getItemCount() { return itemDatas != null ? itemDatas.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType : " + position);
        if (position == 0) return VIEW_TYPE_HEADER;
        else return VIEW_TYPE_ITEM;
    }
}

class HeaderViewHolder extends RecyclerView.ViewHolder {

    public HeaderViewHolder(View itemView) {
        super(itemView);
    }

}
