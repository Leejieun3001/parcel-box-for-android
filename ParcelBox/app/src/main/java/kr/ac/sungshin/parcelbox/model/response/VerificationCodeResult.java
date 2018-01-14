package kr.ac.sungshin.parcelbox.model.response;

/**
 * Created by LG on 2018-01-12.
 */

public class VerificationCodeResult {
    String message;
    String VerificationCode;

    public VerificationCodeResult(String message, String VerificationCode){
        this.message = message;
        this.VerificationCode = VerificationCode;
    }

    public  String getMessage(){return message;}
    public String getVerificationCode() {return VerificationCode;}
}
