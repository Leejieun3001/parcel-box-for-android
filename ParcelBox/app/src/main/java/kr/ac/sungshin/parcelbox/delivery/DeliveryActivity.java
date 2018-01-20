package kr.ac.sungshin.parcelbox.delivery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.ac.sungshin.parcelbox.R;
import kr.ac.sungshin.parcelbox.model.request.Delivery;
import kr.ac.sungshin.parcelbox.model.request.Register;
import kr.ac.sungshin.parcelbox.model.response.RegisterResult;
import kr.ac.sungshin.parcelbox.model.response.Result;
import kr.ac.sungshin.parcelbox.network.ApplicationController;
import kr.ac.sungshin.parcelbox.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryActivity extends AppCompatActivity {
    private EditText et_num;
    private Button btn_reg;
    private NetworkService networkService;
    private String input_num;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        init();
        setNetwork();
        getParcelList();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_num = et_num.getText().toString();
                if (input_num.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    registerNum();
                }
            }
        });
    }

    private void getParcelList() {

    }

    private void registerNum() {
        Register register = new Register();
        register.setCourier_name("고민주기사");
        register.setParcel_idx(1);
        register.setParcel_num(input_num);
        register.setState(1);
        register.setUser_idx(1);

        Call<RegisterResult> resultCall = networkService.getRegisterParcel(register);
        resultCall.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                // Log.i("mytag", "response : " + response.body().toString());
                Log.i("mytag", "response : " + response.body().getMessage().toString());
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("fail")) {
                        Toast.makeText(getApplicationContext(), "이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "등록 성공", Toast.LENGTH_SHORT).show();
                        et_num.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT).show();
                Log.i("mytag", t.getMessage().toString());
            }
        });
    }

    private void setNetwork() {
        networkService = ApplicationController.getInstance().getNetworkService();
    }

    private void init() {
        et_num = findViewById(R.id.et_num);
        btn_reg = findViewById(R.id.btn_reg);
        recyclerView = findViewById(R.id.recycelrView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}