package kr.ac.sungshin.parcelbox.network;

import kr.ac.sungshin.parcelbox.model.request.Join;
import kr.ac.sungshin.parcelbox.model.request.Login;
import kr.ac.sungshin.parcelbox.model.request.Register;
import kr.ac.sungshin.parcelbox.model.response.DeliveryList;
import kr.ac.sungshin.parcelbox.model.response.DeliveryListResult;
import kr.ac.sungshin.parcelbox.model.response.FindingInfo;
import kr.ac.sungshin.parcelbox.model.response.LoginResult;
import kr.ac.sungshin.parcelbox.model.response.Message;
import kr.ac.sungshin.parcelbox.model.response.RegisterResult;
import kr.ac.sungshin.parcelbox.model.response.User;
import kr.ac.sungshin.parcelbox.model.response.VerificationCodeResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hyeona on 2018. 1. 9..
 */

public interface NetworkService {
    @POST("/login")
    Call<LoginResult> getLoginResult(@Body Login login);

    @POST("/login/find_id")
    Call<FindingInfo> getFindIdResult(@Body Login info);

    @POST("/login/find_password")
    Call<FindingInfo> getFindPwResult(@Body Login info);

    @GET("/join/duplicateCheck/")
    Call<Message> getDupCheckResult(@Query("tempEmail") String id);

    @GET("/join/verificationCode/")
    Call<VerificationCodeResult> getVerifiCodeResult(@Query("tempEmail") String id);

    @POST("/join")
    Call<Message> getJoinResult(@Body Join join);

    // 운송장번호 등록
//    @POST("/delivery/registerParcel/")
//    Call<RegisterResult> getRegisterParcel(@Body Register register);
    @Multipart
    @POST("/delivery/registerParcel/")
    Call<Message> getRegisterParcel(@Part("register") Register register, @Part MultipartBody.Part qrCode);

    // 택배 목록 조회 (택배기사)
    @GET("/delivery/showDeliveryList/")
    Call<DeliveryListResult> getDeliveryList(@Query("user_idx") int user_idx);

    @GET("/users/get_parcel")
    Call<DeliveryList> getDeliveryInfo();


}
