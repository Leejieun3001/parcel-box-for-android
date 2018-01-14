package kr.ac.sungshin.parcelbox.model.response;

/**
 * Created by LG on 2018-01-13.
 */

public class Result {
    String message;
    String detail;

    public Result(String message , String detail){
        this.message = message;
        this.detail = detail;
    }

    public String getMessage(){return  message;}
    public String getDetail(){ return  detail;}

}
