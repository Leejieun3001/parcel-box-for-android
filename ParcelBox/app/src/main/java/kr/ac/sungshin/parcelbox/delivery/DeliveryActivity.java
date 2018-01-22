package kr.ac.sungshin.parcelbox.delivery;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.ac.sungshin.parcelbox.R;
import kr.ac.sungshin.parcelbox.model.request.Register;
import kr.ac.sungshin.parcelbox.model.response.DeliveryListResult;
import kr.ac.sungshin.parcelbox.model.response.RegisterResult;
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
    private DeliveryRecyclerAdapter adapter;
    private List<DeliveryItem> itemList;
    private int user_idx = 1;
    @BindView(R.id.delivery_imageView_Qrcode)
    ImageView imageViewQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);

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
                    getParcelList();
                    registerQrCode(input_num);
                }
            }
        });
    }

    private void getParcelList() {
        Log.i("mytag", "getParcelList");

        Call<DeliveryListResult> resultCall = networkService.getDeliveryList(user_idx);
        resultCall.enqueue(new Callback<DeliveryListResult>() {
            @Override
            public void onResponse(Call<DeliveryListResult> call, Response<DeliveryListResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("success")) {
                        itemList = response.body().result.getList();
                        settingAdapter(itemList);
                        Log.i("mytag", "list size : " + response.body().result.getListSize());
                    }
                } else {
                    Log.i("mytag", "fail " + response.body().toString());
                }
                Log.i("mytag", "get response fail, " + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<DeliveryListResult> call, Throwable t) {
                Log.i("mytag", "get list error, " + t.getMessage().toString());
            }
        });


    }

    private void settingAdapter(List<DeliveryItem> itemList) {
        adapter = new DeliveryRecyclerAdapter(getApplicationContext(), itemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void registerNum() {
        Register register = new Register();
        register.setCourier_name("고민주기사");
        register.setParcel_idx(1);
        register.setParcel_num(input_num);
        register.setState(1);
        register.setUser_idx(user_idx);

        Call<RegisterResult> resultCall = networkService.getRegisterParcel(register);
        resultCall.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                // Log.i("mytag", "response : " + response.body().toString());
                Log.i("mytag", "response : " + response.body().getMessage());
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

    //QR코드 등록
    private void registerQrCode(String contents) {
        generateQRCode(contents);
        registerNum();

    }
    //QR 코드 생성
    public void generateQRCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 100, 100));
            ((ImageView) findViewById(R.id.delivery_imageView_Qrcode)).setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap toBitmap(BitMatrix matrix) {
        int height = matrix.getHeight();
        int width = matrix.getWidth();

        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}
