package kr.ac.sungshin.parcelbox.model.response;

/**
*Created by LG on 2018-01-14.
 */

public class VerificationCodeResult {
    private String message;
    private String verificationCode;

    public VerificationCodeResult(String message, String verificationCode){
        this.message = message;
        this.verificationCode = verificationCode;
    }
    public String getMessage() {
        return message;
    }
    public  String getVerificationCode(){
        return verificationCode;
    }
}
