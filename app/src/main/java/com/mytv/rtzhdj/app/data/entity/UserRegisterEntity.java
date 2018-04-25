package com.mytv.rtzhdj.app.data.entity;

/**
 * UserRegisterEntity   用户注册实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-4
 * @update 2018-4-25
 */
public class UserRegisterEntity {

    private int UserId;                     // 用户id
    private int PublishmentSystemId;        // 党支部id
    private String Mobile;                  // 手机

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getPublishmentSystemId() {
        return PublishmentSystemId;
    }

    public void setPublishmentSystemId(int publishmentSystemId) {
        PublishmentSystemId = publishmentSystemId;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
