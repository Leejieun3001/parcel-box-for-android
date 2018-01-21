package kr.ac.sungshin.parcelbox.network;

import android.app.Application;

import java.io.IOException;

import kr.ac.sungshin.parcelbox.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {
    private static ApplicationController instance;
    private static String baseUrl = "http://192.168.109.125:3000";
    private NetworkService networkService;
    private static Retrofit retrofit;

    public static ApplicationController getInstance() {
        return instance;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }    // 네트워크서비스 객체 반환


    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationController.instance = this; //인스턴스 객체 초기화
        buildService();
    }

    public void buildService() {
        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }

    public void setTokenOnHeader (String value) {
        final String token = value;
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("token", token).build();
                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }
}
