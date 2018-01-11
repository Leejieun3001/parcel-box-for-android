package kr.ac.sungshin.parcelbox.network;

import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.response.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hyeona on 2018. 1. 9..
 */

public interface NetworkService {
    @POST("/login/login")
    Call<User> getLoginResult(@Body Login login);
}
