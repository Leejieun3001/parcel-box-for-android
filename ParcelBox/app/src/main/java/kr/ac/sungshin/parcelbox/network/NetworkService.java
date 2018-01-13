package kr.ac.sungshin.parcelbox.network;

import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.response.Result;
import kr.ac.sungshin.parcelbox.model.response.User;
import kr.ac.sungshin.parcelbox.model.response.VerificationCodeResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hyeona on 2018. 1. 9..
 */

public interface NetworkService {
    @POST("/login/login")
    Call<User> getLoginResult(@Body Login login);

    @GET("/join/duplicateCheck/{tempEmail}")
    Call<Result> getIdCheck(@Query("tempEmail") String id);

    @GET("/join/verificationCode/{memberId}")
    Call<VerificationCodeResult> getChecknumber (@Query("tempEmail") String id);


}
