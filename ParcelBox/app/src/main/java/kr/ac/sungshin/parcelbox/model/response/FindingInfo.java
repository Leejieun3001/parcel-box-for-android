package kr.ac.sungshin.parcelbox.model.response;

/**
 * Created by kakao on 2018. 1. 14..
 */

public class FindingInfo {
    private String message;
    private String id;

    public FindingInfo(String message, String id) {
        this.message = message;
        this.id = id;
    }

    public FindingInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }
}
