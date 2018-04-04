package com.mytv.rtzhdj.app.data.entity;

/**
 * UserRegisterEntity   用户注册实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update
 */
public class UserRegisterEntity {

    private int userId;
    private String PublishmentSystemId;
    private String Mobile;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPublishmentSystemId() {
        return PublishmentSystemId;
    }

    public void setPublishmentSystemId(String publishmentSystemId) {
        PublishmentSystemId = publishmentSystemId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
