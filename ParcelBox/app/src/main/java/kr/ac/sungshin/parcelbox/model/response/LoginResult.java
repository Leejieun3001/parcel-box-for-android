package kr.ac.sungshin.parcelbox.model.response;

/**
 * Created by kakao on 2018. 1. 20..
 */

public class LoginResult {
    private String message;
    private User user;
    private String token;

    public LoginResult(String message, User user, String token) {
        this.message = message;
        this.user = user;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
