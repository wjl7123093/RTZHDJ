package com.mytv.rtzhdj.app.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * LoginEntity   登录实体类
 *
 * @author Fred_W
 * @version v1.0.0(1)
 *
 * @crdate 2018-4-21
 * @update
 */
public class LoginEntity implements Serializable {

    private int UserId;
    private String UserName;                // 名称
    private int UserTypeId;                 // 用户类型ID
    private int Integral;                   // 用户积分
    private int PositiveEnergyValue;        // 正能量值
    private int Ranking;                    // 排名
    private int PublishmentSystemId;        // 所在社区id
    private String PublishmentSystemName;   // 所在社区名称
    private String PhotoUrl;                // 用户头像

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        UserTypeId = userTypeId;
    }

    public int getIntegral() {
        return Integral;
    }

    public void setIntegral(int integral) {
        Integral = integral;
    }

    public int getPositiveEnergyValue() {
        return PositiveEnergyValue;
    }

    public void setPositiveEnergyValue(int positiveEnergyValue) {
        PositiveEnergyValue = positiveEnergyValue;
    }

    public int getRanking() {
        return Ranking;
    }

    public void setRanking(int ranking) {
        Ranking = ranking;
    }

    public int getPublishmentSystemId() {
        return PublishmentSystemId;
    }

    public void setPublishmentSystemId(int publishmentSystemId) {
        PublishmentSystemId = publishmentSystemId;
    }

    public String getPublishmentSystemName() {
        return PublishmentSystemName;
    }

    public void setPublishmentSystemName(String publishmentSystemName) {
        PublishmentSystemName = publishmentSystemName;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }
}
