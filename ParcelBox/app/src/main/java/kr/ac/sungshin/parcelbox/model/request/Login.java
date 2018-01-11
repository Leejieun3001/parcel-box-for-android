package kr.ac.sungshin.parcelbox.model.request;

/**
 * Created by kakao on 2018. 1. 9..
 */

public class Login {
    String email;
    String password;

    public Login() { }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
