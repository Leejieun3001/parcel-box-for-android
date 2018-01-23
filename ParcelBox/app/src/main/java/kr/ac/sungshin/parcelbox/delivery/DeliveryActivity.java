package kr.ac.sungshin.parcelbox.delivery;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

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
import kr.ac.sungshin.parcelbox.model.response.Message;
import kr.ac.sungshin.parcelbox.model.response.RegisterResult;
import kr.ac.sungshin.parcelbox.network.ApplicationController;
import kr.ac.sungshin.parcelbox.network.NetworkService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
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
    private int user_idx;
    @BindView(R.id.delivery_imageView_Qrcode)
    ImageView imageViewQrcode;
    private MultipartBody.Part qrCodeBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);

        SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
        user_idx = preferences.getInt("user_idx", 0);
        Log.i("mytag", "user_idx : " + user_idx);

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
                    registerQrCode(input_num);
                    getParcelList();

                }
            }
        });
    }

    private void getParcelList() {
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
            }

            @Override
            public void onFailure(Call<DeliveryListResult> call, Throwable t) {
                Log.i("mytag", "get list error, " + t.getMessage().toString());
            }
        });


    }

    private void settingAdapter(List<DeliveryItem> itemList) {
        adapter = new DeliveryRecyclerAdapter(DeliveryActivity.this, itemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void registerNum() {
        Register register = new Register();
        register.setParcel_num(input_num);
        register.setUser_idx(user_idx);
//        register.setCourier_name("고민주기사");
        //register.setParcel_idx(1);
//        register.setState(1);

        Call<Message> resultCall = networkService.getRegisterParcel(register, qrCodeBody);
        resultCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                // Log.i("mytag", "response : " + response.body().toString());
                Log.i("mytag", "response : " + response.body().getMessage());
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equals("ALREADY_EXIST")) {
                        Toast.makeText(getApplicationContext(), "이미 등록된 운송장 번호입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "등록 성공", Toast.LENGTH_SHORT).show();
                        et_num.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
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
    }

    //QR 코드 생성
    public void generateQRCode(String contents) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            Bitmap bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 100, 100));
            saveBitmaptoJpeg(bitmap);
            ((ImageView) findViewById(R.id.delivery_imageView_Qrcode)).setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = makeRequest();
        qrCodeBody = MultipartBody.Part.createFormData("qrCode", "QRCode", requestBody);
        registerNum();
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


    public RequestBody makeRequest() {
        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.SaveSafe/QRCode.png");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        return requestBody;
    }

    private static void saveBitmaptoJpeg(Bitmap bitmap) {
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String foler_name = "/.SaveSafe/";
        String file_name = "QRCode.png";
        String string_path = ex_storage + foler_name;

        File file_path;
        try {
            file_path = new File(string_path);
            if (!file_path.isDirectory()) {
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path + file_name);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();

        } catch (FileNotFoundException exception) {
            Log.e("mytag", "FileNotFoundException" + exception.getMessage());
        } catch (IOException exception) {
            Log.e("mytag", "IOException" + exception.getMessage());
        }


    }

}
