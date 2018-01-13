package kr.ac.sungshin.parcelbox.model.request;

/**
 * Created by hyeona on 2018. 1. 9..
 */

public class Login {
    String id;
    String password;
    String name;
    int phone;

    public Login() { }

    public Login(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Login(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    public Login(String id, String name, int phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
