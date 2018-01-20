package kr.ac.sungshin.parcelbox.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.ac.sungshin.parcelbox.R;
import kr.ac.sungshin.parcelbox.model.response.DeliveryList;
import kr.ac.sungshin.parcelbox.network.ApplicationController;
import kr.ac.sungshin.parcelbox.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity {

    @BindView(R.id.userhome_recyclerview_recyclerview)
    RecyclerView recyclerView;

    private ArrayList<RecyclerViewData> itemDatas;
    private RecyclerViewAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;
    private final String TAG = "UserHomeActivity";
    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);

        service = ApplicationController.getInstance().getNetworkService();
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Call<DeliveryList> getDeliveryInfo = service.getDeliveryInfo();
        getDeliveryInfo.enqueue(new Callback<DeliveryList>() {
            @Override
            public void onResponse(retrofit2.Call<DeliveryList> call, Response<DeliveryList> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    if (message.equals("EXIST_DATA")) {
                        Log.d(TAG, "EXIST_DATA");
                        itemDatas= response.body().getData();
                    }
                    Log.d(TAG, "size: " + itemDatas.size());
                    recyclerAdapter = new RecyclerViewAdapter(itemDatas);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<DeliveryList> call, Throwable t) {

            }
        });
    }
}
