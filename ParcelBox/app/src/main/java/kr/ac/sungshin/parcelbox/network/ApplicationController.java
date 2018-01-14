package kr.ac.sungshin.parcelbox.network;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
<<<<<<< HEAD
 * Created by gominju on 2018. 1. 7..
 */

public class ApplicationController extends Application {
    private static ApplicationController instance;    // 먼저 어플리케이션 인스턴스 객체를 하나 선언

    private static String baseUrl = "http://13.125.107.225:3000";  // 베이스 url 초기화

    private NetworkService networkService;                        // 네트워크 서비스 객체 선언

    public static ApplicationController getInstance() {
        return instance;
    }    // 인스턴스 객체 반환  왜? static 안드에서 static 으로 선언된 변수는 매번 객체를 새로 생성하지 않아도 다른 액티비티에서
    //자유롭게 사용가능합니다.
=======
 * Created by hyeona on 2018. 1. 8..
 */

public class ApplicationController extends Application {
    private static ApplicationController instance;
    private static String baseUrl = "http://13.124.188.254:3000";
    private NetworkService networkService;

    public static ApplicationController getInstance() {
        return instance;
    }
>>>>>>> master

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
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }
<<<<<<< HEAD

=======
>>>>>>> master
}
