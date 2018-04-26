package com.mytv.rtzhdj.app.data.entity;

/**
 * PartyMemberEntity   党员实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-2-11
 * @update 2018-4-26    更新实体类
 */
public class PartyMemberEntity {

    private int Id;
    private String UserName;
    private String Photourl;
    private String duty;
    private String party_branch;
    private String Mobile;
    private String telephone;
    private String emergency_phone;
    private String other_connections;
    private String address;
    private String qq;
    private String email;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhotourl() {
        return Photourl;
    }

    public void setPhotourl(String photourl) {
        Photourl = photourl;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getParty_branch() {
        return party_branch;
    }

    public void setParty_branch(String party_branch) {
        this.party_branch = party_branch;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmergency_phone() {
        return emergency_phone;
    }

    public void setEmergency_phone(String emergency_phone) {
        this.emergency_phone = emergency_phone;
    }

    public String getOther_connections() {
        return other_connections;
    }

    public void setOther_connections(String other_connections) {
        this.other_connections = other_connections;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
