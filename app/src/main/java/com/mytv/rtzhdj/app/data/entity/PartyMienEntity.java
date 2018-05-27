package com.mytv.rtzhdj.app.data.entity;

/**
 * PartyMienEntity   党员风采 实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-5-9
 * @update 2018-5-27    完善党员详情 实体类
 */
public class PartyMienEntity {

    private int ID;
    private String Title;
    private String ImageUrl;
    private String TelePhone;
    private String AllImgUrl;

    private String LinkPhone;
    private String PublishmentSystemName;
    private String Address;
    private String QQ;
    private String Email;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTelePhone() {
        return TelePhone;
    }

    public void setTelePhone(String telePhone) {
        TelePhone = telePhone;
    }

    public String getAllImgUrl() {
        return AllImgUrl;
    }

    public void setAllImgUrl(String allImgUrl) {
        AllImgUrl = allImgUrl;
    }

    public String getLinkPhone() {
        return LinkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        LinkPhone = linkPhone;
    }

    public String getPublishmentSystemName() {
        return PublishmentSystemName;
    }

    public void setPublishmentSystemName(String publishmentSystemName) {
        PublishmentSystemName = publishmentSystemName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
