package kr.ac.sungshin.parcelbox.delivery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kr.ac.sungshin.parcelbox.R;

/**
 * Created by gominju on 2018. 1. 16..
 */

public class DeliveryRecyclerAdapter extends RecyclerView.Adapter<DeliveryRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<DeliveryItem> list;

    public DeliveryRecyclerAdapter(Context context, List<DeliveryItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DeliveryItem item = list.get(position);
        holder.number.setText((position + 1) + "");
        holder.type.setText(item.getParcel_info());
        holder.addr.setText(item.getAddress());
        holder.name.setText(item.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent =new Intent(context, WriteGameRecordActivity.class);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView number, type, addr, name;
        LinearLayout layout;

        public ViewHolder(View v) {
            super(v);
            number = v.findViewById(R.id.no_tv);
            type = v.findViewById(R.id.type_tv);
            addr = v.findViewById(R.id.addr_tv);
            name = v.findViewById(R.id.name_tv);
            layout = v.findViewById(R.id.layout);
        }
    }
}
