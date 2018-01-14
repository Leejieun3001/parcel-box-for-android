package kr.ac.sungshin.parcelbox.network;

import kr.ac.sungshin.parcelbox.model.request.Join;
import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.response.FindingInfo;
import kr.ac.sungshin.parcelbox.model.response.Message;
import kr.ac.sungshin.parcelbox.model.response.User;
import kr.ac.sungshin.parcelbox.model.response.VerificationCodeResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hyeona on 2018. 1. 9..
 */

public interface NetworkService {
    @POST("/login")
    Call<User> getLoginResult(@Body Login login);

    @POST("/login/find_id")
    Call<FindingInfo> getFindIdResult(@Body Login info);

    @POST("/login/find_password")
    Call<FindingInfo> getFindPwResult(@Body Login info);

    @GET("/join/duplicateCheck/")
    Call<Message> getDupCheckResult(@Query("tempEmail") String id);

    @GET("/join/verificationCode/")
    Call<VerificationCodeResult> getVerifiCodeResult(@Query("tempEmail") String id);
}
