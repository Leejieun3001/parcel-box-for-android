package kr.ac.sungshin.parcelbox.model.request;

/**
 * Created by LG on 2018-01-12.
 */
public class Join {

    private String memberId;
    private String memberPassWord;
    private String memberName;
    private String memberPhone;
    private int memberType;
    private String memberAddress;

    public Join(String memberId, String memberPassWord, String memberName, String memberPhone , int memberType,String memberAddress ) {
        this.memberId = memberId;
        this.memberPassWord = memberPassWord;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.memberType = memberType;
        this.memberAddress = memberAddress;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberPassWord() {
        return memberPassWord;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public int getMemberType() {
        return memberType;
    }

    public String getMemberAddress() {
        return memberAddress;
    }
}
