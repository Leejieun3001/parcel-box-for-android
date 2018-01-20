package kr.ac.sungshin.parcelbox.network;

/**
 * Created by hyeona on 2018. 1. 20..
 * 싱글톤으로 유저 정보를 저장해두는 클래스입니다.
 * 로그인 전 getToken() 함수를 통해 token 값을 검사합니다.
 * token 값이 존재한다면 로그인 과정을 거치지않고 바로 메인으로 넘어가게 됩니다.
 */

public class UserInfo {
    private static UserInfo instance;
    private String token = "";

    private UserInfo() { }

    public static UserInfo getInstance() {
        if (instance == null) instance =  new UserInfo();
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
